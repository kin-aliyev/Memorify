package com.example.feature_home.presentation.collection_detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.core_domain.model.word.KnowledgeLevel
import com.example.core_domain.model.word.WordCard
import com.example.core_domain.repository.CollectionRepository
import com.example.core_domain.repository.WordRepository
import com.example.core_domain.usecase.home.GetCollectionDetailUseCase
import com.example.feature_home.presentation.collection_detail.model.WordFilterState
import com.example.feature_home.presentation.collection_detail.model.WordSortOption
import com.example.feature_home.presentation.navigation.HomeRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CollectionDetailViewModel @Inject constructor(
    private val getCollectionDetail: GetCollectionDetailUseCase,
    private val wordRepository: WordRepository,
    private val collectionRepository: CollectionRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private val collectionId = savedStateHandle.toRoute<HomeRoute.CollectionDetail>().collectionId

    private val _uiState = MutableStateFlow(CollectionDetailUiState())
    val uiState: StateFlow<CollectionDetailUiState> = _uiState.asStateFlow()

    private val _navigationEvent = MutableSharedFlow<CollectionDetailNavigationEvent>()
    val navigationEvent: SharedFlow<CollectionDetailNavigationEvent> =
        _navigationEvent.asSharedFlow()

    private val _errorEvent = MutableSharedFlow<CollectionDetailError>()
    val errorEvent: SharedFlow<CollectionDetailError> = _errorEvent.asSharedFlow()

    init {
        loadData()
    }

    fun onAction(action: CollectionDetailAction) = when (action) {
        is CollectionDetailAction.OnLevelFilterToggle -> toggleLevelFilter(action.level)
        is CollectionDetailAction.OnSortOptionSelect -> updateSort(action.option)
        is CollectionDetailAction.OnFavoritesToggle -> toggleFavoritesFilter()
        is CollectionDetailAction.OnClearFilters -> clearFilters()

        is CollectionDetailAction.OnFavoriteToggle -> toggleFavorite(action.word)
        is CollectionDetailAction.OnDeleteWord -> deleteWord(action.word)

        is CollectionDetailAction.OnToggleTranslation -> toggleTranslation()

        is CollectionDetailAction.OnEditCollection -> navigate(CollectionDetailNavigationEvent.ToEditCollection)
        is CollectionDetailAction.OnDeleteCollection -> deleteCollection()
    }

    private fun loadData() {
        viewModelScope.launch {
            getCollectionDetail(collectionId)
                .onStart { _uiState.update { it.copy(isLoading = true) } }
                .catch {
                    _uiState.update { it.copy(isLoading = false) }
                    _errorEvent.emit(CollectionDetailError.LoadFailed)
                }
                .collect { detail ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            collection = detail.collection,
                            words = detail.words,
                        )
                    }
                }
        }
    }

    private fun toggleLevelFilter(level: KnowledgeLevel) {
        _uiState.update { state ->
            val updated = state.filterState.selectedLevels
                .let { if (level in it) it - level else it + level }
            state.copy(filterState = state.filterState.copy(selectedLevels = updated))
        }
    }

    private fun updateSort(option: WordSortOption) {
        _uiState.update { state ->
            state.copy(filterState = state.filterState.copy(sortOption = option))
        }
    }

    private fun toggleFavoritesFilter() {
        _uiState.update { state ->
            state.copy(filterState = state.filterState.copy(favoritesOnly = !state.filterState.favoritesOnly))
        }
    }

    private fun clearFilters() {
        _uiState.update { state ->
            state.copy(filterState = WordFilterState())
        }
    }

    private fun toggleFavorite(word: WordCard) {
        viewModelScope.launch {
            wordRepository.updateWord(word.copy(isFavorite = !word.isFavorite))
                .onFailure { _errorEvent.emit(CollectionDetailError.FavoriteUpdateFailed) }
        }
    }

    private fun deleteWord(word: WordCard) {
        viewModelScope.launch {
            wordRepository.deleteWord(word)
                .onFailure { _errorEvent.emit(CollectionDetailError.DeleteWordFailed) }
        }
    }

    private fun toggleTranslation() {
        _uiState.update { it.copy(showTranslation = !it.showTranslation) }
    }

    private fun deleteCollection() {
        viewModelScope.launch {
            val collection = _uiState.value.collection ?: return@launch
            collectionRepository.deleteCollection(collection)
                .onSuccess { navigate(CollectionDetailNavigationEvent.Back) }
                .onFailure { _errorEvent.emit(CollectionDetailError.DeleteCollectionFailed) }
        }
    }

    private fun navigate(event: CollectionDetailNavigationEvent) {
        viewModelScope.launch { _navigationEvent.emit(event) }
    }

}
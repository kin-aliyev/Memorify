package com.example.feature_home.presentation.collection_detail

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.core_domain.model.collection.CollectionColor
import com.example.core_domain.model.word.KnowledgeLevel
import com.example.core_domain.model.word.WordCard
import com.example.core_domain.usecase.home.GetCollectionDetailUseCase
import com.example.feature_home.domain.usecase.DeleteCollectionUseCase
import com.example.feature_home.domain.usecase.DeleteWordUseCase
import com.example.feature_home.domain.usecase.UpdateCollectionUseCase
import com.example.feature_home.domain.usecase.UpdateWordUseCase
import com.example.feature_home.presentation.collection_detail.model.WordFilterState
import com.example.feature_home.presentation.collection_detail.model.WordSortOption
import com.example.feature_home.presentation.navigation.HomeGraph
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CancellationException
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
    private val updateWordCard: UpdateWordUseCase,
    private val deleteWordCard: DeleteWordUseCase,
    private val updateCollection : UpdateCollectionUseCase,
    private val deleteCollection : DeleteCollectionUseCase,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private val collectionId = savedStateHandle.toRoute<HomeGraph.CollectionDetail>().collectionId

    private val _uiState = MutableStateFlow(CollectionDetailUiState())
    val uiState: StateFlow<CollectionDetailUiState> = _uiState.asStateFlow()

    private val _navigationEvent = MutableSharedFlow<CollectionDetailNavigationEvent>()
    val navigationEvent: SharedFlow<CollectionDetailNavigationEvent> = _navigationEvent.asSharedFlow()

    private val _errorEvent = MutableSharedFlow<CollectionDetailError>()
    val errorEvent: SharedFlow<CollectionDetailError> = _errorEvent.asSharedFlow()

    init {
        loadData()
    }

    fun onAction(action: CollectionDetailAction) = when (action) {
        // Filters
        CollectionDetailAction.OnClearFilters -> clearFilters()
        is CollectionDetailAction.OnKnowledgeFilterToggle -> toggleLevelFilter(action.level)
        CollectionDetailAction.OnFavoritesFilterToggle -> toggleFavoritesFilter()
        is CollectionDetailAction.OnSortOptionSelect -> updateSort(action.option)

        // Word
        is CollectionDetailAction.OnWordFavoriteToggle -> toggleFavorite(action.word)
        is CollectionDetailAction.OnEditWord -> navigate(
            event = CollectionDetailNavigationEvent.ToEditWord(collectionId = collectionId, wordId = action.word.id)
        )
        is CollectionDetailAction.OnDeleteWord -> deleteWord(action.word)

        // Fab
        is CollectionDetailAction.OnAddWordManualClick -> navigate(
            event = CollectionDetailNavigationEvent.ToAddWordManual(collectionId)
        )
        is CollectionDetailAction.OnAddWordAiClick -> navigate(
            event = CollectionDetailNavigationEvent.ToAddWordAi(collectionId)
        )

        // Collection
        CollectionDetailAction.OnRetry -> loadData()
        CollectionDetailAction.OnTranslationVisibilityToggled -> toggleTranslation()
        is CollectionDetailAction.OnEditCollectionConfirm -> updateCollection(
            name = action.name, emoji = action.emoji, color = action.color
        )
        CollectionDetailAction.OnDeleteCollection -> handleDeleteCollection()
    }

    private fun loadData() {
        viewModelScope.launch {
            Log.d("DETAIL", "collection = doo")
            getCollectionDetail(collectionId)
                .onStart { _uiState.update { it.copy(isLoading = true, isError = false) } }
                .catch { e ->
                    Log.d("DETAIL", "collection = ${e.message} and ${e.cause}")
                    if (e is CancellationException) throw e
                    _uiState.update { it.copy(isLoading = false, isError = true) }
                    _errorEvent.emit(CollectionDetailError.LoadFailed)
                }
                .collect { detail ->
                    Log.d("DETAIL", "collection = ${detail.collection.name}")
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            isError = false,
                            collection = detail.collection,
                            words = detail.words
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
            updateWordCard(word.copy(isFavorite = !word.isFavorite))
                .onFailure { _errorEvent.emit(CollectionDetailError.FavoriteUpdateFailed) }
        }
    }

    private fun deleteWord(word: WordCard) {
        viewModelScope.launch {
            deleteWordCard(word)
                .onFailure { _errorEvent.emit(CollectionDetailError.DeleteWordFailed) }
        }
    }

    private fun toggleTranslation() {
        _uiState.update { it.copy(showTranslation = !it.showTranslation) }
    }

    private fun updateCollection(name: String, emoji: String, color: CollectionColor) {
        viewModelScope.launch {
            val current = _uiState.value.collection ?: return@launch
            val updated = current.copy(
                name = name, emoji = emoji, color = color.name
            )
            updateCollection(updated)
                .onFailure { _errorEvent.emit(CollectionDetailError.UpdateCollectionFailed) }
        }
    }

    private fun handleDeleteCollection() {
        viewModelScope.launch {
            val collection = _uiState.value.collection ?: return@launch
            deleteCollection(collection)
                .onSuccess { navigate(CollectionDetailNavigationEvent.Back) }
                .onFailure { _errorEvent.emit(CollectionDetailError.DeleteCollectionFailed) }
        }
    }

    private fun navigate(event: CollectionDetailNavigationEvent) {
        viewModelScope.launch { _navigationEvent.emit(event) }
    }

}
package com.example.feature_home.presentation.collections

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core_domain.exception.AppException
import com.example.core_domain.model.collection.Collection
import com.example.core_domain.model.collection.CollectionColor
import com.example.core_domain.usecase.home.GetCollectionsUseCase
import com.example.feature_home.domain.usecase.CreateCollectionUseCase
import com.example.feature_home.presentation.collections.CollectionsNavigationEvent.ToCollectionDetail
import com.example.feature_home.presentation.model.toUiModel
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
class CollectionsViewModel @Inject constructor(
    private val getCollectionsUseCase: GetCollectionsUseCase,
    private val createCollectionUseCase: CreateCollectionUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(CollectionsUiState())
    val uiState: StateFlow<CollectionsUiState> = _uiState.asStateFlow()

    private val _navigationEvent = MutableSharedFlow<CollectionsNavigationEvent>()
    val navigationEvent: SharedFlow<CollectionsNavigationEvent> = _navigationEvent.asSharedFlow()

    private val _errorEvent = MutableSharedFlow<CollectionsError>()
    val errorEvent: SharedFlow<CollectionsError> = _errorEvent.asSharedFlow()

    init {
        loadCollections()
    }

    fun onAction(action: CollectionsAction) = when (action) {
        CollectionsAction.OnAddWordAiClick -> navigate(CollectionsNavigationEvent.ToAddAi)
        CollectionsAction.OnAddWordManualClick -> navigate(CollectionsNavigationEvent.ToAddManual)

        is CollectionsAction.OnCollectionClick -> {
            navigate(ToCollectionDetail(action.collectionId))
        }

        is CollectionsAction.OnAddCollectionConfirm -> {
            createCollection(action.name, action.emoji, action.color)
        }
    }

    private fun createCollection(name: String, emoji: String, color: CollectionColor) {
        viewModelScope.launch {
            createCollectionUseCase(Collection(name = name, emoji = emoji, color = color.name))
                .onFailure { error ->
                    val collectionError = when (error) {
                        is AppException.NetworkError -> CollectionsError.NetworkError
                        is AppException.UserNotFound -> CollectionsError.LoadFailed
                        else -> CollectionsError.CreateFailed
                    }
                    _errorEvent.emit(collectionError)
                }
        }
    }

    private fun loadCollections() {
        viewModelScope.launch {
            getCollectionsUseCase()
                .onStart { _uiState.update { it.copy(isLoading = true) } }
                .catch { error ->
                    _uiState.update { it.copy(isLoading = false) }
                    val collectionError = when (error) {
                        is AppException.NetworkError -> CollectionsError.NetworkError
                        is AppException.UserNotFound -> CollectionsError.LoadFailed
                        else -> CollectionsError.LoadFailed
                    }
                    _errorEvent.emit(collectionError)
                }
                .collect { collections ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            collections = collections.map { it.toUiModel() })
                    }
                }
        }
    }

    private fun navigate(event: CollectionsNavigationEvent) {
        viewModelScope.launch { _navigationEvent.emit(event) }
    }

}
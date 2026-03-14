package com.example.feature_home.presentation.collections

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core_domain.usecase.home.GetCollectionsUseCase
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
    private val getCollectionsUseCase: GetCollectionsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(CollectionsUiState())
    val uiState: StateFlow<CollectionsUiState> = _uiState.asStateFlow()

    private val _navigationEvent = MutableSharedFlow<CollectionsNavigationEvent>()
    val navigationEvent: SharedFlow<CollectionsNavigationEvent> = _navigationEvent.asSharedFlow()

    init {
        loadCollections()
    }

    fun onAction(action: CollectionsAction) = when(action) {
        CollectionsAction.OnAddCollectionClick -> navigate(CollectionsNavigationEvent.ToAddCollection)
        CollectionsAction.OnAddWordAiClick -> navigate(CollectionsNavigationEvent.ToAddAi)
        CollectionsAction.OnAddWordManualClick -> navigate(CollectionsNavigationEvent.ToAddManual)

        is CollectionsAction.OnCollectionClick -> { navigate(ToCollectionDetail(action.collectionId)) }
    }

    private fun loadCollections() {
        viewModelScope.launch {
            getCollectionsUseCase()
                .onStart { _uiState.update { it.copy(isLoading = true) } }
                .catch { error -> _uiState.update { it.copy(isLoading = false, errorMessage = error.message) } }
                .collect { collections ->
                    _uiState.update { it.copy(isLoading = false, collections = collections.map { it.toUiModel() }) }
                }
        }
    }

    private fun navigate(event: CollectionsNavigationEvent) {
        viewModelScope.launch { _navigationEvent.emit(event) }
    }

}
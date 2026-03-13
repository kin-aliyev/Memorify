package com.example.feature_home.presentation.collections

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.feature_home.presentation.collections.CollectionsNavigationEvent.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CollectionsViewModel @Inject constructor(

) : ViewModel() {

    private val _uiState = MutableStateFlow(CollectionsUiState())
    val uiState: StateFlow<CollectionsUiState> = _uiState.asStateFlow()

    private val _navigationEvent = MutableSharedFlow<CollectionsNavigationEvent>()
    val navigationEvent: SharedFlow<CollectionsNavigationEvent> = _navigationEvent.asSharedFlow()

    fun onAction(action: CollectionsAction) = when(action) {
        CollectionsAction.OnAddCollectionClick -> { }
        is CollectionsAction.OnCollectionClick -> {
            viewModelScope.launch {
                _navigationEvent.emit(ToCollectionDetail(action.deckId))
            }
        }

        CollectionsAction.OnAddWordAiClick -> { }
        CollectionsAction.OnAddWordManualClick -> { }
    }

}
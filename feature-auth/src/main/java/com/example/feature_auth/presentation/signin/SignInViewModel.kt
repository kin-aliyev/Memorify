package com.example.feature_auth.presentation.signin

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core_domain.exception.AppException
import com.example.feature_auth.domain.provider.AuthStringProvider
import com.example.feature_auth.domain.validation.EmailValidator
import com.example.feature_auth.domain.validation.PasswordValidator
import com.example.feature_auth.domain.usecase.auth.SignInWithEmailUseCase
import com.example.feature_auth.domain.usecase.auth.SignInWithGoogleUseCase
import com.example.feature_auth.presentation.mapper.mapPasswordRules
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.onSuccess

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val signInWithEmailUseCase: SignInWithEmailUseCase,
    private val signInWithGoogleUseCase: SignInWithGoogleUseCase,
    private val emailValidator: EmailValidator,
    private val passwordValidator: PasswordValidator,
    private val stringProvider: AuthStringProvider,
) : ViewModel() {

    private val _uiState = MutableStateFlow(SignInUiState())
    val uiState: StateFlow<SignInUiState> = _uiState.asStateFlow()

    private val _navigationEvent = MutableSharedFlow<SignInNavigationEvent>()
    val navigationEvent: SharedFlow<SignInNavigationEvent> = _navigationEvent.asSharedFlow()

    fun onAction(action: SignInAction) {
        when (action) {
            is SignInAction.UpdateEmail -> updateEmail(action.email)
            is SignInAction.UpdatePassword -> updatePassword(action.password)
            SignInAction.SignInWithEmail -> { signInWithEmail() }
            is SignInAction.SignInWithGoogle -> signInWithGoogle(action.context)
            SignInAction.ForgotPassword -> navigate(SignInNavigationEvent.ToForgotPassword)
            SignInAction.CreateAccount -> navigate(SignInNavigationEvent.ToSignUp)
            SignInAction.EmailFocusLost -> { _uiState.update { it.copy(isEmailTouched = true) } }
        }
    }

    private fun updateEmail(email: String) {
        val isEmailValid = emailValidator.isValid(email)
        _uiState.update { currentState ->
            currentState.copy(
                email = email,
                isEmailValid = isEmailValid,
                errorMessage = null
            )
        }
    }

    private fun updatePassword(password: String) {
        val result = passwordValidator.validate(password)
        val rulesUi = mapPasswordRules(result.failedRules, stringProvider)

        _uiState.update { currentState ->
            currentState.copy(
                password = password,
                isPasswordValid = result.isValid,
                passwordRules = rulesUi,
                errorMessage = null
            )
        }
    }

    private fun signInWithEmail() {
        _uiState.update { it.copy(isEmailTouched = true) }
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            val state = _uiState.value
            signInWithEmailUseCase(state.email, state.password)
                .onSuccess { navigate(SignInNavigationEvent.ToHome) }
                .onFailure(::handleError)
        }
    }

    private fun signInWithGoogle(context: Context) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            signInWithGoogleUseCase(context)
                .onSuccess { navigate(SignInNavigationEvent.ToHome) }
                .onFailure(::handleError)
        }
    }

    private fun handleError(error: Throwable) {
        val authException = error as? AppException ?: AppException.UnknownError(error)
        val errorMessage = stringProvider.getErrorMessage(authException)

        _uiState.update { currentState ->
            currentState.copy(
                isLoading = false,
                errorMessage = errorMessage
            )
        }
    }

    private fun navigate(event: SignInNavigationEvent) {
        _uiState.update { it.copy(isLoading = false) }
        viewModelScope.launch { _navigationEvent.emit(event) }
    }
}


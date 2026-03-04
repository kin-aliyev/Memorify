package com.example.feature_auth.presentation.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.feature_auth.domain.exception.AuthException
import com.example.feature_auth.domain.provider.AuthStringProvider
import com.example.feature_auth.domain.usecase.auth.SignUpWithEmailUseCase
import com.example.feature_auth.domain.validation.EmailValidator
import com.example.feature_auth.domain.validation.PasswordValidator
import com.example.feature_auth.presentation.mapper.mapPasswordRules
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.onSuccess

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val signUpWithEmailUseCase: SignUpWithEmailUseCase,
    private val emailValidator: EmailValidator,
    private val passwordValidator: PasswordValidator,
    private val stringProvider: AuthStringProvider,
) : ViewModel() {

    private val _uiState = MutableStateFlow(SignUpUiState())
    val uiState: StateFlow<SignUpUiState> = _uiState.asStateFlow()

    fun handleAction(action: SignUpAction) = when (action) {
        is SignUpAction.UpdateEmail -> updateEmail(action.email)
        is SignUpAction.UpdatePassword -> updatePassword(action.password)
        is SignUpAction.UpdateConfirmPassword -> updateConfirmPassword(action.confirmPassword)
        SignUpAction.SignUpWithEmail -> signUpWithEmail()
        SignUpAction.EmailFocusLost -> { _uiState.update { it.copy(isEmailTouched = true) }}
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

        val state = _uiState.value

        // Check if confirm password still matches after password change
        val isConfirmPasswordValid = if (state.confirmPassword.isNotBlank()) {
            state.confirmPassword == password
        } else {
            state.isConfirmPasswordValid
        }

        _uiState.update { currentState ->
            currentState.copy(
                password = password,
                isPasswordValid = result.isValid,
                isConfirmPasswordValid = isConfirmPasswordValid,
                passwordRules = rulesUi,
                errorMessage = null
            )
        }
    }

    private fun updateConfirmPassword(confirmPassword: String) {
        val state = _uiState.value
        val isValid = confirmPassword.isNotBlank() && confirmPassword == state.password

        _uiState.update { currentState ->
            currentState.copy(
                confirmPassword = confirmPassword,
                isConfirmPasswordValid = isValid,
                errorMessage = null
            )
        }
    }

    private fun signUpWithEmail() {
        _uiState.update { it.copy(isEmailTouched = true) }
        viewModelScope.launch {
            setLoadingState(true)

            val state = _uiState.value
            signUpWithEmailUseCase(state.email, state.password, state.confirmPassword)
                .onSuccess { navigateToHome() }
                .onFailure(::handleError)
        }
    }

    private fun handleError(error: Throwable) {
        val authException = error as? AuthException ?: AuthException.UnknownError(error)
        val errorMessage = stringProvider.getErrorMessage(authException)

        _uiState.update { currentState ->
            currentState.copy(
                isLoading = false,
                errorMessage = errorMessage
            )
        }
    }

    private fun navigateToHome() {
        _uiState.update { currentState ->
            currentState.copy(
                isLoading = false,
                errorMessage = null,
                navigationEvent = SignUpNavigationEvent.NavigateToHome
            )
        }
    }

    private fun setLoadingState(isLoading: Boolean) {
        _uiState.update { it.copy(isLoading = isLoading) }
    }

    fun onNavigationHandled(){
        _uiState.update { it.copy(navigationEvent = null) }
    }
}
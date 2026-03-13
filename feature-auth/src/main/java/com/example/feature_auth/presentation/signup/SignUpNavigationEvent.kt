package com.example.feature_auth.presentation.signup

sealed interface SignUpNavigationEvent{
    data object ToHome : SignUpNavigationEvent
}
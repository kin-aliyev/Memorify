package com.example.feature_auth.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.core_ui.model.TopBarState
import com.example.core_ui.navigation.GraphRoute
import com.example.feature_auth.presentation.signin.SignInScreen
import com.example.feature_auth.presentation.signup.SignUpScreen

fun NavGraphBuilder.authNavGraph(
    navController: NavController,
    onSetTopBar: (TopBarState) -> Unit,
    onAuthSuccess: () -> Unit,
) {
    navigation<GraphRoute.Auth>(startDestination = AuthRoute.SignIn) {

        composable<AuthRoute.SignIn> {
            SignInScreen(
                onSetTopBar = onSetTopBar,
                onNavigateToSignUp = { navController.navigate(AuthRoute.SignUp) },
                onNavigateToForgotPassword = { navController.navigate(AuthRoute.ForgotPassword) },
                onAuthSuccess = onAuthSuccess
            )
        }

        composable<AuthRoute.SignUp> {
            SignUpScreen(
                onSetTopBar = onSetTopBar,
                onAuthSuccess = onAuthSuccess
            )
        }
    }
}
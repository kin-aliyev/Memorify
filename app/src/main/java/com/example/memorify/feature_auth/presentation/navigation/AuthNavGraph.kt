package com.example.memorify.feature_auth.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.memorify.core.navigation.GraphRoute
import com.example.memorify.feature_auth.presentation.signin.SignInScreen
import com.example.memorify.feature_auth.presentation.signup.SignUpScreen

fun NavGraphBuilder.authNavGraph(navController: NavController) {
    navigation<GraphRoute.Auth>(startDestination = AuthRoute.SignIn) {

        composable<AuthRoute.SignIn> {
            SignInScreen(navController = navController)
        }

        composable<AuthRoute.SignUp> {
            SignUpScreen(navController = navController)
        }
    }
}
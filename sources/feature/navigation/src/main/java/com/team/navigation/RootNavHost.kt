package com.team.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.team.feature_auth.presentation.screens.LoginScreen
import com.team.feature_auth.presentation.screens.RegisterScreen
import com.team.feature_auth.presentation.state.AuthState
import com.team.feature_auth.presentation.viewModels.AuthViewModel
import com.team.main_menu.presentation.screens.home_screen.HomeScreen
import com.team.main_menu.presentation.screens.about.AboutScreen
import com.team.main_menu.presentation.screens.my_orders.MyOrdersScreen
import com.team.main_menu.presentation.screens.offer_details.OfferDetailsScreen
import kotlinx.serialization.Serializable

sealed class RootNavDestinations {

    @Serializable
    object LoginScreen : RootNavDestinations()

    @Serializable
    object RegisterScreen : RootNavDestinations()

    @Serializable
    object HomeScreen : RootNavDestinations()

    @Serializable
    object MyOrdersScreen : RootNavDestinations()

    @Serializable
    object AboutScreen : RootNavDestinations()

    @Serializable
    class OfferDetailsScreen(
        val offerId: String,
        val destinationCity: String = ""
    ) : RootNavDestinations()
}

@Composable
fun RootNavHost(
    authViewModel: AuthViewModel = hiltViewModel()
) {
    val navController = rememberNavController()
    val authState by authViewModel.authState.collectAsState()

    val startDestination = if (authState == AuthState.Authorized){
        RootNavDestinations.HomeScreen
    } else {
        RootNavDestinations.LoginScreen
    }

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable<RootNavDestinations.LoginScreen>(
            exitTransition = { NavAnimations.ScaleOut(this) },
            enterTransition = { NavAnimations.ScaleIn(this) },
            popExitTransition = { NavAnimations.ScaleOut(this) },
            popEnterTransition = { NavAnimations.ScaleIn(this) }
        ) {
            LoginScreen(
                onAuthSuccess = {
                    navController.navigate(RootNavDestinations.HomeScreen) {
                        popUpTo(RootNavDestinations.LoginScreen) {
                            inclusive = true
                        }
                    }
                },
                navigateToRegisterScreen = {
                    navController.navigate(RootNavDestinations.RegisterScreen)
                }
            )
        }

        composable<RootNavDestinations.RegisterScreen>(
            enterTransition = { NavAnimations.SlideInRight(this) },
            exitTransition = { NavAnimations.SlideOutRight(this) },
            popEnterTransition = { NavAnimations.SlideInRight(this) },
            popExitTransition = { NavAnimations.SlideOutRight(this) },
        ) {
            RegisterScreen(
                onAuthSuccess = {
                    navController.navigate(RootNavDestinations.HomeScreen) {
                        popUpTo(RootNavDestinations.LoginScreen) {
                            inclusive = true
                        }
                    }
                },
                navigateToLoginScreen = { navController.popBackStack() }
            )
        }

        composable<RootNavDestinations.HomeScreen>(
            enterTransition = { NavAnimations.SlideInRight(this) },
            exitTransition = { null },
            popEnterTransition = { null },
            popExitTransition = { NavAnimations.SlideOutRight(this) }
        ) {
            HomeScreen(
                onOfferClick = { offerId, destinationCity ->
                    navController.navigate(
                        RootNavDestinations.OfferDetailsScreen(
                            offerId = offerId,
                            destinationCity = destinationCity
                        )
                    )
                },
                onMyOrdersClick = {
                    navController.navigate(RootNavDestinations.MyOrdersScreen)
                },
                onAboutClick = {
                    navController.navigate(RootNavDestinations.AboutScreen)
                },
                exitAccount = {
                    navController.navigate(RootNavDestinations.LoginScreen)
                }
            )
        }

        composable<RootNavDestinations.AboutScreen>(
            enterTransition = { NavAnimations.SlideInRight(this) },
            exitTransition = { null },
            popEnterTransition = { null },
            popExitTransition = { NavAnimations.SlideOutRight(this) }
        ) {
            AboutScreen(
                onBack = { navController.popBackStack() }
            )
        }

        composable<RootNavDestinations.MyOrdersScreen>(
            enterTransition = { NavAnimations.SlideInRight(this) },
            exitTransition = { null },
            popEnterTransition = { null },
            popExitTransition = { NavAnimations.SlideOutRight(this) }
        ) {
            MyOrdersScreen(
                onBack = { navController.popBackStack() }
            )
        }

        composable<RootNavDestinations.OfferDetailsScreen>(
            enterTransition = { NavAnimations.SlideInRight(this) },
            exitTransition = { null },
            popEnterTransition = { null },
            popExitTransition = { NavAnimations.SlideOutRight(this) }
        ) { backStackEntry ->
            val args = backStackEntry.toRoute<RootNavDestinations.OfferDetailsScreen>()

            OfferDetailsScreen(
                offerId = args.offerId,
                destinationCity = args.destinationCity,
                onBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}
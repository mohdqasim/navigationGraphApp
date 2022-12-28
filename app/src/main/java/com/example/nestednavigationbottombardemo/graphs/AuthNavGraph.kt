package com.example.nestednavigationbottombardemo.graphs

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.nestednavigationbottombardemo.screens.LoginContent
import com.example.nestednavigationbottombardemo.screens.ScreenContent

fun NavGraphBuilder.authNavGraph(navController: NavHostController) {
    navigation(
        route = Graph.AUTHENTICATION,
        startDestination = AuthScreen.Login.route
    ) {
        composable(route = AuthScreen.Login.route) {
            LoginContent(
                onClick = {
                    navController.popBackStack()
                    navController.navigate(Graph.HOME)
                },
                onSignUpClick = {
                    navController.navigate(AuthScreen.SignUp.route)
                },
                onForgotClick = {
                    navController.navigate(AuthScreen.Forgot.route)
                }
            )
        }
        composable(route = AuthScreen.SignUp.route) {
            ScreenContent(name = AuthScreen.SignUp.route) {}
        }
        composable(route = AuthScreen.Forgot.route) {
            ScreenContent(name = AuthScreen.Forgot.route) {
                navController.navigate(AuthScreen.MY_PROFILE.route)
                navController.navigate(AuthScreen.MY_PROFILE.createRoute("userName"))
            }
        }
        composable(route = AuthScreen.MY_PROFILE.createRoute("ROhit")) {
            ScreenContent(name = AuthScreen.Forgot.route) {
                navController.navigate(AuthScreen.MY_PROFILE_WITH_MANDATORY_ARGS.route)
                navController.navigate(AuthScreen.MY_PROFILE_WITH_MANDATORY_ARGS.createRoute("userName"))
            }
        }
        composable(route = AuthScreen.MY_PROFILE_WITH_MANDATORY_ARGS.createRoute("sudhir")) {
            ScreenContent(name = AuthScreen.Forgot.route) {}
        }

    }
}

sealed class AuthScreen(val route: String) {
    object Login : AuthScreen(route = "LOGIN")
    object SignUp : AuthScreen(route = "SIGN_UP")
    object Forgot : AuthScreen(route = "FORGOT")
    //with optional parameter
    object MY_PROFILE :
        AuthScreen(route = "${Route.MyProfile}?${AuthArgs.userName}={${AuthArgs.userName}}") {
        //in case of optional you can allow nullable as well.
        fun createRoute(userName: String?=null) = "${Route.MyProfile}?${AuthArgs.userName}=$userName"
    }
    //with mandatory parameter follow this pattern.
    object MY_PROFILE_WITH_MANDATORY_ARGS : AuthScreen(route = "${Route.MyProfile}/{${AuthArgs.userName}}"){
        fun createRoute(userName:String) = "${Route.MyProfile}/$userName"
    }
}

private object Route{
    const val MyProfile = "myProfile"
}
private object AuthArgs{
const val userName = "userName"
}
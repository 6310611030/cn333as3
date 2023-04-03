package com.example.gameapp


import androidx.annotation.StringRes
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.gameapp.ui.StartGameScreen
import com.example.gameapp.ui.GuessingGameApp
import com.example.gameapp.ui.GameScreen


/**
 * enum values that represent the screens in the app
 */
enum class GameScreen(@StringRes val title: Int) {
    Start(title = R.string.app_name),
    SelectGame(title = R.string.select_game),
    NumberGuessing(title = R.string.number_guess),
    Quiz(title = R.string.quiz),
    TicTacToe(title = R.string.tic_tac_toe)

}

@Composable
fun GameAppBar(
    currentScreen: GameScreen,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = { Text(stringResource(currentScreen.title)) },
        modifier = modifier,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back_button)
                    )
                }
            }
        }
    )
}

@Composable
fun GameAppScreen(
    modifier: Modifier = Modifier,
    //viewModel: GameViewModel = viewModel(),
    navController: NavHostController = rememberNavController()
) {
    // Get current back stack entry
    val backStackEntry by navController.currentBackStackEntryAsState()
    // Get the name of the current screen
    val currentScreen = GameScreen.valueOf(
        backStackEntry?.destination?.route ?: GameScreen.Start.name
    )

    Scaffold(
        topBar = {
            GameAppBar(
                currentScreen = currentScreen,
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = { navController.navigateUp() }
            )
        }
    ) { innerPadding ->
        //val uiState by viewModel.uiState.collectAsState()

        NavHost(
            navController = navController,
            startDestination = GameScreen.Start.name,
            modifier = modifier.padding(innerPadding)
        ) {
            composable(route = GameScreen.Start.name) {
                StartGameScreen(navController = navController)
            }
            composable(route = GameScreen.SelectGame.name + "/{name}",
                arguments = listOf(navArgument("name"){
                    type = NavType.StringType
                })) {
                val name = requireNotNull(it.arguments).getString("name")
                if (name != null) {
                    if (name == "NumberGuessing")
                        GuessingGameApp()
                    if (name == "Quiz")
                        GameScreen()
                    //if (name == "PairPicture")
//                  //      PairingGameScreen()
                    //    GameScreen()
                    //}
                }
            }
        }
    }}
package com.example.gameapp.ui

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import com.example.gameapp.R
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update


data class GameUiState(
    val currentQuiz: List<String> = listOf(),
    val currentQuizCount: Int = 1,
    val score: Int = 0,
    val isGuessedWordWrong: Boolean = false,
    val isGameOver: Boolean = false
)

class GameViewModel : ViewModel() {

    // Game UI state
    private val _uiState = MutableStateFlow(GameUiState())
    val uiState: StateFlow<GameUiState> = _uiState.asStateFlow()

    var userGuess by mutableStateOf("")
        private set

    // Set of words used in the game
    private var usedWords: MutableSet<List<String>> = mutableSetOf()
    private lateinit var currentWord: List<String>

    init {
        resetGame()
    }

    /*
     * Re-initializes the game data to restart the game.
     */
    fun resetGame() {
        usedWords.clear()
        _uiState.value = GameUiState(currentQuiz = pickRandomWordAndShuffle())
    }

    /*
     * Update the user's guess
     */
    fun updateUserGuess(guessedWord: String){
        userGuess = guessedWord
    }

    /*
     * Checks if the user's guess is correct.
     * Increases the score accordingly.
     */
    fun checkUserGuess() {
        if (userGuess.equals(currentWord[1])) {
            // User's guess is correct, increase the score
            // and call updateGameState() to prepare the game for next round
            val updatedScore = _uiState.value.score.plus(SCORE_INCREASE)
            updateGameState(updatedScore)
        } else {
            // User's guess is wrong, show an error
            _uiState.update { currentState ->
                currentState.copy(isGuessedWordWrong = true)

            }
            skipWord()
        }
        // Reset user guess
        updateUserGuess("")
    }

    /*
     * Skip to next word
     */
    fun skipWord() {
        updateGameState(_uiState.value.score)
        // Reset user guess
        updateUserGuess("")
    }


    private fun updateGameState(updatedScore: Int) {
        if (usedWords.size == MAX_NO_OF_QUIZZES){
            //Last round in the game, update isGameOver to true, don't pick a new word
            _uiState.update { currentState ->
                currentState.copy(
                    isGuessedWordWrong = false,
                    score = updatedScore,
                    isGameOver = true
                )
            }
        } else{
            // Normal round in the game
            _uiState.update { currentState ->
                currentState.copy(
                    isGuessedWordWrong = false,
                    currentQuiz = pickRandomWordAndShuffle(),
                    currentQuizCount = currentState.currentQuizCount.inc(),
                    score = updatedScore
                )
            }
        }
    }

    private fun shuffleCurrentWord(word: List<String>): List<String> {
        val tempquiz = word
        val tempWord = word[0].toCharArray()
        // Scramble the word
        tempWord.shuffle()
        while (String(tempWord).equals(word)) {
            tempWord.shuffle()
        }
        return tempquiz
    }

    private fun pickRandomWordAndShuffle(): List<String> {
        // Continue picking up a new random word until you get one that hasn't been used before
        currentWord = allQuizzes.random()
        currentWord = allQuizzes.random()
        if (usedWords.contains(currentWord)) {
            return pickRandomWordAndShuffle()
        } else {
            usedWords.add(currentWord)
            return shuffleCurrentWord(currentWord)
        }
    }
}


@Composable
fun GameScreen(
    modifier: Modifier = Modifier,
    gameViewModel: GameViewModel = viewModel()
) {
    val gameUiState by gameViewModel.uiState.collectAsState()
    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        GameStatus(
            quizCount = gameUiState.currentQuizCount,
            score = gameUiState.score
        )
        GameLayout(
            currentQuiz = gameUiState.currentQuiz,
            isGuessWrong = gameUiState.isGuessedWordWrong,
            userGuess = gameViewModel.userGuess
        )
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            OutlinedButton(
                onClick = { gameViewModel.skipWord() },
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp)
            ) {
                Text(stringResource(R.string.skip))
            }

        }
        if (gameUiState.isGameOver) {
            FinalScoreDialog(
                score = gameUiState.score,
                onPlayAgain = { gameViewModel.resetGame() }
            )
        }
    }
}


@Composable
fun GameStatus(quizCount: Int, score: Int, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
            .size(48.dp),
    ) {
        Text(
            text = stringResource(R.string.quiz_count, quizCount),
            fontSize = 18.sp,
        )
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentWidth(Alignment.End),
            text = stringResource(R.string.score, score),
            fontSize = 18.sp,
        )
    }
}

@Composable
fun GameLayout(
    currentQuiz: List<String>,
    isGuessWrong: Boolean,
    userGuess: String,
    gameViewModel: GameViewModel = viewModel(),
    modifier: Modifier = Modifier
) {
    var ans by remember { mutableStateOf(currentQuiz[0]) }
    val random = remember(gameViewModel.uiState.value.currentQuiz) {
        gameViewModel.uiState.value.currentQuiz.slice(1..4).shuffled()
    }
    Column(
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        Text(
            text = (currentQuiz[0]),
            fontSize = 24.sp,
            modifier = modifier.align(Alignment.CenterHorizontally).padding(10.dp)
        )
        Text(
            text = stringResource(R.string.instructions),
            fontSize = 17.sp,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Button(

            modifier = modifier
                .fillMaxWidth()

                .padding(start = 8.dp),
            onClick = {  gameViewModel.updateUserGuess(random[0]);gameViewModel.checkUserGuess() }
        ) {
            Text(text = random[0],
                fontSize = 20.sp,)
        }
        Button(
            modifier = modifier
                .fillMaxWidth()

                .padding(start = 8.dp),
            onClick = { gameViewModel.updateUserGuess(random[1]);gameViewModel.checkUserGuess() }
        ) {
            Text(text = random[1],
                fontSize = 20.sp,)
        }
        Button(
            modifier = modifier
                .fillMaxWidth()

                .padding(start = 8.dp),
            onClick = { gameViewModel.updateUserGuess(random[2]);gameViewModel.checkUserGuess() }
        ) {
            Text(text = (random[2]),
                fontSize = 20.sp,)
        }
        Button(
            modifier = modifier
                .fillMaxWidth()

                .padding(start = 8.dp),
            onClick = { gameViewModel.updateUserGuess(random[3]);gameViewModel.checkUserGuess() }
        ) {
            Text(text = (random[3]),
                fontSize = 20.sp,)
        }

    }
}

/*
 * Creates and shows an AlertDialog with final score.
 */
@Composable
private fun FinalScoreDialog(
    score: Int,
    onPlayAgain: () -> Unit,
    modifier: Modifier = Modifier
) {
    val activity = (LocalContext.current as Activity)

    AlertDialog(
        onDismissRequest = {
            // Dismiss the dialog when the user clicks outside the dialog or on the back
            // button. If you want to disable that functionality, simply use an empty
            // onCloseRequest.
        },
        title = { Text(stringResource(R.string.congratulations)) },
        text = { Text(stringResource(R.string.you_scored, score)) },
        modifier = modifier,
        dismissButton = {
            TextButton(
                onClick = {
                    activity.finish()
                }
            ) {
                Text(text = stringResource(R.string.exit))
            }
        },
        confirmButton = {
            TextButton(onClick = onPlayAgain) {
                Text(text = stringResource(R.string.try_again))
            }
        }
    )
}

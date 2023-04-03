package com.example.gameapp.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.gameapp.R

@Composable
fun StartGameScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController
){
    Column(
        modifier = modifier.padding(16.dp).fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        Image(
            painter = painterResource(R.drawable.game),
            contentDescription = null,
            modifier = Modifier.width(300.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = stringResource(R.string.select_game), style = MaterialTheme.typography.h4)
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = {navController.navigate("NumberGuessing")},
            Modifier.fillMaxWidth()
        ) {
            Text(text = stringResource(R.string.number_guess))
        }
        Button(onClick = {navController.navigate("Quiz")},
            Modifier.fillMaxWidth()
        ) {
            Text(text = stringResource(R.string.quiz))
        }
        Button(onClick = {navController.navigate("TicTacToe")},
            Modifier.fillMaxWidth()
        ) {
            Text(text = stringResource(R.string.tic_tac_toe))
        }
    }
}
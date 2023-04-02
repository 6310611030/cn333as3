package com.example.gameapp.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import com.example.gameapp.R


@Preview(showBackground = true)
@Composable
fun GuessingGameApp(modifier: Modifier = Modifier) {
    var answer by remember { mutableStateOf((1..1000).random()) }
    var input by remember { mutableStateOf(0) }
    var isGameWon by remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current
    var timesTry by remember { mutableStateOf(0) }
    var isGuessing by remember { mutableStateOf(false) }

    Column (
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        //Text(text = "The random number is $answer")
        Text(
            text = stringResource(R.string.try_to),
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            fontSize = 22.sp,
            modifier = Modifier
                .padding(all = 20.dp)
        )
        Spacer(Modifier.height(300.dp))
        if (!isGameWon) {
            TextField(
                label = { Text(stringResource(R.string.your_guess), color = Color.Gray) },
                value = input.toString(),
                onValueChange = { input = it.toIntOrNull() ?: 0;  isGuessing = false},
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number,imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(onDone = { input = input.toString().toIntOrNull() ?: 0
                    focusManager.clearFocus(); isGuessing = true }),
                colors = TextFieldDefaults.textFieldColors(backgroundColor = Color.Transparent)

            )
        }
        Spacer(Modifier.height(200.dp))
        if (input > answer && isGuessing) {
            timesTry++
            isGuessing = false

        }
        if (input > answer) {
            Text(
                text = stringResource(R.string.lower),
                textAlign = TextAlign.Center,
                fontSize = 22.sp,
                color = Color.Gray,
                modifier = Modifier
                    .padding(all = 20.dp)
            )
        }

        if (input < answer && input > 0 && isGuessing) {
            timesTry++
            isGuessing = false

        }
        if (input < answer && input > 0) {
            Text(
                text = stringResource(R.string.higher),
                textAlign = TextAlign.Center,
                color = Color.Gray,
                fontSize = 22.sp,
                modifier = Modifier
                    .padding(all = 20.dp)
            )
        }

        if (input == answer && isGuessing) {
            timesTry++
            isGuessing = false
            isGameWon = true

        }
        if (input == answer) {
            Text(
                text = "Answer is: $answer",
                fontSize = 24.sp,
                textAlign = TextAlign.Center
            )
            Spacer(Modifier.height(10.dp))
            Text(
                text = "Number of tries: $timesTry",
                fontSize = 18.sp,
                textAlign = TextAlign.Center
            )
            Spacer(Modifier.height(10.dp))
            Button(onClick = { answer = (1..1000).random(); input = 0; isGameWon = false; timesTry = 0 }) {
                Text(stringResource(R.string.play_again))
            }
        }
    }
}


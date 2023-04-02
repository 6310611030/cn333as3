package com.example.gameapp.ui

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun TicTacToe() {
    val turn = remember { mutableStateOf("X") }
    val button1 = remember { mutableStateOf("") }
    val button2 = remember { mutableStateOf("") }
    val button3 = remember { mutableStateOf("") }
    val button4 = remember { mutableStateOf("") }
    val button5 = remember { mutableStateOf("") }
    val button6 = remember { mutableStateOf("") }
    val button7 = remember { mutableStateOf("") }
    val button8 = remember { mutableStateOf("") }
    val button9 = remember { mutableStateOf("") }
    val isGameWin = remember { mutableStateOf(false) }
    val isGameDraw = remember { mutableStateOf(false) }

    if (turn.value == "X"){
        turn.value = "X"
    }else{
        turn.value = "O"
    }
    Column {
        Text(text = "Tic Tac Toe",
            textAlign = TextAlign.Center,
            fontSize = 60.sp,
            modifier = Modifier.fillMaxWidth())

        Spacer(modifier = Modifier.height(20.dp))

        Text(text = "${turn.value} Turn",
            textAlign = TextAlign.Center,
            fontSize = 30.sp,
            modifier = Modifier.fillMaxWidth())

        Spacer(modifier = Modifier.height(125.dp))

        Column(verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()) {
            Row(horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()) {
                Buttons(turn, button1)
                Buttons(turn, button2)
                Buttons(turn, button3)
            }
            Row(horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()) {
                Buttons(turn, button4)
                Buttons(turn, button5)
                Buttons(turn, button6)
            }
            Row(horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()) {
                Buttons(turn, button7)
                Buttons(turn, button8)
                Buttons(turn, button9)
            }
        }
    }
    GameLogic(button1,button2, button3, button4, button5, button6, button7, button8, button9, isGameWin, isGameDraw)
    if (isGameWin.value){
        GameOver(button1,button2, button3, button4, button5, button6, button7, button8, button9, turn, isGameWin, isGameDraw)
    }
}



@Composable
fun Buttons(turn: MutableState<String>, value: MutableState<String> ){
    val buttonText = remember { mutableStateOf("") }
    val clicked = remember { mutableStateOf(false) }
    if (value.value == ""){
        buttonText.value = ""
        clicked.value = false
    }
    Button(onClick = { if (turn.value == "X"){
        buttonText.value = "X"
        value.value  = "X"
        turn.value = "O"
        clicked.value = true
    } else {
        buttonText.value = "0"
        value.value  = "O"
        turn.value = "X"
        clicked.value = true
    }
    },
        enabled = !clicked.value,
        modifier = Modifier.size(130.dp),
        colors = ButtonDefaults.buttonColors(backgroundColor = Color.White, contentColor = Color.Black, disabledContentColor = Color.Black, disabledBackgroundColor = Color.White),
        border = BorderStroke(width = 1.dp, color = Color.Black),
        shape = RoundedCornerShape(8.dp))
    {
        Text(text = buttonText.value,
            fontSize = 70.sp)
    }
}

@Composable
fun GameLogic(button1: MutableState<String>,button2: MutableState<String>, button3: MutableState<String>,
              button4: MutableState<String>, button5: MutableState<String>, button6: MutableState<String>,
              button7: MutableState<String>, button8: MutableState<String>, button9: MutableState<String>,
              isGameWin: MutableState<Boolean>, isGameDraw: MutableState<Boolean> ){
    if (button1.value == button2.value && button2.value == button3.value){
        if(button1.value.isNotBlank() && button2.value.isNotBlank()){
            isGameWin.value = true
        }
    }
    else if(button4.value == button5.value && button5.value == button6.value){
        if(button4.value.isNotBlank() && button5.value.isNotBlank()){
            isGameWin.value = true
        }
    }
    else if(button7.value == button8.value && button8.value == button9.value){
        if(button7.value.isNotBlank() && button8.value.isNotBlank()){
            isGameWin.value = true
        }
    }
    else if(button1.value == button4.value && button4.value == button7.value){
        if(button1.value.isNotBlank() && button4.value.isNotBlank()){
            isGameWin.value = true
        }
    }
    else if(button2.value == button5.value && button5.value == button8.value){
        if(button2.value.isNotBlank() && button5.value.isNotBlank()){
            isGameWin.value = true
        }
    }
    else if(button3.value == button6.value && button6.value == button9.value){
        if(button3.value.isNotBlank() && button6.value.isNotBlank()){
            isGameWin.value = true
        }
    }
    else if(button1.value == button5.value && button5.value == button9.value){
        if(button1.value.isNotBlank() && button5.value.isNotBlank()){
            isGameWin.value = true
        }
    }
    else if(button3.value == button5.value && button5.value == button7.value){
        if(button3.value.isNotBlank() && button5.value.isNotBlank()){
            isGameWin.value = true
        }
    }
    else if (button1.value.isNotBlank() && button2.value.isNotBlank() && button3.value.isNotBlank() &&
        button4.value.isNotBlank() && button5.value.isNotBlank() && button6.value.isNotBlank() &&
        button7.value.isNotBlank() && button8.value.isNotBlank() && button9.value.isNotBlank()) {
        isGameWin.value = true
        isGameDraw.value = true
    }
}

@Composable
fun GameOver(button1: MutableState<String>,button2: MutableState<String>, button3: MutableState<String>,
             button4: MutableState<String>, button5: MutableState<String>, button6: MutableState<String>,
             button7: MutableState<String>, button8: MutableState<String>, button9: MutableState<String>,
             turn: MutableState<String>, isGameWin: MutableState<Boolean>, isGameDraw: MutableState<Boolean>) {
    val activity = (LocalContext.current as Activity)
    val title = if (isGameDraw.value) "Draw" else "Congratulations"
    val text: String = if (isGameDraw.value) {
        "Nobody wins"
    } else {
        "${if (turn.value == "X") "O" else "X"} is the winner!"
    }


    AlertDialog(
        onDismissRequest = {},
        title = { Text(title) },

        text = { Text(text) },
        dismissButton = {
            TextButton(
                onClick = {
                    activity.finish()
                }
            ) {
                Text(text = "exit")
            }
        },
        confirmButton = {
            TextButton(onClick = {
                button1.value = ""
                button2.value = ""
                button3.value = ""
                button4.value = ""
                button5.value = ""
                button6.value = ""
                button7.value = ""
                button8.value = ""
                button9.value = ""
                turn.value = "X"
                isGameWin.value = false
                isGameDraw.value = false
            }) {
                Text(text = "play again")
            }
        }
    )

}



//@Preview(showBackground = true)
//@Composable
//fun DefaultPreview() {
//    TicTacToeTheme {
//        TicTacToe()
//    }
//}
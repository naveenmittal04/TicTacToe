package com.naveenmittal.tictactoe.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ComposeNodeLifecycleCallback
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.naveenmittal.tictactoe.model.Player
import com.naveenmittal.tictactoe.model.PlayerType
import com.naveenmittal.tictactoe.ui.viewmodel.TicTacToeState
import com.naveenmittal.tictactoe.ui.viewmodel.TicTacToeViewModel

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun Home(ticTacToeViewModel: TicTacToeViewModel) {
    var ticTacToeState = ticTacToeViewModel.getTicTacToeState().collectAsState()
    when (ticTacToeState.value) {
        TicTacToeState.START -> {
            StartScreen(ticTacToeViewModel)
        }

        TicTacToeState.IN_PROGRESS -> {
            InProgressScreen(ticTacToeViewModel)
        }

        TicTacToeState.STOP -> {
            StopScreen(ticTacToeViewModel)
        }
    }
}

@Composable
fun StopScreen(ticTacToeViewModel: TicTacToeViewModel) {
    Text(text = ticTacToeViewModel.getWinner())
}

@Composable
fun InProgressScreen(ticTacToeViewModel: TicTacToeViewModel) {
    var x by remember { mutableStateOf("") }
    var y by remember { mutableStateOf("") }
    val currentPlayer = ticTacToeViewModel.getCurrentPlayer().collectAsState()
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        ticTacToeViewModel.printBoard()
        Text(text = "Current Player: ${currentPlayer.value?.getSymbol()}")
        TextField(value = x, onValueChange = {x = it}, label = {Text("Enter x")})
        TextField(value = y, onValueChange = {y = it}, label = {Text("Enter y")})
        Button(onClick = {
            ticTacToeViewModel.makeMove(x.toInt(), y.toInt())
        }) {
            Text("Make Move")
        }
    }
}

@Composable
fun StartScreen(ticTacToeViewModel: TicTacToeViewModel) {
    var size by remember { mutableStateOf("3") }
    val players = mutableListOf<Player>()
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        TextField(value = size, onValueChange = {size = it}, label = {Text("Enter size of board")})
        for (i in 0 until size.toInt()-1) {
            val player = Player(i, "X", PlayerType.HUMAN)
            players.add(player)
            PlayerRow(player = player, i = i, {player.setSymbol(it)}, {player.setPlayerType(it)})
        }
        Button(onClick = {
            ticTacToeViewModel.startGame(size.toInt(), players)
        }) {
            Text("Start")
        }
    }
}

@Composable
fun PlayerRow(player: Player, i: Int, callback: (String) -> Unit, callback2: (String) -> Unit) {
    var symbol by remember { mutableStateOf("") }
    var playerType by remember { mutableStateOf("") }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.wrapContentSize().fillMaxWidth()
    ) {
        Text("Player $i", Modifier.wrapContentSize())
        TextField(value = symbol,
            onValueChange = {
                symbol = it
                callback(it)
                            }, modifier = Modifier.wrapContentSize())
        TextField(value = playerType,
            onValueChange = {
                playerType = it
                callback2(it)
                            }, modifier = Modifier.wrapContentSize())
    }
}
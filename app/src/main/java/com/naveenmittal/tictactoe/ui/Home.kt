package com.naveenmittal.tictactoe.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Button
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Switch
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.naveenmittal.tictactoe.model.Bot
import com.naveenmittal.tictactoe.model.DifficultyLevel
import com.naveenmittal.tictactoe.model.Player
import com.naveenmittal.tictactoe.model.PlayerType
import com.naveenmittal.tictactoe.ui.viewmodel.TicTacToeState
import com.naveenmittal.tictactoe.ui.viewmodel.TicTacToeViewModel

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun Home(ticTacToeViewModel: TicTacToeViewModel) {
    val ticTacToeState = ticTacToeViewModel.getTicTacToeState().collectAsState()
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
    Box(
        contentAlignment = Alignment.Center
    ) {
        Text(text = ticTacToeViewModel.getWinner() ?: "", fontSize = 50.sp)
    }
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
        Text(text = "Current Player: ${currentPlayer.value?.getSymbol()}")
        PrintBoard(ticTacToeViewModel)
        if (currentPlayer.value?.isBot() == true) {
            ticTacToeViewModel.makeMove(0, 0)
        }
//        else {
//            TextField(value = x, onValueChange = {x = it}, label = {Text("Enter x")})
//            TextField(value = y, onValueChange = {y = it}, label = {Text("Enter y")})
//            Button(onClick = {
//                ticTacToeViewModel.makeMove(x.toInt(), y.toInt())
//            }) {
//                Text("Make Move")
//            }
//        }

    }
}

@Composable
fun StartScreen(ticTacToeViewModel: TicTacToeViewModel) {
    var size by remember { mutableStateOf("3") }
    var bot by remember { mutableStateOf(false) }
    val players = mutableListOf<Player>()
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        TextField(value = size, onValueChange = {size = it}, label = {Text("Enter size of board")})
        Row(
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "Play with bot")
            Switch(checked = bot, onCheckedChange = {bot = it} )
        }
        if (bot){
            val botPlayer = Bot(size.toInt()-2, DifficultyLevel.EASY, "O")
            players.add(botPlayer)
            BotRow(botPlayer,size.toInt()-2, {botPlayer.setSymbol(it)}, {botPlayer.setDifficultyLevel(it)})
        }
        for (i in 0 until size.toInt()-1 - if(bot) 1 else 0) {
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
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .wrapContentSize()
            .fillMaxWidth()
    ) {
        Text("Player $i", Modifier.wrapContentSize())
        TextField(value = symbol,
            onValueChange = {
                symbol = it
                callback(it)
                            }, modifier = Modifier.wrapContentSize())
    }
}

@Composable
fun BotRow(player: Bot, i: Int, callback: (String) -> Unit, callback2: (DifficultyLevel) -> Unit) {
    var symbol by remember { mutableStateOf("") }
    var difficultyLevel by remember { mutableStateOf(DifficultyLevel.EASY.name) }
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .wrapContentSize()
            .fillMaxWidth()
    ) {
        Text("Player $i", Modifier.wrapContentSize())
        TextField(value = symbol,
            onValueChange = {
                symbol = it
                callback(it)
            }, modifier = Modifier.wrapContentSize())
        SelectFromList(default = difficultyLevel, label = "Select Difficulty Level", options = DifficultyLevel.values().map { it.name }, callback = {
            difficultyLevel = it
            callback2(DifficultyLevel.valueOf(it))})
    }
}

@Composable
fun PrintBoard(ticTacToeViewModel: TicTacToeViewModel) {
    val board = ticTacToeViewModel.getBoard().getBoard()
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        for (i in 0 until board.size) {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                for (j in 0 until board.size) {
                    if(board[i][j].player == null){
                        Button(onClick = { ticTacToeViewModel.makeMove(i, j) }) {
                            Text(text = "-", fontSize = 30.sp)
                        }
                    } else {
                        board[i][j].player?.getSymbol()?.let {
                            Button(onClick = {  }) {
                                Text(text = it, fontSize = 30.sp)
                            }
                        }
                    }
                }
            }
        }
    }
}
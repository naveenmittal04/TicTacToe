package com.naveenmittal.tictactoe.ui

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Button
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ComposeNodeLifecycleCallback
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.naveenmittal.tictactoe.model.Bot
import com.naveenmittal.tictactoe.model.DifficultyLevel
import com.naveenmittal.tictactoe.model.Player
import com.naveenmittal.tictactoe.model.PlayerType
import com.naveenmittal.tictactoe.ui.viewmodel.TicTacToeState
import com.naveenmittal.tictactoe.ui.viewmodel.TicTacToeViewModel
import java.lang.Exception
import java.time.Duration

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

        TicTacToeState.Replay -> {
            ReplayScreen(ticTacToeViewModel)
        }
    }
}

@Composable
fun ReplayScreen(ticTacToeViewModel: TicTacToeViewModel) {
    val move_index = ticTacToeViewModel.getReplayIndex().collectAsState()
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        Text(text = "Game Replay", fontSize = 50.sp)
        PrintReplayBoard(ticTacToeViewModel, move_index)
        Button(onClick = {
            ticTacToeViewModel.nextMove()
        }) {
            Text("Next")
        }
        Button(onClick = {
            ticTacToeViewModel.prevMove()
        }) {
            Text("Previous")
        }
    }
}

@Composable
fun StopScreen(ticTacToeViewModel: TicTacToeViewModel) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        Text(text = ticTacToeViewModel.getWinner() ?: "", fontSize = 50.sp)
        Button(onClick = {
            ticTacToeViewModel.resetGame()
        }) {
            Text("Restart Game")
        }
        Button(onClick = {
            ticTacToeViewModel.replay()
        }) {
            Text(text = "Replay of Current Game")
        }
    }
}

@Composable
fun InProgressScreen(ticTacToeViewModel: TicTacToeViewModel) {
    val context = LocalContext.current
    val currentPlayer = ticTacToeViewModel.getCurrentPlayer().collectAsState()
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        Text(text = "Current Player: ${currentPlayer.value?.getSymbol()}")
        PrintBoard(ticTacToeViewModel)
        if (currentPlayer.value?.isBot() == true) {
            try {
                ticTacToeViewModel.makeMove(0, 0)
            } catch (e: Exception){
                Toast.makeText(context, "Invalid Move", Toast.LENGTH_SHORT).show()
            }
        }
        Button(onClick = {
            try {
                ticTacToeViewModel.undoMove()
            } catch (e: Exception){
                Toast.makeText(context, "No moves to undo", Toast.LENGTH_SHORT).show()
            }

        }) {
            Text("Undo Move")
        }
    }
}

@Composable
fun StartScreen(ticTacToeViewModel: TicTacToeViewModel) {
    val context = LocalContext.current
    var size by remember { mutableStateOf("3") }
    var size_int by remember { mutableStateOf(3) }
    var bot by remember { mutableStateOf(false) }
    val players = mutableListOf<Player>()
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        TextField(value = size, onValueChange = {size = it}, label = {Text("Enter size of board")})
        Button(onClick = {
                if (size.toInt() < 3 || size.toInt() > 5) {
                    Toast.makeText(context, "Size should be greater than 2 and less then 6", Toast.LENGTH_SHORT).show()
                } else {
                    size_int = size.toInt()
                }
            }
        ){
            Text("Set Size")
        }
        Row(
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "Play with bot")
            Switch(checked = bot, onCheckedChange = {bot = it} )
        }
        for (i in 0 until size_int -1 - if(bot) 1 else 0) {
            val player = Player(i, "X", PlayerType.HUMAN)
            players.add(player)
            PlayerRow(player = player, i = i, {player.setSymbol(it)}, {player.setPlayerType(it)})
        }
        if (bot){
            val botPlayer = Bot(size_int-2, DifficultyLevel.EASY, "O")
            players.add(botPlayer)
            BotRow(botPlayer,size_int-2, {botPlayer.setSymbol(it)}, {botPlayer.setDifficultyLevel(it)})
        }
        Button(onClick = {
            ticTacToeViewModel.startGame(size_int, players)
        }) {
            Text("Start")
        }
    }
}

@Composable
fun PlayerRow(player: Player, i: Int, callback: (String) -> Unit, callback2: (String) -> Unit) {
    val context = LocalContext.current
    var symbol by remember { mutableStateOf(player.getSymbol()) }
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
                if(it.length > 1) {
                    Toast.makeText(context, "Symbol should be of length 1", Toast.LENGTH_SHORT).show()
                } else {
                    symbol = it
                    callback(it)
                }
                            }, modifier = Modifier.wrapContentSize())
    }
}

@Composable
fun BotRow(player: Bot, i: Int, callback: (String) -> Unit, callback2: (DifficultyLevel) -> Unit) {
    var symbol by remember { mutableStateOf("O") }
    var difficultyLevel by remember { mutableStateOf(DifficultyLevel.EASY.name) }
    val conext = LocalContext.current
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .wrapContentSize()
            .fillMaxWidth()
    ) {
        Text("Bot Player $i", Modifier.wrapContentSize())
        TextField(value = symbol,
            onValueChange = {
                symbol = it
                callback(it)
            }, modifier = Modifier.wrapContentSize())
        SelectFromList(default = difficultyLevel, label = "Select Difficulty Level", options = listOf(DifficultyLevel.EASY.name), callback = {
            if(it != DifficultyLevel.EASY.name){
                Toast.makeText(conext, "Only Easy Level is Supported for now", Toast.LENGTH_SHORT).show()
            } else {
            difficultyLevel = it
            callback2(DifficultyLevel.valueOf(it))}
            })
    }
}

@Composable
fun PrintBoard(ticTacToeViewModel: TicTacToeViewModel) {
    val context = LocalContext.current
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
                        Button(onClick = { try {
                            ticTacToeViewModel.makeMove(i, j)
                        } catch (e: Exception){
                            Toast.makeText(context, "Invalid Move", Toast.LENGTH_SHORT).show()
                        } }, Modifier.padding(6.dp)) {
                            Text(text = "-", fontSize = 30.sp)
                        }
                    } else {
                        board[i][j].player?.getSymbol()?.let {
                            Button(onClick = {  }, Modifier.padding(6.dp)) {
                                Text(text = it, fontSize = 30.sp)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun PrintReplayBoard(ticTacToeViewModel: TicTacToeViewModel, move_index: State<Int>) {
    val board = ticTacToeViewModel.getReplayBoard()?.getBoard()!!
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Move ${move_index.value}", fontSize = 30.sp)
        for (i in board.indices) {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                for (j in board.indices) {
                    if (board[i][j].player == null) {
                        Button(onClick = { }, Modifier.padding(6.dp)) {
                            Text(text = "-", fontSize = 30.sp)
                        }
                    } else {
                        board[i][j].player?.getSymbol()?.let {
                            Button(onClick = { }, Modifier.padding(6.dp)) {
                                Text(text = it, fontSize = 30.sp)
                            }
                        }
                    }
                }
            }
        }
    }
}
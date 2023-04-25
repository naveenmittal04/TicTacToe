package com.naveenmittal.tictactoe

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.naveenmittal.tictactoe.ui.Home
import com.naveenmittal.tictactoe.ui.theme.TicTacToeTheme
import com.naveenmittal.tictactoe.ui.viewmodel.TicTacToeViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TicTacToeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    Home(ticTacToeViewModel = TicTacToeViewModel())
                }
            }
        }
    }
}

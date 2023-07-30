package com.lelestacia.lelenimev2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.lelestacia.lelenimev2.core.theme.LelenimeV2Theme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LelenimeV2Theme {
                LelenimeApp()
            }
        }
    }
}
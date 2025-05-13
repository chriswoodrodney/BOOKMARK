package com.chrisy.bookmark

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.chrisy.bookmark.ui.theme.BookTrackerTheme
import com.chrisy.bookmark.screens.BookTrackerNav

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BookTrackerTheme {
                BookTrackerNav()
            }
        }
    }
}
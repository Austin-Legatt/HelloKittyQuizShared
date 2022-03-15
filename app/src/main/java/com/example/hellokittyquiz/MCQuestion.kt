package com.example.hellokittyquiz

import androidx.annotation.StringRes

data class MCQuestion(@StringRes val textReId: Int, val questAnswer: Int, var answered: Boolean = false)
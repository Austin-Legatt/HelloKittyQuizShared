package com.example.hellokittyquiz

import androidx.lifecycle.ViewModel

class MCListViewModel: ViewModel() {
    val mcQuests = mutableListOf<MCQuestion>()

    init{
        TODO("Implement question initialization")
    }
}
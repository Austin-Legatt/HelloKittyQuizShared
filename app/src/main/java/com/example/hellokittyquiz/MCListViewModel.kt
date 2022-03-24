package com.example.hellokittyquiz

import androidx.lifecycle.ViewModel

class MCListViewModel: ViewModel() {
    val mcQuests = mutableListOf<MCQuestion>()

    init{
        mcQuests += MCQuestion(questText = "What is Hello Kitty's Astrology Sign?", questAnswers = arrayOf("Scorpio", "Gemini", "Pisces", "Taurus"), correctAnswer = 0)
    }
}
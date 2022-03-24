package com.example.hellokittyquiz

import androidx.lifecycle.ViewModel

class MCListViewModel: ViewModel() {
    val mcQuests = mutableListOf<MCQuestion>()

    init{
        mcQuests += MCQuestion(questText = "What is Hello Kitty's Astrology Sign?", questAnswers = arrayOf("Scorpio", "Gemini", "Pisces", "Taurus"), correctAnswer = 0)
        mcQuests += MCQuestion(questText = "What is Hello Kitty's last name?", questAnswers = arrayOf("Red", "Blue", "White", "Pink"), correctAnswer = 2)
        mcQuests += MCQuestion(questText = "How Many Actors Have Voice Acted as Hello Kitty?", questAnswers = arrayOf("1", "3", "5", "7"), correctAnswer = 1)
        mcQuests += MCQuestion(questText = "What Animal is Hello Kitty's Friend Cathy?", questAnswers = arrayOf("Bunny", "Cat", "Mouse", "Elephant"), correctAnswer = 0)

    }
}
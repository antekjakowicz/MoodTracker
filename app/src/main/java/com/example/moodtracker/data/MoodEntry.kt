package com.example.moodtracker.data

data class MoodEntry(
    var mood: String,
    var description: String,
    val foughtToday: Boolean,
    var usedSnus: Boolean,
    var rating: Int,
    val important: Boolean
)

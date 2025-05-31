package com.example.moodtracker.data

import java.util.UUID
import java.util.Date

data class Mood(
    val id: UUID = UUID.randomUUID(),
    val date: Date = Date(),
    val description: String,
    val mood: MoodType,
    val category: String,
    val sleptWell: Boolean,
    val wasActive: Boolean,
    val rating: Int,
    val isImportant: Boolean
)

enum class MoodType {
    HAPPY, NEUTRAL, SAD
}
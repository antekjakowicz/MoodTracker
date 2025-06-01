package com.example.moodtracker.viewmodel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.moodtracker.data.MoodEntry


class MoodViewModel : ViewModel() {
    private val _entries = MutableLiveData<MutableList<MoodEntry>>(mutableListOf())
    val entries: LiveData<MutableList<MoodEntry>> = _entries

    fun addEntry(entry: MoodEntry) {
        _entries.value?.add(entry)
        _entries.value = _entries.value
    }


    fun setEntries(newEntries: List<MoodEntry>) {
        _entries.value = newEntries.toMutableList()
    }


}
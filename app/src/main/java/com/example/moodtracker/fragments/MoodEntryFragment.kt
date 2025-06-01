 package com.example.moodtracker.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.moodtracker.R
import com.example.moodtracker.data.MoodEntry
import com.example.moodtracker.viewmodel.MoodViewModel

class MoodEntryFragment : Fragment() {

    private val viewModel: MoodViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.mood_entry, container, false)
    }

    @SuppressLint("UseSwitchCompatOrMaterialCode")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val rgMood = view.findViewById<RadioGroup>(R.id.rgMood)
        val etDescription = view.findViewById<EditText>(R.id.etDescription)
        val cbSleptWell = view.findViewById<CheckBox>(R.id.cbSleptWell)
        val cbWasActive = view.findViewById<CheckBox>(R.id.cbWasActive)
        val ratingBar = view.findViewById<RatingBar>(R.id.ratingBar)
        val switchImportant = view.findViewById<Switch>(R.id.switchImportant)
        val btnSave = view.findViewById<Button>(R.id.btnSave)

        btnSave.setOnClickListener {
            val selectedMoodId = rgMood.checkedRadioButtonId
            val moodText = view.findViewById<RadioButton>(selectedMoodId)?.text?.toString() ?: "Brak"
            val description = etDescription.text.toString()
            val foughtToday = cbSleptWell.isChecked
            val usedSnus = cbWasActive.isChecked
            val rating = ratingBar.rating.toInt()
            val important = switchImportant.isChecked

            val entry = MoodEntry(moodText, description, foughtToday, usedSnus, rating, important)
            viewModel.addEntry(entry)

            findNavController().navigate(R.id.historyFragment)
        }
    }}

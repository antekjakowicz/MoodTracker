package com.example.moodtracker.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.moodtracker.R
import com.example.moodtracker.data.MoodEntry
import com.example.moodtracker.viewmodel.MoodViewModel

class HistoryFragment : Fragment() {

    private val viewModel: MoodViewModel by activityViewModels()
    private lateinit var listView: ListView
    private lateinit var adapter: ArrayAdapter<String>

    private var selectedPosition: Int = -1
    private var currentEntries: List<MoodEntry> = emptyList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_history, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        listView = view.findViewById(R.id.listView)
        val btnDelete = view.findViewById<Button>(R.id.btnDelete)


        adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_list_item_activated_1,
            mutableListOf()
        )
        listView.adapter = adapter
        listView.choiceMode = ListView.CHOICE_MODE_SINGLE

        listView.setOnItemClickListener { _, _, position, _ ->
            selectedPosition = position
            listView.setItemChecked(position, true)
        }

        viewModel.entries.observe(viewLifecycleOwner) { entries ->
            currentEntries = entries
            adapter.clear()
            adapter.addAll(entries.map {
                "${it.mood} | ${it.description} | Snus: ${it.usedSnus} | Ocena: ${it.rating}"
            })
        }

        btnDelete.setOnClickListener {
            if (selectedPosition in currentEntries.indices) {
                val mutable = currentEntries.toMutableList()
                mutable.removeAt(selectedPosition)
                viewModel.setEntries(mutable)
                selectedPosition = -1
            } else {
                Toast.makeText(requireContext(), "Nie wybrano elementu", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
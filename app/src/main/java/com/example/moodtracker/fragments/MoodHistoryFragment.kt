package com.example.moodtracker.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moodtracker.R
import com.example.moodtracker.data.Mood
import com.example.moodtracker.data.MoodType
import java.text.SimpleDateFormat
import java.util.*

class MoodHistoryFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var moodAdapter: MoodAdapter
    private val moodList = mutableListOf<Mood>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_mood_history, container, false)

        recyclerView = view.findViewById(R.id.rvMoodHistory)
        recyclerView.layoutManager = LinearLayoutManager(requireContext()) //układ listy : D
        moodAdapter = MoodAdapter(moodList) //  Tworzymy adapter
        recyclerView.adapter = moodAdapter   // Podpinamy adapter do RecyclerView ....
        loadDefaultMoods() //Dodanie gotowych wpisów do listy

        return view
    }

    class MoodAdapter(private val moods: MutableList<Mood>) : RecyclerView.Adapter<MoodAdapter.MoodViewHolder>() {

        class MoodViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

            val moodImage: ImageView = itemView.findViewById(R.id.list_Image)
            val moodDate: TextView = itemView.findViewById(R.id.list_Name)
            val moodDescription: TextView = itemView.findViewById(R.id.list_info)
            val deleteButton: Button = itemView.findViewById(R.id.delete_item_list)
            val saveButton: Button = itemView.findViewById(R.id.saved_item_list)
            val ratingBar: RatingBar = itemView.findViewById(R.id.ratingBar)

            //przechowuje dane do widoków pojedynczego elementu
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoodViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_mood_entry, parent, false)
            return MoodViewHolder(view)
            // Tworzy widok dla jednego elementu listy
        }

        override fun onBindViewHolder(holder: MoodViewHolder, position: Int) {
            val mood = moods[position]
            val formatter = SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault())

            holder.moodDate.text = formatter.format(mood.date)
            holder.moodDescription.text = mood.description
            holder.ratingBar.rating = mood.rating.toFloat()

            //ustawienie ikonki w zależności od nastroju
            holder.moodImage.setImageResource(
                when (mood.mood) {
                    MoodType.HAPPY -> R.drawable.happyface
                    MoodType.NEUTRAL -> R.drawable.rollingeyes
                    MoodType.SAD -> R.drawable.sadface
                }
            )

            // Obsługa usuwania wpisu
            holder.deleteButton.setOnClickListener {
                moods.removeAt(position)
                notifyItemRemoved(position)
                notifyItemRangeChanged(position, moods.size)
                //button usuwa elemnty z listy informuje adapter o zmianie
            }

            // Obsługa przycisku zapisz
            holder.saveButton.setOnClickListener {
                Toast.makeText(holder.itemView.context, "Zapisano: ${mood.description}", Toast.LENGTH_SHORT).show()
            }

            // Obsługa zmiany gwiazdek
            holder.ratingBar.setOnRatingBarChangeListener { _, rating, _ ->
                moods[position] = moods[position].copy(rating = rating.toInt())
                // Obsługa zmian gwiazdek
            }
        }

        override fun getItemCount() = moods.size
    }

    // Dodawanie nowego wpisu
    fun updateList(
        description: String,
        mood: MoodType,
        category: String,
        sleptWell: Boolean,
        wasActive: Boolean,
        rating: Int,
        isImportant: Boolean
    ) {
        moodList.add(
            Mood(
                description = description,
                mood = mood,
                category = category,
                sleptWell = sleptWell,
                wasActive = wasActive,
                rating = rating,
                isImportant = isImportant
            )
        )
        moodAdapter.notifyDataSetChanged()
    }

    // Domyślne dane na start
    private fun loadDefaultMoods() {
        val defaultMoods = listOf(
            Mood(
                description = "Byłem dziś bardzo zadowolony – dostałem pizzę!",
                mood = MoodType.HAPPY,
                category = "Jedzenie",
                sleptWell = true,
                wasActive = false,
                rating = 4,
                isImportant = true
            ),
            Mood(
                description = "Dzień był przeciętny, nic ciekawego się nie działo.",
                mood = MoodType.NEUTRAL,
                category = "Zwykły dzień",
                sleptWell = false,
                wasActive = true,
                rating = 3,
                isImportant = false
            ),
            Mood(
                description = "Smutny dzień, przegapiłem mecz Realu.",
                mood = MoodType.SAD,
                category = "Sport",
                sleptWell = false,
                wasActive = false,
                rating = 2,
                isImportant = false
            )
        )

        moodList.addAll(defaultMoods)
        moodAdapter.notifyDataSetChanged()
    }
}

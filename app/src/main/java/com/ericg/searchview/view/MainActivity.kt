package com.ericg.searchview.view

import android.os.Bundle
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.ericg.searchview.R
import com.ericg.searchview.adapter.PersonAdapter
import com.ericg.searchview.databinding.ActivityMainBinding
import com.ericg.searchview.model.Person

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    private var people: ArrayList<Person> = arrayListOf()
    private var matchedPeople: ArrayList<Person> = arrayListOf()
    private var personAdapter: PersonAdapter = PersonAdapter(people)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        initRecyclerView()
        performSearch()
    }

    override fun onResume() {
        initRecyclerView()
        super.onResume()
    }

    private fun initRecyclerView() {

        people = arrayListOf(
            Person("Eric G", 19),
            Person("Reen", 19),
            Person("Jeff", 21),
            Person("Geoffrey", 19),
            Person("Lorem ipsum", 35),
            Person("Paul N", 23),
            Person("Diana", 20),
            Person("Peter", 24),
            Person("Amos", 41),
            Person("Steve", 17),
        )

        personAdapter = PersonAdapter(people).also {
            binding.recyclerView.adapter = it
            binding.recyclerView.adapter!!.notifyDataSetChanged()
        }
        binding.searchView.isSubmitButtonEnabled = true
    }

    private fun performSearch() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                search(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                search(newText)
                return true
            }
        })
    }

    private fun search(text: String?) {
        matchedPeople = arrayListOf()

        text?.let {
            people.forEach { person ->
                if (person.name.contains(text, true) ||
                    person.age.toString().contains(text, true)
                ) {
                    matchedPeople.add(person)
                    updateRecyclerView()
                }
            }
            if (matchedPeople.isEmpty()) {
                Toast.makeText(this, "No match found!", Toast.LENGTH_SHORT).show()
            }
            updateRecyclerView()
        }
    }

    private fun updateRecyclerView() {
        binding.recyclerView.apply {
            personAdapter.list = matchedPeople
            personAdapter.notifyDataSetChanged()
        }
    }
}
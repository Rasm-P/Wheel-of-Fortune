package com.example.wheeloffortune

import android.content.res.Resources
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.wheeloffortune.adapter.WordAdapter
import com.example.wheeloffortune.data.CategoryData

class WordGuessing : Fragment() {

    private val viewModel: WordGuessingViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_word_guessing, container, false)
        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view)
        val textView = view.findViewById<TextView>(R.id.category_title)
        textView.text = context?.getString(viewModel.currentCategory.categoryResourceId)

        recyclerView.adapter = context?.let { WordAdapter(it, viewModel.getPhraseWithRevealed()) }
        recyclerView.layoutManager = GridLayoutManager(context, 1)
        recyclerView.setHasFixedSize(true)

        return view
    }

}
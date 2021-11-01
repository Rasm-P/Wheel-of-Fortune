package com.example.wheeloffortune

import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
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
        textView.text = viewModel.currentCategory.categoryResource

        val wordsArr = viewModel.getWordsPerline()
        val layoutManager = GridLayoutManager(context, viewModel.getLogestWord())
        layoutManager.spanSizeLookup = object : SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                Log.d("RecyclerVIew", viewModel.getLogestWord().toString());
                Log.d("RecyclerVIewIndex", wordsArr[position].toString());
            Log.d("RecyclerVIewIndex", position.toString());
                return wordsArr[position]
            }
        }

        recyclerView.adapter = context?.let { WordAdapter(it, viewModel.getPhraseWithRevealed()) }
        recyclerView.layoutManager = GridLayoutManager(context, 10)
        recyclerView.setHasFixedSize(true)

        return view
    }
}
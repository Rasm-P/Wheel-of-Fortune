package com.example.wheeloffortune

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.wheeloffortune.adapter.WordAdapter
import com.example.wheeloffortune.databinding.FragmentWordGuessingBinding

class WordGuessing : Fragment() {

    private lateinit var binding: FragmentWordGuessingBinding
    private val viewModel: WordGuessingViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_word_guessing, container, false)
        val recyclerView = binding.root.findViewById<RecyclerView>(R.id.recycler_view)

        recyclerView.adapter = context?.let { WordAdapter(it, viewModel.getPhraseWithRevealed()) }
        recyclerView.layoutManager = GridLayoutManager(context, viewModel.wordPhrase.length)
        recyclerView.setHasFixedSize(true)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.wordGuessingViewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
    }
}
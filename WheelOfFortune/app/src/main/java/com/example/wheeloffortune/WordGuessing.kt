package com.example.wheeloffortune

import android.os.Bundle
import android.util.Log
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
import com.google.android.material.snackbar.Snackbar

class WordGuessing : Fragment() {

    private lateinit var binding: FragmentWordGuessingBinding
    private val viewModel: WordGuessingViewModel by viewModels()
    private var isGuessing = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_word_guessing, container, false)
        val recyclerView = binding.root.findViewById<RecyclerView>(R.id.recycler_view)

        var adapter = WordAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = GridLayoutManager(context, viewModel.wordPhrase.length)
        recyclerView.setHasFixedSize(true)

        viewModel.charListWithRevealed.observe(viewLifecycleOwner,
            { notes -> adapter.setCharList(notes) })

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.wordGuessingViewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        binding.spinTheWheel.setOnClickListener { onWheelSpin() }
        binding.submitPhrase.setOnClickListener { onSubmitGuessPhrase() }
        updateViewForGuessing()
    }

    private fun onSubmitGuessPhrase() {
        val guessPhrase = binding.textInput.text.toString()
        val firstChar = guessPhrase.first().lowercaseChar()
        Log.v("FirstChar", firstChar.toString())

        if (viewModel.wordPhrase == guessPhrase){
            setError(false)
            //Todo update this if won
            snack("You won!")
            viewModel.restartGame()
        } else if (viewModel.wordPhrase.contains(firstChar) && viewModel.doesNotContainChar(firstChar)) {
            setError(false)
            viewModel.addPoints()
            viewModel.addRevealedChar(firstChar)
            isGuessing = false
            updateViewForGuessing()
        } else {
            setError(true)
        }
    }

    private fun onWheelSpin() {
        isGuessing = true
        when ((0..13).random()) {
            1 -> {viewModel.setPointsToAdd(100)
                snack("Points: 100")}
            2 ->  {viewModel.setPointsToAdd(200)
                snack("Points: 200")}
            3 -> {viewModel.setPointsToAdd(300)
                snack("Points: 300")}
            4 -> {viewModel.setPointsToAdd(400)
                snack("Points: 400")}
            5 -> {viewModel.setPointsToAdd(500)
                snack("Points: 500")}
            6 -> {viewModel.setPointsToAdd(600)
                snack("Points: 600")}
            7 -> {viewModel.setPointsToAdd(700)
                snack("Points: 700")}
            8 -> {viewModel.setPointsToAdd(800)
                snack("Points: 800")}
            9 -> {viewModel.setPointsToAdd(900)
                snack("Points: 900")}
            10 -> {viewModel.setPointsToAdd(1000)
                snack("Points: 1000")}
            11 -> {snack("Extra turn!")
                isGuessing = false}
            12 -> {snack("Miss turn!")
                isGuessing = false}
            else -> {viewModel.bankrupt()
                snack("Bankrupt!")
                isGuessing = false}
        }
        updateViewForGuessing()
    }

    private fun snack(message: String, duration: Int = Snackbar.LENGTH_LONG) {
        Snackbar.make(binding.root, message, duration).show()
    }

    private fun updateViewForGuessing() {
        if (isGuessing) {
            binding.guessPhraseTextField.visibility = View.VISIBLE
            binding.textInput.visibility = View.VISIBLE
            binding.submitPhrase.visibility = View.VISIBLE
            binding.spinTheWheel.visibility = View.INVISIBLE
        } else {
            binding.guessPhraseTextField.visibility = View.INVISIBLE
            binding.textInput.visibility = View.INVISIBLE
            binding.submitPhrase.visibility = View.INVISIBLE
            binding.spinTheWheel.visibility = View.VISIBLE
        }
    }

    private fun setError(error: Boolean) {
        if (error) {
            binding.guessPhraseTextField.isErrorEnabled = true
            binding.textInput.error = getString(R.string.wrong_awnser)
        } else {
            binding.guessPhraseTextField.isErrorEnabled = false
            binding.textInput.text = null
        }
    }
}
package com.example.wheeloffortune.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.wheeloffortune.R
import com.example.wheeloffortune.adapters.WordAdapter
import com.example.wheeloffortune.databinding.FragmentWordGuessingBinding
import com.example.wheeloffortune.utils.SpacingDecorator
import com.example.wheeloffortune.viewModels.WordGuessingViewModel
import com.google.android.material.snackbar.Snackbar

// Made with inspiration from developer Unscramble app: https://developer.android.com/courses/pathways/android-basics-kotlin-unit-3-pathway-3
class WordGuessing : Fragment() {

    private lateinit var binding: FragmentWordGuessingBinding
    private val viewModel: WordGuessingViewModel by viewModels()
    private var isGuessing = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_word_guessing, container, false)
        val recyclerView = binding.root.findViewById<RecyclerView>(R.id.recycler_view)

        var adapter = WordAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = GridLayoutManager(context, 1)
        var decorator = SpacingDecorator(10)
        recyclerView.addItemDecoration(decorator)
        recyclerView.setHasFixedSize(true)

        viewModel.wordListWithRevealed.observe(viewLifecycleOwner,
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
        val guessChar = binding.textInput.text.toString()
        setError(false)

        if(guessChar.isNotEmpty()) {
            val firstChar = guessChar.first().lowercaseChar()
            if (viewModel.revealedCharacters.contains(firstChar)) {
                setError(true, R.string.letter_already_revealed)
            } else if (viewModel.wordPhrase.lowercase().contains(firstChar) && viewModel.doesNotContainChar(firstChar)) {
                viewModel.addPoints(firstChar)
                viewModel.addRevealedChar(firstChar)
                isGuessing = false
                updateViewForGuessing()
                snack("Letters revealed!")
            } else {
                viewModel.looseLife()
                isGuessing = false
                updateViewForGuessing()
                snack("The letter was not present!")
            }

            if (viewModel.isGameWon()) {
                gameWon()
            } else if (viewModel.isGameLost()) {
                gameLost()
            }

        } else {
            setError(true, R.string.field_cannot_be_empty)
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
            11 -> {viewModel.gainLife()
                snack("Extra turn!")
                isGuessing = false}
            12 -> {viewModel.looseLife()
                if (viewModel.isGameLost()) {
                    gameLost()
                }
                snack("Miss turn!")
                isGuessing = false}
            else -> {viewModel.bankrupt()
                snack("Bankrupt!")
                isGuessing = false}
        }
        updateViewForGuessing()
    }

    private fun gameWon() {
        val bundle = Bundle()
        viewModel.points.value?.let { bundle.putInt("points", it) }
        binding.root.findNavController().navigate(R.id.action_wordGuessing_to_gameWon, bundle)
        viewModel.restartGame()
    }

    private fun gameLost() {
        val bundle = Bundle()
        viewModel.points.value?.let { bundle.putInt("points", it) }
        binding.root.findNavController().navigate(R.id.action_wordGuessing_to_gameLost, bundle)
        viewModel.restartGame()
    }

    private fun snack(message: String, duration: Int = Snackbar.LENGTH_LONG) {
        Snackbar.make(binding.root, message, duration).show()
    }

    private fun updateViewForGuessing() {
        if (isGuessing) {
            binding.guessPhraseTextField.visibility = View.VISIBLE
            binding.textInput.visibility = View.VISIBLE
            binding.submitPhrase.visibility = View.VISIBLE
            binding.spinTheWheel.visibility = View.GONE
        } else {
            binding.guessPhraseTextField.visibility = View.GONE
            binding.textInput.visibility = View.GONE
            binding.submitPhrase.visibility = View.GONE
            binding.spinTheWheel.visibility = View.VISIBLE
        }
    }

    private fun setError(error: Boolean, stringId: Int = R.string.wrong_input) {
        if (error) {
            binding.guessPhraseTextField.isErrorEnabled = true
            binding.textInput.error = getString(stringId)
        } else {
            binding.guessPhraseTextField.isErrorEnabled = false
            binding.textInput.text = null
        }
    }
}
package com.example.wheeloffortune.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.DecelerateInterpolator
import android.view.animation.RotateAnimation
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView
import com.example.wheeloffortune.R
import com.example.wheeloffortune.adapters.WordAdapter
import com.example.wheeloffortune.databinding.FragmentWordGuessingBinding
import com.example.wheeloffortune.utils.SpacingDecorator
import com.example.wheeloffortune.viewModels.WordGuessingViewModel
import com.google.android.material.snackbar.Snackbar

//Built with inspiration from developer app Unscramble: https://developer.android.com/courses/pathways/android-basics-kotlin-unit-3-pathway-3
class WordGuessing : Fragment() {

    private lateinit var binding: FragmentWordGuessingBinding
    private val viewModel: WordGuessingViewModel by viewModels()
    private val sections = 24
    private var degree = 0
    private var sectorDegreesArr = IntArray(sections)
    private var isGuessing = false
    private lateinit var wheel: ImageView

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

        wheel = binding.wheel
        findDegreesForSections()

        viewModel.wordListWithRevealed.observe(viewLifecycleOwner,
            { notes -> adapter.setCharList(notes) })

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.wordGuessingViewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        binding.submitLetter.setOnClickListener { onSubmitGuessPhrase() }
        binding.spinTheWheel.setOnClickListener { onWheelSpin() }
        binding.rulesPage.setOnClickListener { showRulesPopup() }
        updateViewForGuessing()
    }

    //Takes the first char of the user submission and matches the letter with the phrase as well as other appropriate actions.
    private fun onSubmitGuessPhrase() {
        val guessChar = binding.textInput.text.toString()
        setError(false)

        if(guessChar.isNotEmpty() && guessChar.first()!=' ') {
            val firstChar = guessChar.first().lowercaseChar()
            if (viewModel.revealedCharacters.contains(firstChar)) {
                setError(true, R.string.letter_already_revealed)
            } else if (viewModel.wordPhrase.lowercase().contains(firstChar) && viewModel.doesNotContainChar(firstChar)) {
                viewModel.addPoints(firstChar)
                viewModel.addRevealedChar(firstChar)
                isGuessing = false
                updateViewForGuessing()
                snack(getString(R.string.letters_revealed))
            } else {
                viewModel.looseLife()
                isGuessing = false
                updateViewForGuessing()
                snack(getString(R.string.the_letter_was_not_present))
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

    //A switch case with all the possible outcomes when spinning the wheel
    private fun onWheelSpin() {
        isGuessing = true
        spinWheelAnimation()
        when (sections - degree) {
            1 -> {viewModel.setPointsToAdd(300)
                snack(getString(R.string.points_300))}
            2 ->  {viewModel.setPointsToAdd(200)
                snack(getString(R.string.points_200))}
            3 -> {viewModel.setPointsToAdd(100)
                snack(getString(R.string.points_100))}
            4 -> {viewModel.setPointsToAdd(500)
                snack(getString(R.string.points_500))}
            5 -> {viewModel.setPointsToAdd(400)
                snack(getString(R.string.points_400))}
            6 -> {viewModel.setPointsToAdd(300)
                snack(getString(R.string.points_300))}
            7 -> {viewModel.setPointsToAdd(200)
                snack(getString(R.string.points_200))}
            8 -> {viewModel.gainLife()
                snack(getString(R.string.extra_turn))
                isGuessing = false}
            9 -> {viewModel.setPointsToAdd(100)
                snack(getString(R.string.points_100))}
            10 -> {viewModel.setPointsToAdd(200)
                snack(getString(R.string.points_200))}
            11 -> {viewModel.setPointsToAdd(150)
                snack(getString(R.string.points_150))}
            12 -> {viewModel.setPointsToAdd(450)
                snack(getString(R.string.points_450))}
            13 -> {viewModel.looseLife()
                if (viewModel.isGameLost()) {
                    gameLost()
                }
                snack(getString(R.string.miss_turn))
                isGuessing = false}
            14 -> {viewModel.setPointsToAdd(400)
                snack(getString(R.string.points_400))}
            15 -> {viewModel.setPointsToAdd(250)
                snack(getString(R.string.points_250))}
            16 -> {viewModel.setPointsToAdd(200)
                snack(getString(R.string.points_200))}
            17 -> {viewModel.setPointsToAdd(150)
                snack(getString(R.string.points_150))}
            18 -> {viewModel.setPointsToAdd(400)
                snack(getString(R.string.points_400))}
            19 -> {viewModel.setPointsToAdd(600)
                snack(getString(R.string.points_600))}
            20 -> {viewModel.setPointsToAdd(250)
                snack(getString(R.string.points_250))}
            21 -> {viewModel.setPointsToAdd(300)
                snack(getString(R.string.points_300))}
            22 -> {viewModel.bankrupt()
                snack(getString(R.string.bankrupt))
                isGuessing = false}
            23 -> {viewModel.setPointsToAdd(750)
                snack(getString(R.string.points_750))}
            24 -> {viewModel.setPointsToAdd(250)
                snack(getString(R.string.points_250))}
        }
        updateViewForGuessing()
    }

    //Sets total points and phrase in a bundle, navigates to the gameWon view, and restarts the game settings
    private fun gameWon() {
        val bundle = Bundle()
        viewModel.points.value?.let { bundle.putInt("points", it) }
        viewModel.wordPhrase.let { bundle.putString("phrase", it) }
        binding.root.findNavController().navigate(R.id.action_wordGuessing_to_gameWon, bundle)
        viewModel.restartGame()
    }

    //Sets total points and phrase in a bundle, navigates to the gameLost view, and restarts the game settings
    private fun gameLost() {
        val bundle = Bundle()
        viewModel.points.value?.let { bundle.putInt("points", it) }
        viewModel.wordPhrase.let { bundle.putString("phrase", it) }
        binding.root.findNavController().navigate(R.id.action_wordGuessing_to_gameLost, bundle)
        viewModel.restartGame()
    }

    //Method for standard Snackbar messages
    private fun snack(message: String, duration: Int = Snackbar.LENGTH_LONG) {
        Snackbar.make(binding.root, message, duration).show()
    }

    //Method for updating the visibility of the submitLetter and spinTheWheel UI elements accordingly depending on if the user is currently guessing
    private fun updateViewForGuessing() {
        if (isGuessing) {
            binding.guessPhraseTextField.visibility = View.VISIBLE
            binding.textInput.visibility = View.VISIBLE
            binding.submitLetter.visibility = View.VISIBLE
            binding.spinTheWheel.visibility = View.GONE
        } else {
            binding.guessPhraseTextField.visibility = View.GONE
            binding.textInput.visibility = View.GONE
            binding.submitLetter.visibility = View.GONE
            binding.spinTheWheel.visibility = View.VISIBLE
        }
    }

    //Setts error for the submission text field and message accordingly depending on the input
    private fun setError(error: Boolean, stringId: Int = R.string.wrong_input) {
        if (error) {
            binding.guessPhraseTextField.isErrorEnabled = true
            binding.textInput.error = getString(stringId)
        } else {
            binding.guessPhraseTextField.isErrorEnabled = false
            binding.textInput.text = null
        }
    }

    //Made this spin animation with inspiration form https://youtu.be/5O2Uox-TR00
    //Applies the rotate animation for a random number of sections based on the circular degrees for each section
    private fun spinWheelAnimation() {
        degree = (0 until sections).random();
        var rotate = RotateAnimation(
            0F, ((360*sections) + sectorDegreesArr[degree]).toFloat(),
            RotateAnimation.RELATIVE_TO_SELF, 0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f)
        rotate.duration = 360
        rotate.fillAfter = true
        rotate.interpolator = DecelerateInterpolator()
        wheel.startAnimation(rotate)
    }

    //Counts the circular degrees for each section of the wheel
    private fun findDegreesForSections() {
        var sectorDegrees = 360/sections
        for (i in 0 until sections) {
            sectorDegreesArr[i] = (i+1)*sectorDegrees
        }
    }

    //Shows the game rules using a MaterialDialog popup with the game_rules_dialog view
    private fun showRulesPopup() {
        val dialog = context?.let { MaterialDialog(it).noAutoDismiss().customView(R.layout.game_rules_dialog) }
        dialog?.findViewById<TextView>(R.id.back_to_game)?.setOnClickListener { dialog.dismiss() }
        dialog?.show()
    }
}
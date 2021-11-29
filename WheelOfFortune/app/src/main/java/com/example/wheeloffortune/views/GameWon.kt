package com.example.wheeloffortune.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.findNavController
import com.example.wheeloffortune.R

class GameWon : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_game_won, container, false)
        var points = arguments?.let { it.getInt("points") }
        var phrase = arguments?.let { it.getString("phrase") }
        view.findViewById<TextView>(R.id.game_phrase).text = getString(R.string.game_phrase) + phrase
        view.findViewById<TextView>(R.id.game_points).text = getString(R.string.total_points) + points.toString()
        view.findViewById<TextView>(R.id.play_again_button).setOnClickListener { playAgain() }

        return view
    }

    private fun playAgain() {
        view?.findNavController()?.popBackStack()
    }
}
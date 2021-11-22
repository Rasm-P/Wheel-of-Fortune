package com.example.wheeloffortune

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.example.wheeloffortune.databinding.FragmentGameLostBinding
import com.example.wheeloffortune.databinding.FragmentWordGuessingBinding
import kotlin.properties.Delegates

class GameLost : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_game_lost, container, false)
        var points = arguments?.let { it.getInt("points") }
        view.findViewById<TextView>(R.id.game_points).text = points.toString()
        view.findViewById<TextView>(R.id.play_again_button).setOnClickListener { playAgain() }

        return view
    }

    private fun playAgain() {
        view?.findNavController()?.navigate(R.id.action_gameLost_to_wordGuessing)
    }
}
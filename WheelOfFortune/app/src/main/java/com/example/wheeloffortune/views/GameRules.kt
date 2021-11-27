package com.example.wheeloffortune.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.findNavController
import com.example.wheeloffortune.R

class GameRules : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_game_rules, container, false)
        view.findViewById<TextView>(R.id.back_to_game).setOnClickListener { backToGame() }
        return view
    }

    private fun backToGame() {
        view?.findNavController()?.popBackStack()
    }
}
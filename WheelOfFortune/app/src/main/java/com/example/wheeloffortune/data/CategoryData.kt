package com.example.wheeloffortune.data

import com.example.wheeloffortune.R
import com.example.wheeloffortune.model.Category

class CategoryData() {

    fun loadCategories(): List<Category> {
        return listOf<Category>(
            Category("Test", listOf("Testing phrase")),
            Category("Random", listOf("Randomized wordlist")),
            Category("Birthday", listOf("Birthday surprise")),
            Category("Humanity", listOf("Believing in humanity")),
            Category("Android", listOf("Android and kotlin"))
        )
    }
}
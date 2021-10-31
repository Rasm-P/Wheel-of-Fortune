package com.example.wheeloffortune.data

import com.example.wheeloffortune.R
import com.example.wheeloffortune.model.Category
import com.example.wheeloffortune.model.Phrase

class CategoryData() {

    fun loadCategories(): List<Category> {
        return listOf<Category>(
            Category(R.string.category1, listOf("Testing phrase")),
            Category(R.string.category2, listOf("Randomized wordlist")),
            Category(R.string.category3, listOf("Birthday surprise")),
            Category(R.string.category4, listOf("Believing in humanity")),
            Category(R.string.category5, listOf("Android and kotlin"))
        )
    }
}
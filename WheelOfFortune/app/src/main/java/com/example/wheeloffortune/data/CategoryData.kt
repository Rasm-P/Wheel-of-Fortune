package com.example.wheeloffortune.data

import com.example.wheeloffortune.models.Category

class CategoryData() {

    fun loadCategories(): List<Category> {
        return listOf(
            Category("Birthday", listOf("Birthday surprise party")),
            Category("Random", listOf("Randomized wordlist")),
            Category("Humanity", listOf("Believing in humanity")),
            Category("Android", listOf("Android and kotlin"))
        )
    }
}
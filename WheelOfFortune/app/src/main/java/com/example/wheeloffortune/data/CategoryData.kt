package com.example.wheeloffortune.data

import com.example.wheeloffortune.R
import com.example.wheeloffortune.model.Category

class CategoryData() {

    fun loadCategories(): List<Category> {
        return listOf<Category>(
            Category("Birthday", listOf("Birthday surprise"))
        )
    }
}
package com.example.wheeloffortune.data

import com.example.wheeloffortune.models.Category

class CategoryData() {

    fun loadCategories(): List<Category> {
        return listOf<Category>(
            Category("Birthday", listOf("Birthday surprise"))
        )
    }
}
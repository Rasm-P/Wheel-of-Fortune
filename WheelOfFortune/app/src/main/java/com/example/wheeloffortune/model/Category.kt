package com.example.wheeloffortune.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class Category(
    val categoryResource: String,
    val phraseList: List<String>
)
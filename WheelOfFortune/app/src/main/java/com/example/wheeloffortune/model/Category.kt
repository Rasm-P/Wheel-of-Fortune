package com.example.wheeloffortune.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class Category(
    @StringRes val categoryResourceId: Int,
    val phraseList: List<String>
)
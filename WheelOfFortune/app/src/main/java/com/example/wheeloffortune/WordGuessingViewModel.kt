package com.example.wheeloffortune

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.wheeloffortune.data.CategoryData
import com.example.wheeloffortune.model.Category


class WordGuessingViewModel() : ViewModel(){
    private val _points = MutableLiveData(0)
    val points: LiveData<Int>
        get() =_points

    private val _lives = MutableLiveData(5)
    val lives: LiveData<Int>
        get() =_lives

    private lateinit var _currentCategory: Category
    val currentCategory: Category
        get() =_currentCategory

    private var categoryList: List<Category> = CategoryData().loadCategories()
    private var revealedCharters: MutableList<Char> = ArrayList()
    private lateinit var wordPhrase: String

    init {
        getNextPhrase()
    }

    private fun getNextPhrase() {
        _currentCategory = categoryList.random()
        wordPhrase = _currentCategory.phraseList.random()
    }

    fun restartGame() {
        _points.value = 0
        _lives.value = 0
        revealedCharters.clear()
        getNextPhrase()
    }

    fun getPhraseWithRevealed(): List<Char> {
        var tempPhrase = wordPhrase
        for (char in wordPhrase) {
            if (char != ' ' && !revealedCharters.contains(char.lowercaseChar())) {
                tempPhrase = tempPhrase.replace(" ","")
            }
        }
        return tempPhrase.toList()
    }

    fun getLogestWord(): Int {
        val strArr = wordPhrase.split(" ")
        var maxLength = 0
        for (str in strArr) {
            if (str.length > maxLength) {
                maxLength = str.length
            }
        }
        return maxLength
    }

    fun getWordsPerline(): Array<Int> {
        val strArr = wordPhrase.split(" ")
        return strArr.map { it.length }.toTypedArray<Int>()
    }




}
package com.example.wheeloffortune

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.wheeloffortune.data.CategoryData
import com.example.wheeloffortune.model.Category


class WordGuessingViewModel() : ViewModel(){
    private var _categoryList: List<Category> = CategoryData().loadCategories()
    private var _revealedCharters: MutableList<Char> = ArrayList()
    private val _pointsToAdd = MutableLiveData(0)

    private val _points = MutableLiveData(0)
    val points: LiveData<Int>
        get() =_points

    private val _lives = MutableLiveData(5)
    val lives: LiveData<Int>
        get() =_lives

    private var _currentCategory = MutableLiveData<Category>()
    val currentCategory: LiveData<Category>
        get() = _currentCategory

    private lateinit var _wordPhrase: String
    val wordPhrase: String
        get() = _wordPhrase

    private val _charListWithRevealed = MutableLiveData<List<Char>>()
    val charListWithRevealed: LiveData<List<Char>>
         get() = _charListWithRevealed

    init {
        getNextPhrase()
        _charListWithRevealed.value = getPhraseWithRevealed().value
    }

    private fun getNextPhrase() {
        _currentCategory.value = _categoryList.random()
        _wordPhrase = _currentCategory.value!!.phraseList.random()
    }

    fun restartGame() {
        _points.value = 0
        _lives.value = 0
        _revealedCharters.clear()
        getNextPhrase()
        _charListWithRevealed.value = getPhraseWithRevealed().value
    }

    private fun getPhraseWithRevealed(): MutableLiveData<List<Char>> {
        var tempPhrase = _wordPhrase
        for (char in _wordPhrase) {
            if (char != ' ' && !_revealedCharters.contains(char.lowercaseChar())) {
                tempPhrase = tempPhrase.replace(char,'*')
            }
        }
        var tempMutableLive = MutableLiveData<List<Char>>()
        tempMutableLive.value = tempPhrase.toMutableList()
        return tempMutableLive
    }

    fun setPointsToAdd(pointsToAdd: Int) {
        _pointsToAdd.value = pointsToAdd
    }

    fun addPoints() {
        _points.value = _pointsToAdd.value?.let { _points.value?.plus(it) }
    }

    fun bankrupt() {
        _points.value = 0
    }

    fun addRevealedChar(char : Char) {
        _revealedCharters.add(char)
        Log.v("WheelLog", _revealedCharters.toString())
        _charListWithRevealed.value = getPhraseWithRevealed().value
        Log.v("WheelLog", _charListWithRevealed.value.toString())
    }

    fun doesNotContainChar(char : Char) : Boolean {
        for (c in _revealedCharters) {
            if (c == char) {
                return false
            }
        }
        return true
    }
}
package com.example.wheeloffortune.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.wheeloffortune.data.CategoryData
import com.example.wheeloffortune.models.Category

class WordGuessingViewModel() : ViewModel(){
    private var _categoryList: List<Category> = CategoryData().loadCategories()
    private val _pointsToAdd = MutableLiveData(0)

    private var _revealedCharacters: MutableList<Char> = ArrayList()
    val revealedCharacters: MutableList<Char>
        get() =_revealedCharacters

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
        _lives.value = 5
        _revealedCharacters.clear()
        getNextPhrase()
        _charListWithRevealed.value = getPhraseWithRevealed().value
    }

    private fun getPhraseWithRevealed(): MutableLiveData<List<Char>> {
        var tempPhrase = _wordPhrase
        for (char in _wordPhrase) {
            if (char != ' ' && !_revealedCharacters.contains(char.lowercaseChar())) {
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

    fun addPoints(letter: Char) {
        var count = _wordPhrase.lowercase().filter { it == letter }.count()
        var multiply = _pointsToAdd.value?.times(count)
        _points.value = multiply?.let { _points.value?.plus(it) }
    }

    fun bankrupt() {
        _points.value = 0
    }

    fun addRevealedChar(char : Char) {
        _revealedCharacters.add(char)
        _charListWithRevealed.value = getPhraseWithRevealed().value
    }

    fun doesNotContainChar(char : Char) : Boolean {
        for (c in _revealedCharacters) {
            if (c == char) {
                return false
            }
        }
        return true
    }

    fun gainLife() {
        _lives.value = _lives.value?.plus(1)
    }

    fun looseLife() {
        _lives.value = _lives.value?.minus(1)
    }

    fun isGameWon(): Boolean {
        if (getPhraseWithRevealed().value?.contains('*')!!) {
            return false
        }
        return true
    }

    fun isGameLost(): Boolean {
        if (_lives.value!! <= 0) {
            return true
        }
        return false
    }
}
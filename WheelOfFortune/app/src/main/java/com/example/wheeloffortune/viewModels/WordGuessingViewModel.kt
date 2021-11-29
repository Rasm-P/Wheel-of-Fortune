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

    private val _wordListWithRevealed = MutableLiveData<List<String>>()
    val wordListWithRevealed: LiveData<List<String>>
         get() = _wordListWithRevealed

    init {
        setNextPhrase()
        _wordListWithRevealed.value = getPhraseWithRevealed().value
    }

    //Sets the next random phrase from the _categoryList as the _wordPhrase
    private fun setNextPhrase() {
        _currentCategory.value = _categoryList.random()
        _wordPhrase = _currentCategory.value!!.phraseList.random()
    }

    //Method for restarting the game by resetting various field values
    fun restartGame() {
        _points.value = 0
        _lives.value = 5
        _revealedCharacters.clear()
        setNextPhrase()
        _wordListWithRevealed.value = getPhraseWithRevealed().value
    }

    //Returns the phrase as a list of livedata strings with the chars from the _revealedCharacters list revealed
    private fun getPhraseWithRevealed(): MutableLiveData<List<String>> {
        var tempPhrase = _wordPhrase
        for (char in _wordPhrase) {
            if (char != ' ' && !_revealedCharacters.contains(char.lowercaseChar())) {
                tempPhrase = tempPhrase.replace(char,'*')
            }
        }
        var tempMutableLive = MutableLiveData<List<String>>()
        tempMutableLive.value = tempPhrase.split(" ").toMutableList()
        return tempMutableLive
    }

    //Sets points that are to be added if the user reveals a letter
    fun setPointsToAdd(pointsToAdd: Int) {
        _pointsToAdd.value = pointsToAdd
    }

    //Adds points multiplied by the occurrences of the car in the current phrase
    fun addPoints(letter: Char) {
        var count = _wordPhrase.lowercase().filter { it == letter }.count()
        var multiply = _pointsToAdd.value?.times(count)
        _points.value = multiply?.let { _points.value?.plus(it) }
    }

    //Sets the user points to 0
    fun bankrupt() {
        _points.value = 0
    }

    //Adds a revealed char to the revealed chars list and reveals it in the wordList
    fun addRevealedChar(char : Char) {
        _revealedCharacters.add(char)
        _wordListWithRevealed.value = getPhraseWithRevealed().value
    }

    //Returns true if the _revealedCharacters list contains a specific char
    fun doesNotContainChar(char : Char) : Boolean {
        for (c in _revealedCharacters) {
            if (c == char) {
                return false
            }
        }
        return true
    }

    //Adds a life to the _lives field
    fun gainLife() {
        _lives.value = _lives.value?.plus(1)
    }

    //Removes a life from the _lives field
    fun looseLife() {
        _lives.value = _lives.value?.minus(1)
    }

    //Returns true if there are no more letters to be revealed
    fun isGameWon(): Boolean {
        if (_wordListWithRevealed.value?.filter { word -> word.contains("*") }!!.any()) {
            return false
        }
        return true
    }

    //Returns true if the _lives field is less or equal to 0
    fun isGameLost(): Boolean {
        if (_lives.value!! <= 0) {
            return true
        }
        return false
    }
}
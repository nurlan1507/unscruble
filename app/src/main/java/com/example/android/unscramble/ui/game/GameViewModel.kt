package com.example.android.unscramble.ui.game

import android.service.autofill.LuhnChecksumValidator
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GameViewModel:ViewModel() {
    private var _score = MutableLiveData(0)
    private var _currentWordCount = MutableLiveData(0)
    private val _currentScrambledWord = MutableLiveData<String>()
    private var wordsList: MutableList<String> = mutableListOf()
    private lateinit var currentWord:String

    fun reinitializeData() {
        _score.value = 0
        _currentWordCount.value = 0
        wordsList.clear()
        getNextWord()
    }

    //getters
    val currentScrambleWord:LiveData<String>
        get()=_currentScrambledWord

    val score :LiveData<Int>
        get() = _score

    val currentWordCount:LiveData<Int>
        get() = _currentWordCount


    init{
        Log.d("GameFragment", "GameViewModel created")
        getNextWord()
    }

    override fun onCleared() {
        super.onCleared()
        Log.d("GameFragment", "GameViewModel destroyed!")
    }

    fun getNextWord(){
        currentWord = allWordsList.random()
        val tempWord = currentWord.toCharArray()
        while(String(tempWord).equals(currentWord,false)){
            tempWord.shuffle()
        }
        if (wordsList.contains(currentWord)){
            getNextWord()
        }else{
            _currentScrambledWord.value = String(tempWord)
            _currentWordCount.value = _currentWordCount.value?.plus(1)
            wordsList.add(currentWord)
        }
    }

    public fun isUserWordCorrect(userWord:String):Boolean{
        if(userWord.equals(currentWord,true)){
            increaseScore()
            return true
        }
        return false
    }

    public fun nextWord ():Boolean{
        return if(_currentWordCount.value!! < MAX_NO_OF_WORDS){
            getNextWord()
            true
        }else false
    }

    public fun increaseScore(){
        _score.value = _score.value?.plus(SCORE_INCREASE)
        Log.d("GAMEVIEWMODEL", "PLUS ${score.value}")
    }


}
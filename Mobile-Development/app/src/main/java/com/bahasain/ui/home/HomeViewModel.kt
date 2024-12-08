package com.bahasain.ui.home

import androidx.lifecycle.ViewModel
import com.bahasain.data.VocabRepository

class HomeViewModel(private val repository: VocabRepository): ViewModel(){
    fun getWotd() = repository.getWotd()
}
package com.bahasain.ui.cultural

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bahasain.data.repository.CultureRepository

class CulturalViewModel(private val repository: CultureRepository): ViewModel() {
    fun getHistorical() = repository.getHistorical()

    fun getFolklore() = repository.getFolklore()
}
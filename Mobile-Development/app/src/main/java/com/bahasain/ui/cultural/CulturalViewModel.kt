package com.bahasain.ui.cultural

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bahasain.data.repository.CultureRepository

class CulturalViewModel(private val repository: CultureRepository) : ViewModel() {
    fun getHistorical() = repository.getHistorical()

    fun getDetailHistorical(historicalId: String) = repository.getDetailHistorical(historicalId)

    fun setHistoricalId(historicalId: String) = repository.getDetailHistorical(historicalId)

    fun getFolklore() = repository.getFolklore()

    fun getDetailFolklore(folkloreId: String) = repository.getDetailFolklore(folkloreId)

    fun setFolkloreId(folkloreId: String) = repository.getDetailFolklore(folkloreId)

    fun getRecipe() = repository.getRecipe()

    fun setRecipeId(recipeId: String) = repository.getDetailRecipe(recipeId)

    fun getDetailRecipe(recipeId: String) = repository.getDetailRecipe(recipeId)
}
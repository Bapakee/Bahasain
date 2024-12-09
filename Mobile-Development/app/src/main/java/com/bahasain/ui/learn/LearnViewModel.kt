package com.bahasain.ui.learn

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.bahasain.data.Result
import com.bahasain.data.remote.response.DataItemLearn
import com.bahasain.data.repository.ModuleRepository

class LearnViewModel(private val moduleRepository: ModuleRepository) : ViewModel() {
    fun getModule(): LiveData<Result<List<DataItemLearn?>?>>{
        return moduleRepository.getModule()
    }
}
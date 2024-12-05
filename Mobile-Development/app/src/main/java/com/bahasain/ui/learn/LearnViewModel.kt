package com.bahasain.ui.learn

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bahasain.data.ModuleRepository
import com.bahasain.data.Result
import com.bahasain.data.remote.response.DataItem

class LearnViewModel(private val moduleRepository: ModuleRepository) : ViewModel() {
    fun getModule(): LiveData<Result<List<DataItem?>?>>{
        return moduleRepository.getModule()
    }
}
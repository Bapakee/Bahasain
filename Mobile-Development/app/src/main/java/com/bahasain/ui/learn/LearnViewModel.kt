package com.bahasain.ui.learn

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.bahasain.data.Result
import com.bahasain.data.remote.request.ProgressRequest
import com.bahasain.data.remote.response.learn.DataItemScore
import com.bahasain.data.repository.ModuleRepository

class LearnViewModel(private val moduleRepository: ModuleRepository) : ViewModel() {
    fun getModule() =  moduleRepository.getModule()

    fun setModuleId(moduleId: String, moduleLevel: String) = moduleRepository.getQuiz(moduleId, moduleLevel)

    fun getQuiz(moduleId: String, moduleLevel: String) = moduleRepository.getQuiz(moduleId, moduleLevel)

    fun putProgress(moduleId: Int, levelId: Int, score: Int) = moduleRepository.putProgress(
        ProgressRequest(moduleId, levelId, score)
    )

    fun getScore(): LiveData<Result<DataItemScore?>?> {
        return moduleRepository.getScore().asLiveData()
    }
}
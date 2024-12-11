package com.bahasain.ui.learn

import androidx.lifecycle.ViewModel
import com.bahasain.data.repository.ModuleRepository

class LearnViewModel(private val moduleRepository: ModuleRepository) : ViewModel() {
    fun getModule() =  moduleRepository.getModule()

    fun setModuleId(moduleId: String, moduleLevel: String) = moduleRepository.getQuiz(moduleId, moduleLevel)

    fun getQuiz(moduleId: String, moduleLevel: String) = moduleRepository.getQuiz(moduleId, moduleLevel)
}
package com.bahasain.ui.placement

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.bahasain.data.repository.UserRepository
import com.bahasain.data.pref.UserModel
import com.bahasain.data.remote.request.LevelRequest
import kotlinx.coroutines.launch

class PlacementViewModel(private val repository: UserRepository): ViewModel() {

    fun setLevel(level: Int) = repository.setLevel(LevelRequest(level))


    fun getSession(): LiveData<UserModel> {
        return repository.getSession().asLiveData()
    }

    fun saveSession(userModel: UserModel) {
        viewModelScope.launch {
            repository.saveSession(userModel)
        }
    }
}
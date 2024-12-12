package com.bahasain.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.bahasain.data.Result
import com.bahasain.data.repository.UserRepository
import com.bahasain.data.pref.UserModel
import com.bahasain.data.remote.response.learn.DataItemScore
import com.bahasain.data.remote.response.user.ProfileResponse
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val userRepository: UserRepository
): ViewModel() {

    fun getProfile(): LiveData<Result<ProfileResponse?>?> {
        return userRepository.getProfile().asLiveData()
    }

    fun getSession(): LiveData<UserModel> {
        return userRepository.getSession().asLiveData()
    }

    fun logout() {
        viewModelScope.launch {
            userRepository.logout()
        }
    }
}
package com.bahasain.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.bahasain.data.repository.UserRepository
import com.bahasain.data.pref.UserModel
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val userRepository: UserRepository
): ViewModel() {

    fun getProfile() = userRepository.getProfile()

    fun getSession(): LiveData<UserModel> {
        return userRepository.getSession().asLiveData()
    }

    fun logout() {
        viewModelScope.launch {
            userRepository.logout()
        }
    }
}
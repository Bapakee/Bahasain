package com.bahasain.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.bahasain.data.UserRepository
import com.bahasain.data.pref.UserModel

class ProfileViewModel(
    private val userRepository: UserRepository
): ViewModel() {

    fun getSession(): LiveData<UserModel> {
        return userRepository.getSession().asLiveData()
    }
}
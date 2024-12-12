package com.bahasain.ui.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.bahasain.data.repository.UserRepository
import com.bahasain.data.pref.UserModel

class SplashViewModel(
    private val userRepository: UserRepository
): ViewModel() {

    fun getSession(): LiveData<UserModel> {
        return userRepository.getSession().asLiveData()
    }
}
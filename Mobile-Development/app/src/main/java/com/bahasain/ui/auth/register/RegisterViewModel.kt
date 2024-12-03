package com.bahasain.ui.auth.register

import androidx.lifecycle.ViewModel
import com.bahasain.data.UserRepository
import com.bahasain.data.remote.request.RegisterRequest

class RegisterViewModel(private val repository: UserRepository) : ViewModel() {
    fun register(name: String, email: String, password: String, confirmPassword: String) =
        repository.register(RegisterRequest(name, email, password, confirmPassword))
}
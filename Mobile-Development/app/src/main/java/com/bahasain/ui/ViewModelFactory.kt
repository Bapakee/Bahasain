package com.bahasain.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bahasain.data.ModuleRepository
import com.bahasain.data.UserRepository
import com.bahasain.data.VocabRepository
import com.bahasain.di.Injection
import com.bahasain.ui.auth.login.LoginViewModel
import com.bahasain.ui.auth.register.RegisterViewModel
import com.bahasain.ui.home.HomeViewModel
import com.bahasain.ui.learn.LearnViewModel
import com.bahasain.ui.placement.PlacementViewModel
import com.bahasain.ui.profile.ProfileViewModel
import com.bahasain.ui.splash.SplashViewModel
import com.bahasain.ui.vocab.VocabViewModel

class ViewModelFactory(
    private val userRepository: UserRepository,
    private val moduleRepository: ModuleRepository,
    private val vocabRepository: VocabRepository
) : ViewModelProvider.NewInstanceFactory(){

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T{
        return when{
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                LoginViewModel(userRepository) as T
            }

            modelClass.isAssignableFrom(SplashViewModel::class.java) -> {
                SplashViewModel(userRepository) as T
            }

            modelClass.isAssignableFrom(RegisterViewModel::class.java) -> {
                RegisterViewModel(userRepository) as T
            }

            modelClass.isAssignableFrom(ProfileViewModel::class.java) -> {
                ProfileViewModel(userRepository) as T
            }

            modelClass.isAssignableFrom(LearnViewModel::class.java) -> {
                LearnViewModel(moduleRepository) as T
            }

            modelClass.isAssignableFrom(PlacementViewModel::class.java) -> {
                PlacementViewModel(userRepository) as T
            }

            modelClass.isAssignableFrom(VocabViewModel::class.java) -> {
                VocabViewModel(vocabRepository) as T
            }
            
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> {
                HomeViewModel(vocabRepository) as T
            }

            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: ViewModelFactory? = null

        @JvmStatic
        fun getInstance(context: Context): ViewModelFactory {
            if (INSTANCE == null) {
                synchronized(ViewModelFactory::class.java) {
                    INSTANCE = ViewModelFactory(
                        Injection.provideUserRepository(context),
                        Injection.ProvideModuleRepository(context),
                        Injection.ProvideVocabRepository(context)
                    )
                }
            }
            return INSTANCE as ViewModelFactory
        }
    }
}
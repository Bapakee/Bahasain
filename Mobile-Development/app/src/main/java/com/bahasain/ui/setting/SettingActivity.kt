package com.bahasain.ui.setting

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.viewModels
import com.bahasain.data.Result
import com.bahasain.ui.ViewModelFactory
import com.bahasain.ui.auth.login.LoginActivity
import com.bahasain.ui.profile.ProfileViewModel
import com.bahasain.utils.AlarmManagerSetup
import com.dicoding.bahasain.R
import com.dicoding.bahasain.databinding.ActivitySettingBinding

class SettingActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingBinding

    private var notificationType: Int? = null

    private val viewModel: ProfileViewModel by viewModels {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        getProfile()

        val sharedPref = getSharedPreferences("settings", Context.MODE_PRIVATE)
        val isReminderEnabled = sharedPref.getBoolean("REMINDER_ENABLED", true)
        binding.switchNotif.isChecked = isReminderEnabled

        binding.switchNotif.setOnCheckedChangeListener{ _, isChecked ->
            val editor = sharedPref.edit()
            editor.putBoolean("REMINDER_ENABLED", isChecked)
            editor.apply()

            if (isChecked) {
                enableReminder()
            } else {
                disableReminder()
            }
        }

        binding.btnLogout.setOnClickListener{
            viewModel.logout()

            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }

    }

    private fun getProfile(){
        viewModel.getProfile().observe(this){ result ->
            if (result != null){
                when(result){
                    is Result.Loading -> {

                    }

                    is Result.Success -> {
                        binding.tvName.text = result.data?.data?.name
                        notificationType = result.data?.data?.notif
                    }

                    is Result.Error -> {

                    }
                }
            }
        }
    }

    private fun enableReminder() {
        notificationType?.let { AlarmManagerSetup.setDailyReminder(this, it) }
    }

    private fun disableReminder() {
        AlarmManagerSetup.cancelAllReminders(this)
    }
}
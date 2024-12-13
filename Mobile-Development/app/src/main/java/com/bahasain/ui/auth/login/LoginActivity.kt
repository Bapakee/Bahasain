package com.bahasain.ui.auth.login

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.auth0.android.jwt.JWT
import com.bahasain.data.Result
import com.bahasain.data.pref.UserModel
import com.bahasain.ui.MainActivity
import com.bahasain.ui.ViewModelFactory
import com.bahasain.ui.auth.register.RegisterActivity
import com.bahasain.ui.survey.SurveyActivity
import com.bahasain.ui.view.ButtonSign
import com.bahasain.ui.view.EditTextEmail
import com.bahasain.ui.view.EditTextPassword
import com.dicoding.bahasain.R
import com.dicoding.bahasain.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    private lateinit var edtTextEmail: EditTextEmail
    private lateinit var edtTextPassword: EditTextPassword

    private lateinit var btnSignIn: ButtonSign

    private val viewModel by viewModels<LoginViewModel> {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        btnSignIn = binding.btnSign
        edtTextEmail = binding.edtTextEmail
        edtTextPassword = binding.edtTextPassword

        setEdtText()
        setButtonEnable()

        btnSignIn.setOnClickListener { login() }
        binding.btnSignup.setOnClickListener { signupView() }
    }

    private fun login() {
        edtTextEmail = binding.edtTextEmail
        edtTextPassword = binding.edtTextPassword

        val email = edtTextEmail.text.toString()
        val password = edtTextPassword.text.toString()

        viewModel.login(email, password).observe(this) { result ->
            if (result != null) {
                when (result) {
                    is Result.Loading -> {
                        showLoading(true)
                    }

                    is Result.Success -> {
                        showLoading(false)
                        Toast.makeText(this, "Login Success", Toast.LENGTH_SHORT).show()

                        val accessToken = result.data.data?.accessToken
                        val refreshToken = result.data.data?.refreshToken

                        Log.d("token", "$accessToken")

                        val jwt = JWT(accessToken.toString())

                        val isNew = jwt.getClaim("isNew").asBoolean()

                        val userModel = UserModel(
                            accessToken.toString(),
                            refreshToken.toString()
                        )

                        viewModel.saveSession(userModel)

                        if(isNew == true){
                            val intent = Intent(this, SurveyActivity::class.java)
                            startActivity(intent)
                            finish()
                        }else{
                            val intent = Intent(this, MainActivity::class.java)
                            startActivity(intent)
                            finish()
                        }
                    }

                    is Result.Error -> {
                        showLoading(false)
                        Toast.makeText(this, result.error, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun setEdtText() {
        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                setButtonEnable()
            }

            override fun afterTextChanged(s: Editable?) {}

        }

        edtTextEmail.addTextChangedListener(textWatcher)
        edtTextPassword.addTextChangedListener(textWatcher)
    }

    private fun setButtonEnable() {
        val email = edtTextEmail.text?.toString().orEmpty()
        val password = edtTextPassword.text?.toString().orEmpty()

        val isEmailValid = android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()

        val isPasswordValid = password.length >= 8 && password.any { it.isUpperCase() }

        btnSignIn.isEnabled = isPasswordValid && isEmailValid
    }

    private fun signupView() {
        val intent = Intent(this, RegisterActivity::class.java)
        intent.flags =
            Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}
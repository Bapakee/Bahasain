package com.bahasain.ui.auth.login

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bahasain.data.Result
import com.bahasain.data.pref.UserModel
import com.bahasain.ui.ViewModelFactory
import com.bahasain.ui.auth.register.RegisterActivity
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

        btnSignIn.setOnClickListener{ login() }
        binding.btnSignup.setOnClickListener{ signupView() }
    }

    private fun login(){
        edtTextEmail = binding.edtTextEmail
        edtTextPassword = binding.edtTextPassword

        val email = edtTextEmail.text.toString()
        val password = edtTextPassword.text.toString()

        viewModel.login(email, password).observe(this){ result ->
            if (result != null){
                when(result){
                    is Result.Loading -> {

                    }
                    is Result.Success -> {
                        Toast.makeText(this, "Login Succes", Toast.LENGTH_SHORT).show()

                        val token = result.data.accessToken
                        viewModel.saveSession(UserModel(email, token, password))
                    }
                    is Result.Error -> {
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

        val isPasswordValid = password.length >= 8

        btnSignIn.isEnabled = isPasswordValid && isEmailValid
    }

    private fun signupView(){
        val intent = Intent(this, RegisterActivity::class.java)
        intent.flags =
            Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }
}
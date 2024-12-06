package com.bahasain.ui.auth.register

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bahasain.data.Result
import com.bahasain.ui.ViewModelFactory
import com.bahasain.ui.auth.login.LoginActivity
import com.bahasain.ui.auth.login.LoginViewModel
import com.bahasain.ui.view.ButtonSign
import com.bahasain.ui.view.EditTextEmail
import com.bahasain.ui.view.EditTextName
import com.bahasain.ui.view.EditTextPassword
import com.dicoding.bahasain.R
import com.dicoding.bahasain.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding

    private val viewModel by viewModels<RegisterViewModel> {
        ViewModelFactory.getInstance(this)
    }

    private lateinit var edtTextName: EditTextName
    private lateinit var edtTextEmail: EditTextEmail
    private lateinit var edtTextPassword: EditTextPassword
    private lateinit var btnSignUp: ButtonSign

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        edtTextName = binding.edtTextName
        edtTextEmail = binding.edtTextEmail
        edtTextPassword = binding.edtTextPassword
        btnSignUp = binding.btnRegister

        setEdtText()
        setButtonEnable()

        binding.btnRegister.setOnClickListener{ register() }

        binding.btnLogin.setOnClickListener{
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun register(){
        val name = binding.edtTextName.text
        val email = binding.edtTextEmail.text
        val password = binding.edtTextPassword.text
        val confirmPassword = binding.edtTextConfirmPassword.text

        viewModel.register(name.toString(), email.toString(), password.toString(), confirmPassword.toString())
            .observe(this){ result ->
                if (result != null){
                    when(result){
                        is Result.Loading -> {

                        }
                        is Result.Success -> {
                            Toast.makeText(this, "Register Success", Toast.LENGTH_SHORT).show()

                            val intent = Intent(this, LoginActivity::class.java)
                            startActivity(intent)
                            finish()
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
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                setButtonEnable()
            }

            override fun afterTextChanged(s: Editable?) { }

        }

        edtTextName.addTextChangedListener( textWatcher )
        edtTextEmail.addTextChangedListener(textWatcher)
        edtTextPassword.addTextChangedListener(textWatcher)
    }

    private fun setButtonEnable() {
        val name = edtTextName.text?.toString().orEmpty()
        val email = edtTextEmail.text?.toString().orEmpty()
        val password = edtTextPassword.text?.toString().orEmpty()

        val isNameValid = name.length >= 3

        val isEmailValid = android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()

        val isPasswordValid = password.length >= 8

        btnSignUp.isEnabled = isPasswordValid && isEmailValid && isNameValid
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}
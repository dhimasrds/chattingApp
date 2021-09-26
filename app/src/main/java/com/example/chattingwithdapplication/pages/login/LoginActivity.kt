package com.example.chattingwithdapplication.pages.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.chattingwithdapplication.pages.home.HomeActivity
import com.example.chattingwithdapplication.R
import com.example.chattingwithdapplication.databinding.ActivityLoginBinding
import com.example.chattingwithdapplication.pages.sign_up.SignUpActivity
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var viewModel: LoginViewModel
    private lateinit var auth : FirebaseAuth
    private var password : String? = null
    private var email : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
         binding  = DataBindingUtil.setContentView(this, R.layout.activity_login)
         viewModel  = ViewModelProvider(this).get(LoginViewModel::class.java)

        auth = FirebaseAuth.getInstance()

        binding.apply {
            lifecycleOwner = this@LoginActivity
            vm = viewModel
        }



        listener()
    }

   private fun listener(){
        binding.apply {
            includeLoginContent.btnLogin.setOnClickListener {
               password =  includeLoginContent.password.text.toString()
                email = includeLoginContent.email.text.toString()
                if (email.isNullOrEmpty() || password.isNullOrEmpty()){
                    Toast.makeText(this@LoginActivity,"Email or password cannot be empty", Toast.LENGTH_SHORT).show()
                }else{
                    login(email!!,password!!)
                }
            }

            binding.includeLoginContent.signup.setOnClickListener {
                goToSignUp()
            }
        }
    }



    private fun login (email : String, password : String){
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    val intent = Intent(this, HomeActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(this,"User does not exist", Toast.LENGTH_SHORT).show()

                }
            }
    }
    private fun goToSignUp(){
        Log.d("listener", "go to sign up")
        val intent = Intent(this, SignUpActivity::class.java)
        startActivity(intent)
        finish()
    }
}
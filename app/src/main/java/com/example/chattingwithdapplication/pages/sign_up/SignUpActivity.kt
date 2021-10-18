package com.example.chattingwithdapplication.pages.sign_up

import android.app.Activity
import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.chattingwithdapplication.R
import com.example.chattingwithdapplication.databinding.ActivitySignUpBinding
import com.example.chattingwithdapplication.entity.Users
import com.example.chattingwithdapplication.pages.login.LoginActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import java.util.*

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    private lateinit var viewModel: SignUpViewModel
    private lateinit var auth: FirebaseAuth
    private var password: String? = null
    private var email: String? = null
    private var username: String? = null
    private var uriPhoto: Uri? = null
    private lateinit var users : Users


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_up)
        viewModel = ViewModelProvider(this).get(SignUpViewModel::class.java)

            binding.apply {
            lifecycleOwner = this@SignUpActivity
            vm = viewModel
        }


        auth = FirebaseAuth.getInstance()
        listener()
    }

    private fun listener() {
        binding.apply {
            includeLoginContent.btnLogin.setOnClickListener {
                password = includeLoginContent.password.text.toString()
                email = includeLoginContent.email.text.toString()
                username = includeLoginContent.username.text.toString()
                if (email.isNullOrEmpty() || password.isNullOrEmpty()){
                    Toast.makeText(this@SignUpActivity,"Email or password cannot be empty", Toast.LENGTH_SHORT).show()
                }else{
                    signUp(email!!,password!!)
                }
                Log.d("listener", password.toString())
                Log.d("listener", email.toString())
                signUp(email!!, password!!)
            }

            btnSelectPhoto.setOnClickListener {
                val intent = Intent(Intent.ACTION_PICK)
                intent.type = "image/*"
                startActivityForResult(intent, 0)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 0 && resultCode == Activity.RESULT_OK && data != null){
             uriPhoto = data.data
            val bitmap = MediaStore.Images.Media.getBitmap(contentResolver,uriPhoto)

            binding.ivSelectPhoto.setImageBitmap(bitmap)
            binding.btnSelectPhoto.alpha =0f
//            val bitmapDrawable = BitmapDrawable(bitmap)
//            binding.btnSelectPhoto.setBackgroundDrawable(bitmapDrawable)
        }
    }

    fun signUp(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("signup", "createUserWithEmail:success")
                    val user = auth.currentUser
                    uploadImageToFireBase()
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                    finish()

                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("signup", "createUserWithEmail:failure", task.exception)
                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun uploadImageToFireBase(){
        val fileName = UUID.randomUUID().toString()
        Log.d("uploadImageToFireBase", fileName)
        val ref = FirebaseStorage.getInstance().getReference("/images/$fileName")

        ref.putFile(uriPhoto!!).addOnSuccessListener { it ->
            Log.d("uploadImageToFireBase","Succsessfully uploade image : ${it.metadata?.path}")

           ref.downloadUrl.addOnSuccessListener {imageUrl  ->
               saveUserToDatabase(imageUrl.toString())
           }
        }
    }

    private fun saveUserToDatabase(imageUrl : String){
        val uid = FirebaseAuth.getInstance().uid
        val ref = FirebaseDatabase.getInstance().getReference("/Users/$uid")

        users = Users()
        users.username = username.toString()
        users.email = email.toString()
        users.uid = uid.toString()
        users.uriUrl = imageUrl

        ref.setValue(users).addOnSuccessListener {
            Log.d("uploadImageToFireBase","Succsessfully uploade user")

        }
    }
}
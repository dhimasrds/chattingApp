package com.example.chattingwithdapplication.pages.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.chattingwithdapplication.R
import com.example.chattingwithdapplication.databinding.ActivityMainBinding
import com.example.chattingwithdapplication.entity.Users
import java.util.concurrent.ScheduledThreadPoolExecutor

class HomeActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    private lateinit var viewModel: HomeViewModel
        private lateinit var usersList : ArrayList<Users>
    private lateinit var userListAdapter: UserListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        binding.apply {
            lifecycleOwner = this@HomeActivity
            vm = viewModel
        }

        usersList = ArrayList()

        val loopCount = 10

        var sum = 0

        for(i in 1 .. loopCount){
            val users = Users()
            users.email = "email $i"
                users.username = "username $i"
                users.uriUrl = "url $i"
            Log.d("adapter", "count  $i")
            Log.d("adapter", users.email.toString())
            usersList.add(users)
        }

        userListAdapter = UserListAdapter(this,usersList)
        binding.recycleView.adapter = userListAdapter


    }
}
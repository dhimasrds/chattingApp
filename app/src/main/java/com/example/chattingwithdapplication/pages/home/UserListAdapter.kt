package com.example.chattingwithdapplication.pages.home

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.chattingwithdapplication.databinding.UserCardviewBinding
import com.example.chattingwithdapplication.entity.Users

/**
 * Created by Dhimas Saputra on 26/09/21
 * Jakarta, Indonesia.
 */
class UserListAdapter(
    private val context: Context,
    private val usersList: List<Users>
) : RecyclerView.Adapter<UserListAdapter.UserViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        return UserViewHolder(
            UserCardviewBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val userList = usersList[position]
        holder.bind(userList)
    }

    override fun getItemCount(): Int {
        return usersList.size
    }


    inner class UserViewHolder(private val binding: UserCardviewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("NewApi")
        fun bind(user: Users) {
            Log.d("adapter user", user.email.toString())
            binding.apply {
                username.text = user.username
                tvMessage.text = user.email
                tvDate.text = user.uriUrl
            }

        }

    }
}


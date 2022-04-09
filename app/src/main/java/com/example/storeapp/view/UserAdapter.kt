package com.example.storeapp.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.storeapp.R
import com.example.storeapp.databinding.UserItemBinding
import com.example.storeapp.model.User

class UserAdapter(private var users: ArrayList<User>) :
    RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    class UserViewHolder(private val binding: UserItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(user: User) {
            binding.user = user
        }
    }

    fun refresh(users: ArrayList<User>) {
        this.users = users
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val inflate = LayoutInflater.from(parent.context)
        val binding: UserItemBinding =
            DataBindingUtil.inflate(inflate, R.layout.user_item, parent, false)
        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(users[position])
    }

    override fun getItemCount(): Int = users.size
}
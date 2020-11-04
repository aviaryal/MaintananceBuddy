package com.example.maintanancebuddy.Views

import com.example.maintanancebuddy.Models.User
import com.example.maintanancebuddy.R
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Item
import kotlinx.android.synthetic.main.user_row_message.view.*

class UserItem(val user: User): Item<GroupieViewHolder>()
{
    override fun bind(viewHolder: GroupieViewHolder, position: Int) {

        viewHolder.itemView.username_new_message.text= user.fname +" "+ user.lname
    }

    override fun getLayout(): Int {

        return R.layout.user_row_message
    }
}
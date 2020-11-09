package com.letsbuildthatapp.kotlinmessenger.views

import com.example.maintanancebuddy.Models.User
import com.example.maintanancebuddy.R
import com.squareup.picasso.Picasso
import com.xwray.groupie.Item
import com.xwray.groupie.GroupieViewHolder
import kotlinx.android.synthetic.main.chat_from_row.view.*
import kotlinx.android.synthetic.main.chat_to_row.view.*

class ChatFromItem(val text: String, val user: User): Item<GroupieViewHolder>() {
    override fun bind(GroupieViewHolder: GroupieViewHolder, position: Int) {
        GroupieViewHolder.itemView.textview_from_row.text = text

        // val uri = user.profileImageUrl
        //val targetImageView = GroupieViewHolder.itemView.imageview_chat_from_row
        //Picasso.get().load(uri).into(targetImageView)
    }

    override fun getLayout(): Int {
        return R.layout.chat_from_row
        //return R.layout.chat_to_row
    }
}

class ChatToItem(val text: String, val user: User): Item<GroupieViewHolder>() {
    override fun bind(GroupieViewHolder: GroupieViewHolder, position: Int) {
        GroupieViewHolder.itemView.textview_to_row.text = text

        // load our user image into the star
        // val uri = user.profileImageUrl
        //val targetImageView = GroupieViewHolder.itemView.imageview_chat_to_row
        // Picasso.get().load(uri).into(targetImageView)
    }

    override fun getLayout(): Int {
        return R.layout.chat_to_row
        //return  R.layout.chat_from_row
    }
}
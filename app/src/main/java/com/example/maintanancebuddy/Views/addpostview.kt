package com.example.maintanancebuddy.Views

import com.example.maintanancebuddy.Models.User
import com.example.maintanancebuddy.Models.addPostclass
import com.example.maintanancebuddy.R
import com.squareup.picasso.Picasso
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Item
import kotlinx.android.synthetic.main.addhomepage_post.view.*
import kotlinx.android.synthetic.main.notification_view_repair.view.*
import kotlinx.android.synthetic.main.user_row_message.view.*

class addPostView(val newpost: addPostclass): Item<GroupieViewHolder>()
{
    override fun bind(viewHolder: GroupieViewHolder, position: Int) {

        viewHolder.itemView.homepage_add_post_description.text= newpost.descr
        viewHolder.itemView.image_post_homepage
        val uri = newpost.photouri
        val targetImageView = viewHolder.itemView.image_post_homepage
        Picasso.get().load(uri).into(targetImageView)
    }

    override fun getLayout(): Int {

        return R.layout.addhomepage_post
    }
}
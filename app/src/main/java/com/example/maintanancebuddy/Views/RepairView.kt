package com.example.maintanancebuddy.Views

import android.util.Log
import com.example.maintanancebuddy.Models.Maintanance_detail
import com.example.maintanancebuddy.Models.User
import com.example.maintanancebuddy.R
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Item
import kotlinx.android.synthetic.main.chat_from_row.view.*
import kotlinx.android.synthetic.main.repair_row_view.view.*

class RepairItem(var repair: Maintanance_detail): Item<GroupieViewHolder>() {
    override fun bind(GroupieViewHolder: GroupieViewHolder, position: Int) {
        GroupieViewHolder.itemView.textview_repair_titile.text =repair.type
        Log.d("RepairItem",repair.type)
        // val uri = user.profileImageUrl
        //val targetImageView = GroupieViewHolder.itemView.imageview_chat_from_row
        //Picasso.get().load(uri).into(targetImageView)
    }

    override fun getLayout(): Int {
        return R.layout.repair_row_view
        //return R.layout.chat_to_row
    }
}

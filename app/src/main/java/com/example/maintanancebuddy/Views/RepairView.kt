package com.example.maintanancebuddy.Views

import android.graphics.Color
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

        if(repair.status=="requested")
        {
            GroupieViewHolder.itemView.textview_repair_status.setTextColor(Color.parseColor("#FF0000"))

        }
        else if(repair.status=="completed")
            GroupieViewHolder.itemView.textview_repair_status.setTextColor(Color.parseColor("#00FF00"))
        else if(repair.status=="ongoing")
        {
            GroupieViewHolder.itemView.textview_repair_status.setTextColor(Color.parseColor("#FFFF00"))

        }
        GroupieViewHolder.itemView.textview_repair_status.text= repair.status

        Log.d("RepairItem",repair.type)

    }

    override fun getLayout(): Int {
        return R.layout.repair_row_view
        //return R.layout.chat_to_row
    }
}

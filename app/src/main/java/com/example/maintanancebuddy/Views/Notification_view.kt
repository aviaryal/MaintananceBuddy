package com.example.maintanancebuddy.Views

import android.accessibilityservice.GestureDescription
import android.graphics.Color
import android.util.Log
import com.example.maintanancebuddy.Models.Maintanance_detail
import com.example.maintanancebuddy.Models.User
import com.example.maintanancebuddy.R
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Item
import kotlinx.android.synthetic.main.chat_from_row.view.*
import kotlinx.android.synthetic.main.notification_view_repair.view.*
import kotlinx.android.synthetic.main.repair_row_view.view.*
import kotlinx.android.synthetic.main.repair_row_view.view.textview_repair_status

class Notification_view(var repair: Maintanance_detail): Item<GroupieViewHolder>() {
    override fun bind(GroupieViewHolder: GroupieViewHolder, position: Int) {

        var value:String=""
        if(repair.status=="requested")
        {
            GroupieViewHolder.itemView.notification_in_row.setTextColor(Color.parseColor("#FF0000"))
            value= "You submitted a request for  ${repair.type.toString()} "
        }
        else if(repair.status=="completed") {
            GroupieViewHolder.itemView.notification_in_row.setTextColor(Color.parseColor("#00FF00"))
            value="Your request for maintenance of ${repair.type.toString()} is competed"
        }
        else if(repair.status=="ongoing")
        {
            GroupieViewHolder.itemView.notification_in_row.setTextColor(Color.parseColor("#FFFF00"))
            value="Your request for maintenance of ${repair.type.toString()} is ongoing"
        }
        GroupieViewHolder.itemView.notification_in_row.text= value

        Log.d("RepairItem",repair.type)

    }

    override fun getLayout(): Int {
        return R.layout.notification_view_repair
        //return R.layout.chat_to_row
    }
}

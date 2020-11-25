package com.example.maintanancebuddy.Models

import android.os.Parcelable
import com.google.firebase.Timestamp
import kotlinx.android.parcel.Parcelize

@Parcelize
class Maintanance_detail(val id: String, val uid: String, val aptno:String, val type: String, val locat:String, val descr:String,val photouri: String, val videouri:String, val status:String, val timestamp :Long ) :
    Parcelable {
    constructor(): this ("","","", "","","","","", "",-1)
}
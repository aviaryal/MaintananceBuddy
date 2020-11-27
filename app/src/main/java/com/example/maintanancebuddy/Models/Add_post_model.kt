package com.example.maintanancebuddy.Models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class addPostclass( val id: String,  val descr:String,val photouri: String,  val timestamp :Long ) :
    Parcelable {
    constructor(): this ("","", "",-1)
}
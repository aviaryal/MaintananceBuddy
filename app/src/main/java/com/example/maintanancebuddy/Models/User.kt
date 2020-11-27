package com.example.maintanancebuddy.Models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
/*
@IgnoreExtraProperties
public data class User(
    var Uid:String?="",
    var Fname:String?="",
    var Lname:String?="",
    var Email:String?="",
    var Aptno:String?="",
    var isAdmin: Int?=0

)

 */


@Parcelize
class User(val uid: String, val fname: String, val lname: String, val email: String,val aptno: String, val cellphone:String, var isAdmin: Int): Parcelable {
    constructor() : this("", "", "", "","","",0)
}

@Parcelize
class emergencyContact(val uid: String, val name: String,  val relationship: String,val cellphone:String): Parcelable {
    constructor() : this("", "", "", "")
}
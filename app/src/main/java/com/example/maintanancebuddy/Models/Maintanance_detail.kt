package com.example.maintanancebuddy.Models

import com.google.firebase.Timestamp

class Maintanance_detail(val id: String, val uid: String, val aptno:String, val type: String, val locat:String, val descr:String,val photouri: String, val videouri:String, val status:String, val timestamp :Long )
{
    constructor(): this ("","","", "","","","","", "",-1)
}
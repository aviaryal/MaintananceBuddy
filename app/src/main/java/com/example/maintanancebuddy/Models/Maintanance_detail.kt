package com.example.maintanancebuddy.Models

import com.google.firebase.Timestamp

class Maintanance_detail(val id: String, val uid: String, val type: String, val status:String, val timestamp :Long )
{
    constructor(): this ("","","", "",-1)
}
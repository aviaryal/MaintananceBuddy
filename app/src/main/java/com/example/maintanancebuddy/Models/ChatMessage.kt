package com.example.maintanancebuddy.Models



class ChatMessage(val id: String, val text: String, val fromId: String, val toID: String, val timestamp: Long) {
    constructor() : this("", "", "", "", -1)
}
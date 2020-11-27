package com.example.maintanancebuddy

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.maintanancebuddy.Models.ChatMessage
import com.example.maintanancebuddy.Models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.letsbuildthatapp.kotlinmessenger.views.ChatFromItem
import com.letsbuildthatapp.kotlinmessenger.views.ChatToItem
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Item

import kotlinx.android.synthetic.main.fragment_chat_log.*


// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class ChatLog : Fragment() {

    private var param1: String? = null
    private var param2: String? = null

    var fragmentview: View?=null
    private lateinit var bundle:Bundle
    private var toUser: User? =null
    val adapter= GroupAdapter<GroupieViewHolder>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)

        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bundle = this.arguments
        if (bundle != null) {
            toUser = bundle.getParcelable<User>(Chat.USER_KEY)
            toUser?.uid?.let { Log.d("Chat_log ", "UserID" + it) }

        }

        recyclerview_chatlog.adapter = adapter

        listenmessages()
        send_button_chat_log.setOnClickListener {
            Log.d(TAG, "Attempt to send message....")
            performSendMessage()
        }




    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {

        return  inflater.inflate(R.layout.fragment_chat_log, container, false)
    }

    companion object {
        val TAG = "ChatLog"
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ChatLog().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    private fun listenmessages() {
        val fromId = FirebaseAuth.getInstance().uid
        val toId = toUser?.uid
        val ref = FirebaseDatabase.getInstance().getReference("/user-messages/$fromId/$toId")

        ref.addChildEventListener(object: ChildEventListener {

            override fun onChildAdded(snapshot: DataSnapshot, p1: String?) {
                val chatMessage = snapshot.getValue(ChatMessage::class.java)

                if (chatMessage != null) {
                    Log.d(TAG, "From listenmessage: "+ chatMessage.text)

                    if (chatMessage.fromId == FirebaseAuth.getInstance().uid) {
                        val currentUser = Chat.currentuser?:return
                        adapter.add(ChatFromItem(chatMessage.text, currentUser))
                    } else {
                        adapter.add(ChatToItem(chatMessage.text, toUser!!))
                    }
                }

                recyclerview_chatlog.scrollToPosition(adapter.itemCount - 1)

            }

            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onChildChanged(p0: DataSnapshot, p1: String?) {

            }

            override fun onChildMoved(p0: DataSnapshot, p1: String?) {

            }

            override fun onChildRemoved(p0: DataSnapshot) {

            }

        })

    }


    private fun performSendMessage() {
        // how do we actually send a message to firebase...
        val text = edittext_chat_log.text.toString()

        val fromId = FirebaseAuth.getInstance().uid

        val toId = toUser?.uid

        if (fromId == null) return
        val reference = FirebaseDatabase.getInstance().getReference("/user-messages/$fromId/$toId").push()

        val toReference = FirebaseDatabase.getInstance().getReference("/user-messages/$toId/$fromId").push()

        val chatMessage = toId?.let {
            ChatMessage(reference.key!!, text, fromId,
                it, System.currentTimeMillis() / 1000)
        }

        reference.setValue(chatMessage)
            .addOnSuccessListener {
                Log.d(TAG, "Saved our chat message: ${reference.key}")
                edittext_chat_log.text.clear()
                recyclerview_chatlog.scrollToPosition(adapter.itemCount - 1)
            }

        toReference.setValue(chatMessage)

        val latestMessageRef = FirebaseDatabase.getInstance().getReference("/latest-messages/$fromId/$toId")
        latestMessageRef.setValue(chatMessage)

        val latestMessageToRef = FirebaseDatabase.getInstance().getReference("/latest-messages/$toId/$fromId")
        latestMessageToRef.setValue(chatMessage)
    }
    private fun dummpydata()
    {

        adapter.add(com.example.maintanancebuddy.ChatFromItem())
        adapter.add(com.example.maintanancebuddy.ChatToItem())
        adapter.add(com.example.maintanancebuddy.ChatFromItem())
        adapter.add(com.example.maintanancebuddy.ChatToItem())
        adapter.add(com.example.maintanancebuddy.ChatFromItem())
        adapter.add(com.example.maintanancebuddy.ChatToItem())
        recyclerview_chatlog.adapter=adapter

    }






}

class ChatFromItem(): Item<GroupieViewHolder>() {
    override fun bind(GroupieViewHolder: GroupieViewHolder, position: Int) {


        // val uri = user.profileImageUrl
        //val targetImageView = GroupieViewHolder.itemView.imageview_chat_from_row
        //Picasso.get().load(uri).into(targetImageView)
    }

    override fun getLayout(): Int {
        return R.layout.chat_from_row
    }
}

class ChatToItem(): Item<GroupieViewHolder>() {
    override fun bind(GroupieViewHolder: GroupieViewHolder, position: Int) {


        // load our user image into the star
        // val uri = user.profileImageUrl
        //val targetImageView = GroupieViewHolder.itemView.imageview_chat_to_row
        // Picasso.get().load(uri).into(targetImageView)
    }

    override fun getLayout(): Int {
        return R.layout.chat_to_row
    }
}
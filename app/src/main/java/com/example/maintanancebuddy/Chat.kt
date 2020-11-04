package com.example.maintanancebuddy

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.example.maintanancebuddy.Models.User
import com.example.maintanancebuddy.Views.UserItem
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder

import kotlinx.android.synthetic.main.fragment_chat.*


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class Chat : Fragment() {
    private lateinit var transaction: FragmentTransaction
    private lateinit var navController:NavController
    var fragmentview: View?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getCurrentUser()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?


    ): View? {


        fragmentview= inflater.inflate(R.layout.fragment_chat, container, false)


        return fragmentview
    }
    companion object {
        val USER_KEY = "USER_KEY"
        var currentuser:User ?=null
    }



    private fun getUsers()
    {
        val ref=FirebaseDatabase.getInstance().getReference("/users")
        val uid = FirebaseAuth.getInstance().uid
        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                val adapter = GroupAdapter<GroupieViewHolder>()
                snapshot.children.forEach() {
                    val user = it.getValue(User::class.java)

                    if (user != null  && user.uid!=uid) {
                        adapter.add(UserItem(user))

                    }
                }
                adapter.setOnItemClickListener() { item, view ->
                    val userItem = item as UserItem
                    val bundle=Bundle()
                    bundle.putParcelable(USER_KEY,userItem.user)

                    view.findNavController().navigate(R.id.action_chat_to_chatLog,bundle)

                }
                recyclerview_chat.adapter = adapter
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }
    private fun getCurrentUser() {
        val uid = FirebaseAuth.getInstance().uid
        Log.d("Chat","getCurrentUser "+ uid)
        val ref = FirebaseDatabase.getInstance().getReference("/users/$uid")
        ref.addListenerForSingleValueEvent(object: ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {

                currentuser= snapshot.getValue(User::class.java)
                if(currentuser!=null)
                {
                    snapshot.getValue(User::class.java)?.uid?.let { Log.d("Chat","getCurrentUser_onDataChange " +it) }

                }
                getUsers()
            }
            override fun onCancelled(p0: DatabaseError) {

            }
        })
    }

}


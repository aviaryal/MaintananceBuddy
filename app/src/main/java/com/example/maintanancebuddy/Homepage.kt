package com.example.maintanancebuddy

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.example.maintanancebuddy.Models.Maintanance_detail
import com.example.maintanancebuddy.Models.addPostclass
import com.example.maintanancebuddy.Views.RepairItem
import com.example.maintanancebuddy.Views.addPostView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import kotlinx.android.synthetic.main.fragment_homepage.*
import kotlinx.android.synthetic.main.fragment_repair__history_resident.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Homepage.newInstance] factory method to
 * create an instance of this fragment.
 */
class Homepage : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        welcomeUserName()
        loadpost()

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_homepage, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment homepage.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Homepage().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
    private fun welcomeUserName(){
        val preferences = activity ?.getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE)
        welcometext.text="Welcome "+preferences?.getString("username","")
    }
    private fun loadpost()
    {
        val ref=FirebaseDatabase.getInstance().getReference("/post")
        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                val adapter = GroupAdapter<GroupieViewHolder>()
                snapshot.children.forEach() {
                    val newpost = it.getValue(addPostclass::class.java)
                    if (newpost != null) {
                        adapter.add(addPostView(newpost))
                        Log.d(Repair.TAG,newpost.id)
                    }
                }

                recyclerview_homepage.adapter = adapter

            }


            override fun onCancelled(error: DatabaseError) {

            }
        })
    }


}
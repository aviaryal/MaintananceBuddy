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
import com.example.maintanancebuddy.Models.User
import com.example.maintanancebuddy.Views.RepairItem
import com.example.maintanancebuddy.Views.UserItem
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import kotlinx.android.synthetic.main.fragment_chat.*
import kotlinx.android.synthetic.main.fragment_repair.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Repair.newInstance] factory method to
 * create an instance of this fragment.
 */
class Repair : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var ref: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val preferences = activity
            ?.getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE)
        val isadmin= preferences?.getInt("isadmin",2121)
        if(isadmin==1)
            Resident_repair_history_buttom.text="View Request"
        onrequestclicked(view)
        onHistoryclicked(view)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_repair, container, false)
    }

    companion object {
        val TAG="Repair"
    }


    private fun onrequestclicked(view: View)
    {
        RepairRequest.setOnClickListener{
            it.findNavController().navigate(R.id.repair_details_resident)
        }
    }
    private fun onHistoryclicked(view: View)
    {
        Resident_repair_history_buttom.setOnClickListener(){
            it.findNavController().navigate(R.id.repair_History_resident)
        }


    }
}
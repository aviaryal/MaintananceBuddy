package com.example.maintanancebuddy

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.maintanancebuddy.Models.Maintanance_detail
import com.example.maintanancebuddy.Views.Notification_view
import com.example.maintanancebuddy.Views.RepairItem
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import kotlinx.android.synthetic.main.fragment_notification.*
import kotlinx.android.synthetic.main.fragment_repair__history_resident.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Notification.newInstance] factory method to
 * create an instance of this fragment.
 */
class Notification : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var ref:DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_notification, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val preferences = activity
            ?.getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE)
        val isadmin= preferences?.getInt("isadmin",2121)
        if (isadmin != null) {
            shownotiftication(isadmin)
        }





    }

    companion object {
        val TAG="Notification"
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Notification.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Notification().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
    private fun shownotiftication(isadmin:Int)
    {
        val uid = FirebaseAuth.getInstance().uid
        if(isadmin==0)
            ref = FirebaseDatabase.getInstance().getReference("/repair/$uid")
        else
            ref = FirebaseDatabase.getInstance().getReference("/repair_manager")
        //
        ref.orderByChild("timestamp").addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                val adapter = GroupAdapter<GroupieViewHolder>()
                snapshot.children.forEach() {
                    val repair_details = it.getValue(Maintanance_detail::class.java)
                    if (repair_details != null) {
                        adapter.add(Notification_view(repair_details,isadmin))
                        Log.d(TAG,repair_details.type)
                    }
                }
                adapter.setOnItemClickListener(){ item, view ->
                    val maintainceItem= item as Notification_view
                    val bundle=Bundle()
                    bundle.putParcelable(Repair_History_resident.Resident_repair_History,maintainceItem.repair)
                    view.findNavController().navigate(R.id.repair_Resident_History_details,bundle)
                }


                recyclerview_notification.adapter = adapter

            }


            override fun onCancelled(error: DatabaseError) {

            }
        })

    }
}
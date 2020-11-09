package com.example.maintanancebuddy

import android.app.Activity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import com.example.maintanancebuddy.Models.ChatMessage
import com.example.maintanancebuddy.Models.Maintanance_detail
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.fragment_chat_log.*
import kotlinx.android.synthetic.main.fragment_repair_details_resident.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Repair_details_resident.newInstance] factory method to
 * create an instance of this fragment.
 */
class Repair_details_resident : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    var type: String=""
    private lateinit var fragmentview:View
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        populate_spinner_items(view)
        //savedata()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        fragmentview=inflater.inflate(R.layout.fragment_repair_details_resident, container, false)
        return fragmentview
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Repair_details_resident.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        val TAG="Repair_resident"
        fun newInstance(param1: String, param2: String) =
            Repair_details_resident().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
    private fun populate_spinner_items(view: View)
    {

        val spinner: Spinner = view.findViewById(R.id.spinner_repair_items)
        // Create an ArrayAdapter using the string array and a default spinner layout
        activity?.let {
            ArrayAdapter.createFromResource(
                it,
                R.array.repair_items_type,
                android.R.layout.simple_spinner_item
            ).also { adapter ->
                // Specify the layout to use when the list of choices appears
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                // Apply the adapter to the spinner
                spinner.adapter = adapter

            }

        }
        spinner?.onItemSelectedListener=object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                type=p0?.getItemAtPosition(p2).toString()
                Log.d(TAG, "populate_spinner_item $type")
                savedata()
            }
        }

    }
    private fun savetoStorage()
    {

    }
    private fun savedata()
    {
        val string = repair_description_resident.editableText.toString()
        val uid= FirebaseAuth.getInstance().uid
        Log.d(TAG,"savedata $type")

        submit_request.setOnClickListener {
            val reference = FirebaseDatabase.getInstance().getReference("/repair/$uid").push()
            val repair_des = uid?.let {
                reference!!.key?.let { it1 ->
                    Maintanance_detail(
                        it1,
                        uid,
                        type,
                        "requested",
                        System.currentTimeMillis() / 1000
                    )
                }

            }
            reference.setValue(repair_des).addOnSuccessListener {
                Log.d(TAG,"Reference $reference!!.key")
                Toast.makeText(activity,"Successfully requested",Toast.LENGTH_SHORT).show()
                fragmentManager?.popBackStack()
            }
        }




    }
}
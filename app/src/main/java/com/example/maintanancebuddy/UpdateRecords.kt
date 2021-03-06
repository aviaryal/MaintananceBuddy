package com.example.maintanancebuddy

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.fragment_update_records.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [UpdateRecords.newInstance] factory method to
 * create an instance of this fragment.
 */
class UpdateRecords : Fragment() {
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_update_records, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        saveChanges()
        cancelChanges()
    }

    companion object {
        val TAG = "Update Resident Information"
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment UpdateRecords.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            UpdateRecords().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    //yet to implement save changes
    private fun saveChanges(){
        save_edit_changes.setOnClickListener{
            savedata()
        }
    }

    private fun cancelChanges(){
        cancel_edit_changes.setOnClickListener{
            fragmentManager?.popBackStack()
        }
    }

    private fun savedata(){
        if(display_name_update_records.text.toString().isEmpty()){
            Toast.makeText(activity,"The user first name field cannot be empty", Toast.LENGTH_SHORT).show()
            return
        }
        if(display_lname_update.text.toString().isEmpty()){
            Toast.makeText(activity,"The user last name field cannot be empty", Toast.LENGTH_SHORT).show()
            return
        }
        if(display_phone_update_records.text.toString().isEmpty()){
            Toast.makeText(activity,"The phone number field cannot be empty", Toast.LENGTH_SHORT).show()
            return
        }
        if(display_EmergencyName_update_records.text.toString().isEmpty()){
            Toast.makeText(activity,"The emergency name field cannot be empty", Toast.LENGTH_SHORT).show()
            return
        }
        if(display_EmergencyRelationship_update_records.text.toString().isEmpty()){
            Toast.makeText(activity,"The emergency field cannot be empty", Toast.LENGTH_SHORT).show()
            return
        }
        if(display_EmergencyPhone_update_records.text.toString().isEmpty()){
            Toast.makeText(activity,"The emergency phone field cannot be empty", Toast.LENGTH_SHORT).show()
            return
        }

        val uid = FirebaseAuth.getInstance().uid
        val ref= FirebaseDatabase.getInstance().getReference("users/$uid")

        ref.child("fname").setValue(display_name_update_records.text.toString())
        ref.child("lname").setValue(display_lname_update.text.toString())
        ref.child("cellphone").setValue(display_phone_update_records.text.toString())

        val sharedPreference =  activity?.getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE)
        var editor = sharedPreference?.edit()
        editor?.putString("username", display_name_update_records.text.toString()+" "+(display_lname_update.text.toString()))
        editor?.putString("cellphone",display_phone_update_records.text.toString())
        editor?.commit()


        val ref2=FirebaseDatabase.getInstance().getReference("Emergency_Contact/$uid")
        ref2.child("name").setValue(display_EmergencyName_update_records.text.toString())
        ref2.child("relationship").setValue(display_EmergencyRelationship_update_records.text.toString().isEmpty())
        ref2.child("cellphone").setValue(display_EmergencyPhone_update_records.text.toString())

        Toast.makeText(activity,"The record successfully updated", Toast.LENGTH_SHORT).show()

        fragmentManager?.popBackStack()


    }
}
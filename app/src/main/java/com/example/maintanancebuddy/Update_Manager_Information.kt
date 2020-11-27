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
import kotlinx.android.synthetic.main.fragment_update__manager__information.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Update_Manager_Information.newInstance] factory method to
 * create an instance of this fragment.
 */
class Update_Manager_Information : Fragment() {
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

        dont_save_manager_changes()
        save_manager_info_changes()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_update__manager__information, container, false)
    }

    companion object {
        val TAG = "Update Manager Information"
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Update_Manager_Information.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Update_Manager_Information().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    private fun dont_save_manager_changes(){
        cancel_Manager_Changes.setOnClickListener{
            fragmentManager?.popBackStack()
        }
    }

    //Need to implement
    private fun save_manager_info_changes(){
            save_manager_changes.setOnClickListener{
                savedata()
            }
    }


    private fun savedata()
    {
        val uid = FirebaseAuth.getInstance().uid

        val ref= FirebaseDatabase.getInstance().getReference("users/$uid")


        if(manager_edit_name_update_records.text.toString().isEmpty())
        {
            Toast.makeText(activity,"The name field cannot be empty", Toast.LENGTH_SHORT).show()
            return
        }
        if(display_phone_update_records_manager.text.toString().isEmpty())
        {
            Toast.makeText(activity,"The phone field cannot be empty", Toast.LENGTH_SHORT).show()
            return
        }


        ref.child("fname").setValue(manager_edit_name_update_records.text.toString())
        ref.child("lname").setValue(manager_edit_lname_update_records.text.toString())
        ref.child("cellphone").setValue(display_phone_update_records_manager.text.toString())
        Toast.makeText(activity,"The record successfully updated", Toast.LENGTH_SHORT).show()

        val sharedPreference =  activity?.getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE)
        var editor = sharedPreference?.edit()
        editor?.putString("username", manager_edit_name_update_records.text.toString()+" "+manager_edit_lname_update_records.text.toString())
        editor?.putString("cellphone",display_phone_update_records_manager.text.toString())
        editor?.commit()
        fragmentManager?.popBackStack()
    }
}
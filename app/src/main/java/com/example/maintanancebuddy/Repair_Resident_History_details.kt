package com.example.maintanancebuddy

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.MediaController
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import android.widget.Toast.makeText
import androidx.navigation.findNavController
import com.example.maintanancebuddy.Models.Maintanance_detail
import com.example.maintanancebuddy.Models.User
import com.google.firebase.database.FirebaseDatabase
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_repair__resident__history_details.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Repair_Resident_History_details.newInstance] factory method to
 * create an instance of this fragment.
 */
class Repair_Resident_History_details : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    var maintaince_details: Maintanance_detail?=null
    private var whichradio:Int=-1
    private lateinit var mediaController: MediaController
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
        return inflater.inflate(
            R.layout.fragment_repair__resident__history_details,
            container,
            false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bundle = this.arguments
        val preferences = activity
            ?.getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE)
        val isadmin= preferences?.getInt("isadmin",2121)

        if (bundle != null) {
            maintaince_details= bundle.getParcelable<Maintanance_detail>(Repair_History_resident.Resident_repair_History)
            val text= maintaince_details?.type.toString()
            repair_history_details_resident_type_firebase.text= maintaince_details?.type.toString()
            repair_history_details_resident_location_firebase.text= maintaince_details?.locat.toString()
            repair_history_details_resident_status_firebase.text= maintaince_details?.status.toString()
            repair_history_details_resident_description_firebase.text = maintaince_details?.descr.toString()
            Picasso.get().load(maintaince_details?.photouri.toString()).into(imageView_Resident_History)
            if(maintaince_details?.status.toString()=="completed")
                resident_edit_repair_request.visibility = View.GONE
            if(maintaince_details?.videouri.isNullOrEmpty())
                videoView_resident_history.visibility= View.GONE
            else
            {
                mediaController = MediaController(activity)

                videoView_resident_history.setVideoPath(maintaince_details?.videouri.toString())
                videoView_resident_history.setOnPreparedListener(){
                    mediaController.setAnchorView(resident_videoContainer)
                    videoView_resident_history.setMediaController(mediaController)
                    //videoView_resident_history.start()
                }

            }
            if(isadmin==0)
                radio_group.visibility=View.GONE
            else {
                resident_edit_repair_request.text = "Submit"
                radio_group.setOnCheckedChangeListener(){_,checkedID->
                    if (checkedID==R.id.completed)
                        whichradio=0;
                    else(checkedID==R.id.ongoing)
                        whichradio=1
                }

            }


        }
        resident_edit_repair_request.setOnClickListener(){
            if(isadmin==0) {
                it.findNavController().navigate(R.id.repair_details_resident, bundle)
            }
            else
            {
                if(whichradio==-1) {
                    Toast.makeText(activity, "Selected the status", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                editchanges()
            }
        }


    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Repair_Resident_History_details.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Repair_Resident_History_details().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
    fun onRadioButtonClicked(view: View)
    {
        if(view is RadioButton)
        {
            val checked= view.isChecked
            when (view.getId())
            {
                R.id.ongoing->
                {
                    whichradio=0
                }
                R.id.completed->
                {
                    whichradio=1
                }
            }

        }
    }
    private fun editchanges()
    {
        val user_uid = maintaince_details?.uid.toString()
        val repair_id = maintaince_details?.id.toString()
        val ref= FirebaseDatabase.getInstance().getReference("repair_manager/$repair_id")
        val ref_res= FirebaseDatabase.getInstance().getReference("/repair/$user_uid/$repair_id")
        if(whichradio==0) {
            ref.child("status").setValue("completed")
            ref_res.child("status").setValue("completed")
        }
        else if(whichradio==1)
        {
            ref.child("status").setValue("ongoing")
            ref_res.child("status").setValue("ongoing")
        }
        fragmentManager?.popBackStack()
    }
}
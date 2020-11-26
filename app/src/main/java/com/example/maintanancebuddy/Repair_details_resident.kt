package com.example.maintanancebuddy

import android.app.Activity
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.example.maintanancebuddy.Models.Maintanance_detail
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_repair_details_resident.*
import java.util.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class Repair_details_resident : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    var type: String=""
    private val Requestcode= 123
    private lateinit var fragmentview:View
    private var selectedPhotoUri: Uri? =null
    private var selectedVideoUri: Uri? =null
    private lateinit var bundle: Bundle
    private  var maintaince_details: Maintanance_detail?=null
    private lateinit var progreeDialog: ProgressDialog
    private lateinit var pbar: ProgressBar
    private lateinit var mediaController: MediaController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bundle= this.arguments
        populate_spinner_items(view)
        pbar= ProgressBar(activity)
        progreeDialog=ProgressDialog(activity)
        progreeDialog.setTitle("Uploading")
        progreeDialog.setMessage("Please wait")
        progreeDialog.setCancelable(false)
        progreeDialog.setCanceledOnTouchOutside(false)
        mediaController = MediaController(activity)
        if (bundle != null) {
            maintaince_details= bundle.getParcelable<Maintanance_detail>(Repair_History_resident.Resident_repair_History)
            repair_location_resident.setText(maintaince_details?.locat.toString())
            repair_description_resident.setText(maintaince_details?.descr.toString())
            Picasso.get().load(maintaince_details?.photouri.toString()).into(imageView_resident_details)
            Log.d(TAG,maintaince_details?.videouri.toString())
            if(maintaince_details?.videouri.isNullOrEmpty())
                videoview_resident_details.visibility= View.GONE
            else
            {

                videoview_resident_details.setVideoPath(maintaince_details?.videouri.toString())
                videoview_resident_details.setOnPreparedListener(){
                    mediaController.setAnchorView(resident_videocontainer_details)
                    videoview_resident_details.setMediaController(mediaController)
                    //videoView_resident_history.start()
                }
            }

        }
        else
        {
            imageView_resident_details.visibility=View.GONE
            videoview_resident_details.visibility=View.GONE
        }


        submit_request.setOnClickListener{
            saveImagetoStorage()
        }
        repair_upload_image_resident.setOnClickListener{
            val intent= Intent()
            intent.type= "image/*"
            intent.action= Intent.ACTION_GET_CONTENT
            startActivityForResult(intent, 0)
        }
        repair_upload_video_resident.setOnClickListener{
            val intent= Intent()
            intent.type= "video/*"
            intent.action= Intent.ACTION_GET_CONTENT
            startActivityForResult(intent, 1)
        }



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
        val TAG="Repair_details_resident"
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
                //Log.d(TAG, "populate_spinner_item $type")
            }
        }

    }
    private fun saveImagetoStorage()
    {
        if(selectedPhotoUri==null) {
            Toast.makeText(activity, "Need at least photo", Toast.LENGTH_SHORT).show()
            return
        }
        if(selectedVideoUri==null)
            Toast.makeText(activity, "Submitting without video", Toast.LENGTH_SHORT).show()
        val filename= UUID.randomUUID().toString()
        val ref= FirebaseStorage.getInstance().getReference("/images/$filename")

        progreeDialog.show()
        ref.putFile(selectedPhotoUri!!).addOnSuccessListener {
            Log.d(TAG, "Successfully uploaded image: ${it.metadata?.path}")
            ref.downloadUrl.addOnSuccessListener {
                Log.d(TAG, "Image Location: $it")
                val pathimage=it.toString()
                if(selectedVideoUri!=null)
                {
                    saveVideotoStorage(pathimage)
                }
                else
                    savedata(pathimage, "")
            }

        }

    }
    private fun saveVideotoStorage(imageuri: String)
    {
        val videofile= UUID.randomUUID().toString()
        val refvideo= FirebaseStorage.getInstance().getReference("/videos/$videofile")
        refvideo.putFile(selectedVideoUri!!).addOnSuccessListener {
            Log.d(TAG, "Successfully uploaded video: ${it.metadata?.path}")
            refvideo.downloadUrl.addOnSuccessListener {
                Log.d(TAG, "Image Location: $it")
                savedata(imageuri, it.toString())
            }
        }
    }

    //trying to get pictures
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data != null) {
            if (requestCode== 0 && resultCode== Activity.RESULT_OK && data.data !=null)
            {
                selectedPhotoUri= data.data
                Toast.makeText(activity, "Getting picture", Toast.LENGTH_SHORT).show()
                imageView_resident_details.setImageURI(selectedPhotoUri)
                imageView_resident_details.visibility=View.VISIBLE
            }
            else if (requestCode== 1 && resultCode== Activity.RESULT_OK && data.data !=null)
            {

                selectedVideoUri= data.data
                Toast.makeText(activity, "Getting video", Toast.LENGTH_SHORT).show()
                videoview_resident_details.setVideoURI(selectedVideoUri)
                videoview_resident_details.visibility- View.VISIBLE
                mediaController.setAnchorView(resident_videocontainer_details)


            }
        }

    }
    private fun savedata(imageuri: String, videouri: String)
    {
        val loc = repair_location_resident.editableText.toString()
        val uid= FirebaseAuth.getInstance().uid
        val preferences = activity
            ?.getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE)
        val aptno=preferences?.getString("aptno", "1")
        val description= repair_description_resident.editableText.toString()
        Log.d(TAG, "savedata $type")
        if(description.isEmpty() || loc.isEmpty() )
        {
            Toast.makeText(activity, "Missing Value", Toast.LENGTH_SHORT).show()
            return
        }

        if(maintaince_details==null)
        {
            val reference = FirebaseDatabase.getInstance().getReference("/repair/$uid").push()
            Log.d(TAG, "savedata $type")
            val repair_des= Maintanance_detail(
                reference.key!!,
                uid!!,
                aptno!!,
                type,
                loc,
                description,
                imageuri,
                videouri,
                "requested",
                System.currentTimeMillis() / 1000
            )
            val secondref=FirebaseDatabase.getInstance().getReference("/repair_manger").push()
            secondref.setValue(repair_des).addOnSuccessListener {

            }
            reference.setValue(repair_des).addOnSuccessListener {
                Log.d(TAG, "Reference $reference!!.key")
                Toast.makeText(activity, "Successfully requested", Toast.LENGTH_SHORT).show()
                progreeDialog.dismiss()
                fragmentManager?.popBackStack()
            }
        }
        else
        {
            val id=maintaince_details?.id.toString()
            val reference = FirebaseDatabase.getInstance().getReference("/repair/$uid/$id")
            reference.child("descr").setValue(description)
            reference.child("locat").setValue(loc)
            reference.child("type").setValue(type)
            reference.child("photouri").setValue(imageuri)
            reference.child("videouri").setValue(videouri)
            reference.child("timestamp").setValue(System.currentTimeMillis() / 1000)
            progreeDialog.dismiss()
            fragmentManager?.popBackStack()
        }

    }
}
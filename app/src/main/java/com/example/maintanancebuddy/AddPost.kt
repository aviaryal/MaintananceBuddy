package com.example.maintanancebuddy

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.maintanancebuddy.Models.addPostclass
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.fragment_add_post.*

import java.util.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AddPost.newInstance] factory method to
 * create an instance of this fragment.
 */
class AddPost : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    var type: String=""
    private val Requestcode= 123
    private lateinit var fragmentview:View
    private var selectedPhotoUri: Uri? =null
    private var selectedVideoUri: Uri? =null

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
        return inflater.inflate(R.layout.fragment_add_post, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        submit_add_post.setOnClickListener {
            saveImagetoStorage()
        }

        add_image_add_post.setOnClickListener {
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(intent, 0)
        }

    }


    companion object {
        val TAG = "AddPost"
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment AddPost.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AddPost().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    private fun saveImagetoStorage() {
        if (selectedPhotoUri == null) {
            Toast.makeText(activity, "Need at least photo", Toast.LENGTH_SHORT).show()
            return
        }

        val filename = UUID.randomUUID().toString()
        val ref = FirebaseStorage.getInstance().getReference("/images/$filename")


        ref.putFile(selectedPhotoUri!!).addOnSuccessListener {
            Log.d(TAG, "Successfully uploaded image: ${it.metadata?.path}")
            ref.downloadUrl.addOnSuccessListener {
                Log.d(TAG, "Image Location: $it")
                val pathimage = it.toString()
                savedata(pathimage)
            }
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data != null) {
            if (requestCode == 0 && resultCode == Activity.RESULT_OK && data.data != null) {
                if (data != null) {
                    selectedPhotoUri = data.data
                }
                Toast.makeText(activity, "Getting picture", Toast.LENGTH_SHORT).show()

            }
        }
    }

    private fun savedata(imageuri:String)
    {

        val uid= FirebaseAuth.getInstance().uid

        if(read_post_entry_box.text.toString().isEmpty())
        {
            Toast.makeText(activity,"The post description cannot be empty",Toast.LENGTH_SHORT).show()
            return
        }

        val reference = FirebaseDatabase.getInstance().getReference("/post/").push()
        Log.d(TAG,"savedata $type")
        val post_description = addPostclass(reference.key!!,read_post_entry_box.text.toString(),imageuri,System.currentTimeMillis()/1000 )
        reference.setValue(post_description).addOnSuccessListener {
            Log.d(TAG,"Reference $reference!!.key")
            Toast.makeText(activity,"Post Successfully added",Toast.LENGTH_SHORT).show()
            fragmentManager?.popBackStack()
        }

    }
}

package com.example.maintanancebuddy

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_manager__profile.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Manager_Profile.newInstance] factory method to
 * create an instance of this fragment.
 */
class Manager_Profile : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    //private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        add_new_post()
        update_manager_info()
        signout()
        getdata_manager()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_manager__profile, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Manager_Profile.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Manager_Profile().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    private fun getdata_manager(){
        val preferences = activity ?.getSharedPreferences("PREFERENCE_NAME",Context.MODE_PRIVATE)

        display_manager_name.text=preferences?.getString("username","")
        display_Manager_email.text=preferences?.getString("email","")
        display_Manager_phone.text=preferences?.getString("cellphone","")
    }
    private fun update_manager_info(){
        updateInfoManager.setOnClickListener{
            it.findNavController().navigate(R.id.update_Manager_Information)
        }
    }

    private fun add_new_post(){
        addCommunityPost.setOnClickListener{
            it.findNavController().navigate(R.id.addPost)
        }
    }

    private fun signout()
    {
        manager_signout.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            cleardata()
            val intent = Intent(activity, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }
    }
    private fun cleardata()
    {
        val preferences = activity
            ?.getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE)
        var editor= preferences?.edit()
        if (editor != null) {
            editor.clear()
            editor.commit()
        }
    }

}
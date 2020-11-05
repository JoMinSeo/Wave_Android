package com.project.wave_v2.view.fragment

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.project.wave_v2.R
import com.project.wave_v2.view.activity.LoginActivity
import kotlinx.android.synthetic.main.fragment_profile.*

class ProfileFragment : Fragment() {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val prefs: SharedPreferences = requireActivity().getSharedPreferences("user_info", Context.MODE_PRIVATE)
        var id : String? = prefs.getString("userId", "user")
        val editor : SharedPreferences.Editor = prefs.edit()

        profile_id_Tv.text = id

        logoutBtn.setOnClickListener {
            editor.clear()
            editor.commit()
            startActivity(Intent(context, LoginActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
            activity?.finish()
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }
}
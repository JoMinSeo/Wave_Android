package com.project.wave_v2.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.project.wave_v2.data.request.playlist.CallPlayListBody
import com.project.wave_v2.network.Service
import kotlinx.android.synthetic.main.fragment_home.*
import retrofit2.Retrofit

class MainFragment : Fragment() {

    lateinit var API : Service
    lateinit var retrofit : Retrofit

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        API = retrofit.create(Service::class.java)
//        API.getList(CallPlayListBody(userId = ))

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }
}
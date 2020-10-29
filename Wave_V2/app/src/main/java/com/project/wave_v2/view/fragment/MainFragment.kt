package com.project.wave_v2.view.fragment

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.project.wave_v2.R
import com.project.wave_v2.data.SliderItem
import com.project.wave_v2.data.request.playlist.CallPlayListBody
import com.project.wave_v2.data.response.playlist.CallPlayListModel
import com.project.wave_v2.data.response.playlist.ListInfo
import com.project.wave_v2.network.RetrofitClient
import com.project.wave_v2.network.Service
import com.project.wave_v2.widget.MainAdapter
import com.project.wave_v2.widget.MainImageSlider
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType
import com.smarteist.autoimageslider.SliderAnimations
import com.smarteist.autoimageslider.SliderView
import kotlinx.android.synthetic.main.fragment_home.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit


class MainFragment : Fragment() {

    lateinit var API : Service
    lateinit var retrofit : Retrofit

    lateinit var navController: NavController

    lateinit var firstAdapter: MainAdapter
    lateinit var secondAdapter: MainAdapter
    lateinit var thirdAdapter: MainAdapter

    var firstList : ArrayList<ListInfo> = ArrayList<ListInfo>()
    var secondList : ArrayList<ListInfo> = ArrayList<ListInfo>()
    var thirdList : ArrayList<ListInfo> = ArrayList<ListInfo>()

    lateinit var slideAdapter : MainImageSlider


    private fun onItemClick(index: Int, position: Int, playList: ListInfo) {
        navController.navigate(R.id.action_navigation_home_to_songListFragment)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        retrofit = RetrofitClient.getInstance()

        navController = Navigation.findNavController(view)

        firstAdapter = MainAdapter()
        firstAdapter.setList(firstList)
        secondAdapter = MainAdapter()
        secondAdapter.setList(secondList)
        thirdAdapter = MainAdapter()
        thirdAdapter.setList(thirdList)

        recycler_first.adapter = firstAdapter
        recycler_second.adapter = secondAdapter
        recycler_third.adapter = thirdAdapter

        val prefs: SharedPreferences = requireActivity().getSharedPreferences("test", MODE_PRIVATE)
        var id : String? = prefs.getString("userId", "user")

        genre1.text = "#더미더미"
        genre2.text = "#더어어미"
        genre3.text = "#더미이이"

        Log.d("ididid", id)
//        API = retrofit.create(Service::class.java)
//        API.getList(
//                CallPlayListBody(
//                        userId = id
//                )
//        ).enqueue(object : Callback<List<CallPlayListModel>> {
//            override fun onResponse(call: Call<List<CallPlayListModel>>, response: Response<List<CallPlayListModel>>) {
//                if (response.code() == 200) {
//                    firstList = response.body()?.get(0)?.list!!
//                    secondList = response.body()?.get(1)?.list!!
//                    thirdList = response.body()?.get(2)?.list!!
//
//                    genre1.text = "#" + response.body()?.get(0)?.genreName
//                    genre2.text = "#" + response.body()?.get(1)?.genreName
//                    genre3.text = "#" + response.body()?.get(2)?.genreName
//
//                    firstAdapter.notifyDataSetChanged()
//                    secondAdapter.notifyDataSetChanged()
//                    thirdAdapter.notifyDataSetChanged()
//                } else {
//                    Log.d("Main List", response.code().toString())
//                }
//            }
//
//            override fun onFailure(call: Call<List<CallPlayListModel>>, t: Throwable) {
//
//            }
//        })

        slideAdapter = MainImageSlider(context)
        addNewItem()
        imageSlider.setSliderAdapter(slideAdapter)
        imageSlider.setIndicatorAnimation(IndicatorAnimationType.WORM)
        imageSlider.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION)
        imageSlider.autoCycleDirection = SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH
        imageSlider.indicatorSelectedColor = Color.WHITE
        imageSlider.indicatorUnselectedColor = Color.GRAY
        imageSlider.scrollTimeInSec = 3
        imageSlider.isAutoCycle = true
        imageSlider.startAutoCycle()

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    fun addNewItem() {
        val sliderItem1 = SliderItem()
        val sliderItem2 = SliderItem()
        val sliderItem3 = SliderItem()
        val sliderItem4 = SliderItem()

        sliderItem1.ImageUrl = R.drawable.ic_launcher_background
        sliderItem2.ImageUrl = R.drawable.edittext_back
        sliderItem3.ImageUrl = R.drawable.intro
        sliderItem4.ImageUrl = R.drawable.loginback

        slideAdapter.addItem(sliderItem1)
        slideAdapter.addItem(sliderItem2)
        slideAdapter.addItem(sliderItem3)
        slideAdapter.addItem(sliderItem4)
    }
}
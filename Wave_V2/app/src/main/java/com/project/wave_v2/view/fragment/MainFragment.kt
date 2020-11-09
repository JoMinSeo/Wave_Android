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
import androidx.recyclerview.widget.LinearLayoutManager
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

    var API : Service? = null
    lateinit var retrofit : Retrofit

    lateinit var navController: NavController

    var firstList : ArrayList<ListInfo> = ArrayList<ListInfo>()
    var secondList : ArrayList<ListInfo> = ArrayList<ListInfo>()
    var thirdList : ArrayList<ListInfo> = ArrayList<ListInfo>()

    var firstAdapter: MainAdapter = MainAdapter(firstList)
    var secondAdapter: MainAdapter = MainAdapter(secondList)
    var thirdAdapter: MainAdapter = MainAdapter(thirdList)

    lateinit var slideAdapter : MainImageSlider

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        retrofit = RetrofitClient.getInstance()
        API = RetrofitClient.getService()

        navController = Navigation.findNavController(view)

        rcViewSetting()

        val prefs: SharedPreferences = requireActivity().getSharedPreferences("user_info", MODE_PRIVATE)
        var id : String? = prefs.getString("userId", "user")


        Log.d("ididid", id)
        API?.getList(
                CallPlayListBody(
                        userId = id
                )
        )?.enqueue(object : Callback<List<CallPlayListModel>> {
            override fun onResponse(call: Call<List<CallPlayListModel>>, response: Response<List<CallPlayListModel>>) {
                if (response.code() == 200) {
                    firstList.clear()
                    secondList.clear()
                    thirdList.clear()

                    for(i in 0 until response.body()?.get(0)?.list?.size!! ){
                        firstList.add(response.body()?.get(0)?.list!![i])
                    }
                    firstAdapter.notifyDataSetChanged()
                    for(i in 0 until response.body()?.get(1)?.list?.size!! ){
                        secondList.add(response.body()?.get(1)?.list!![i])
                    }
                    secondAdapter.notifyDataSetChanged()
                    for(i in 0 until response.body()?.get(2)?.list?.size!! ){
                        thirdList.add(response.body()?.get(2)?.list!![i])
                    }
                    thirdAdapter.notifyDataSetChanged()


                    genre1.text = "#" + response.body()?.get(0)?.genreName
                    genre2.text = "#" + response.body()?.get(1)?.genreName
                    genre3.text = "#" + response.body()?.get(2)?.genreName


                } else {
                    Log.d("Main List", response.code().toString())
                }
            }

            override fun onFailure(call: Call<List<CallPlayListModel>>, t: Throwable) {

            }
        })

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

    private fun rcViewSetting(){
        recycler_first.adapter = firstAdapter
        recycler_second.adapter = secondAdapter
        recycler_third.adapter = thirdAdapter

        recycler_first.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        recycler_second.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        recycler_third.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        recycler_first.setHasFixedSize(true)
        recycler_second.setHasFixedSize(true)
        recycler_third.setHasFixedSize(true)
    }

    private fun addNewItem() {
        val sliderItem1 = SliderItem()
        val sliderItem2 = SliderItem()
        val sliderItem3 = SliderItem()
        val sliderItem4 = SliderItem()

        sliderItem1.ImageUrl = R.drawable.slide1
        sliderItem2.ImageUrl = R.drawable.slide2
        sliderItem3.ImageUrl = R.drawable.slide3
        sliderItem4.ImageUrl = R.drawable.slide4

        slideAdapter.addItem(sliderItem1)
        slideAdapter.addItem(sliderItem2)
        slideAdapter.addItem(sliderItem3)
        slideAdapter.addItem(sliderItem4)
    }
}
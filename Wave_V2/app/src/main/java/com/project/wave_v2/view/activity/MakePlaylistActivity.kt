package com.project.wave_v2.view.activity

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.project.wave_v2.R
import com.project.wave_v2.data.request.like.LikeFeelBody
import com.project.wave_v2.data.response.like.LikeFeelModel
import com.project.wave_v2.network.RetrofitClient
import com.project.wave_v2.network.Service
import kotlinx.android.synthetic.main.activity_genre.*
import kotlinx.android.synthetic.main.activity_make_playlist.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class MakePlaylistActivity : AppCompatActivity() {

    var mainGenreId : Int = -1
    var subGenreId : Int = -1

    var mainGenreList : ArrayList<String> = ArrayList<String>()
    var subGenreList : ArrayList<String> = ArrayList<String>()

    lateinit var mainAdapter : ArrayAdapter<*>
    lateinit var subAdapter1 : ArrayAdapter<*>
    lateinit var subAdapter2 : ArrayAdapter<*>

    var API: Service? = null
    lateinit var retrofit: Retrofit

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_make_playlist)

        var userInfo = getSharedPreferences("user_info", Context.MODE_PRIVATE)

        retrofit = RetrofitClient.getInstance()
        API = RetrofitClient.getService()

        insertMainList()

        mainGenreSelect()
    }

    private fun mainGenreSelect() {
        mainGenre_Sp.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                mainGenreId = position + 1
                callSubGenre(mainGenreId)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        subGenre_Sp.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                subGenreId = position + 1
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }
    }

    private fun callSubGenre(genreId : Int){
        API?.likeFeel(LikeFeelBody(genreId))
            ?.enqueue(object : Callback<List<LikeFeelModel>> {
                override fun onResponse(
                    call: Call<List<LikeFeelModel>>,
                    response: Response<List<LikeFeelModel>>
                ) {
                    if(response.code() == 200){
                        subGenreList.clear()
                        for(e in response.body()!!){
                            e.subGenreName?.let { subGenreList.add(it) }
                        }
                        subAdapter1.notifyDataSetChanged()
                        subAdapter2.notifyDataSetChanged()
                    }
                }

                override fun onFailure(call: Call<List<LikeFeelModel>>, t: Throwable) {
                }
            })
    }

    private fun insertMainList(){
        mainGenreList.add("팝")
        mainGenreList.add("아이돌")
        mainGenreList.add("발라드")
        mainGenreList.add("힙합")
        mainGenreList.add("밴드")
        mainGenreList.add("트로트")
        mainGenreList.add("인디")
        mainGenreList.add("디스코")
        mainGenreList.add("록")
    }
}
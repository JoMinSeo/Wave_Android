package com.project.wave_v2.view.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.project.wave_v2.R
import com.project.wave_v2.data.request.like.LikeFeelBody
import com.project.wave_v2.data.request.like.LikeInfoMainBody
import com.project.wave_v2.data.request.like.LikeInfoSubBody
import com.project.wave_v2.data.response.ResultModel
import com.project.wave_v2.data.response.like.LikeFeelModel
import com.project.wave_v2.network.RetrofitClient
import com.project.wave_v2.network.Service
import kotlinx.android.synthetic.main.activity_genre.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit


class GenreActivity : AppCompatActivity() {

    var userId : String? = null

    var mainGenreList : ArrayList<String> = ArrayList<String>()
    var subGenreList : ArrayList<String> = ArrayList<String>()

    lateinit var mainAdapter : ArrayAdapter<*>
    lateinit var subAdapter1 : ArrayAdapter<*>
    lateinit var subAdapter2 : ArrayAdapter<*>

    var mainGenreId : Int = -1
    var subGenreId1 : Int = -1
    var subGenreId2 : Int = -1

    lateinit var API: Service
    lateinit var retrofit: Retrofit

    var check : Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_genre)

        retrofit = RetrofitClient.getInstance()
        API = retrofit.create(Service::class.java)

        insertMain()

        userId = intent.getStringExtra("userId")

        adapterSetting()

        listenerSetting()

    }
    private fun listenerSetting(){
        mainGenreSpinner.onItemSelectedListener = object : OnItemSelectedListener{
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
        subGenreSpinner1.onItemSelectedListener = object : OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                subGenreId1 = position + 1
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }
        subGenreSpinner2.onItemSelectedListener = object : OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                subGenreId2 = position + 1
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }

        finishBtn.setOnClickListener {
            if(subGenreId1 == subGenreId2){
                Toast.makeText(this@GenreActivity, "서로 다른 세부 장르를 선택해주세요", Toast.LENGTH_SHORT).show()
            }else{
                mainInfoSetting()
            }

        }
    }

    private fun adapterSetting(){
        mainAdapter = ArrayAdapter(
            this,
            R.layout.support_simple_spinner_dropdown_item,
            mainGenreList
        )
        mainAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item)
        mainGenreSpinner.adapter = mainAdapter

        subAdapter1 = ArrayAdapter(
            this,
            R.layout.support_simple_spinner_dropdown_item,
            subGenreList
        )
        subAdapter1.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item)
        subGenreSpinner1.adapter = subAdapter1

        subAdapter2 = ArrayAdapter(
            this,
            R.layout.support_simple_spinner_dropdown_item,
            subGenreList
        )
        subAdapter2.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item)
        subGenreSpinner2.adapter = subAdapter2
    }

    private fun callSubGenre(genreId : Int){
        API.likeFeel(LikeFeelBody(genreId))
            .enqueue(object : Callback<List<LikeFeelModel>>{
                override fun onResponse(
                    call: Call<List<LikeFeelModel>>,
                    response: Response<List<LikeFeelModel>>
                ) {
                    if(response.code() == 200){
                        subGenreList.clear()
                        for(e in response.body()!!){
                            e.subGenreName?.let { subGenreList.add(it) };
                        }
                        subAdapter1.notifyDataSetChanged()
                        subAdapter2.notifyDataSetChanged()
                    }
                }

                override fun onFailure(call: Call<List<LikeFeelModel>>, t: Throwable) {

                }

            })
    }

    private fun mainInfoSetting(){
        API.likeInfo1(LikeInfoMainBody(userId,mainGenreId))
            .enqueue(object : Callback<ResultModel>{
                override fun onResponse(call: Call<ResultModel>, response: Response<ResultModel>) {
                    if(response.code() == 200){
                        if(response.body()?.result == "ok"){
                            subInfoSetting(subGenreId1)
                            subInfoSetting(subGenreId2)
                        }
                    }
                }

                override fun onFailure(call: Call<ResultModel>, t: Throwable) {

                }
            })
    }

    private fun subInfoSetting(subId : Int){
        API.likeInfo2(LikeInfoSubBody(userId,subId))
            .enqueue(object : Callback<ResultModel>{
                override fun onResponse(call: Call<ResultModel>, response: Response<ResultModel>) {
                    if(response.code() == 200){
                        if(response.body()?.result == "ok"){
                            if(check){
                                Toast.makeText(this@GenreActivity,"회원가입이 완성되었습니다!", Toast.LENGTH_SHORT).show()
                                startActivity(Intent(this@GenreActivity, LoginActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                            }else{
                                check = true
                            }
                        }
                    }
                }

                override fun onFailure(call: Call<ResultModel>, t: Throwable) {

                }
            })
    }

    private fun insertMain(){
        mainGenreList.add("팝")
        mainGenreList.add("아이돌")
        mainGenreList.add("발라드")
        mainGenreList.add("힙합")
        mainGenreList.add("밴드")
        mainGenreList.add("트로트")
        mainGenreList.add("인디")
        mainGenreList.add("디스코")
        mainGenreList.add("록")

        subGenreList.add("Loading...")
    }

    override fun onBackPressed() {
        Toast.makeText(this@GenreActivity, "정보 기입 중에는 취소하실 수 없습니다.", Toast.LENGTH_SHORT).show()
    }
}
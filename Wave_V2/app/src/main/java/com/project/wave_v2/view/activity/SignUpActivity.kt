package com.project.wave_v2.view.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.project.wave_v2.R
import com.project.wave_v2.data.request.RegisterBody
import com.project.wave_v2.data.response.ResultModel
import com.project.wave_v2.network.RetrofitClient
import com.project.wave_v2.network.Service
import kotlinx.android.synthetic.main.activity_sign_up.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class SignUpActivity : AppCompatActivity() {

    lateinit var API: Service
    lateinit var retrofit: Retrofit

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        retrofit = RetrofitClient.getInstance()
        next.setOnClickListener {
            clickNext()
        }
    }

    private fun clickNext() {
        val id = id_regi.text.toString()
        val name = name_regi.text.toString()
        val pw = pwd_regi.text.toString()
        val pwCheck = pwd_check.text.toString()
        val email = email_regi.text.toString()
        if (id == "" || name == "" || pw == "" || pwCheck == "" || email == "") {
            Toast.makeText(this, "빈칸을 입력해 주세요", Toast.LENGTH_SHORT).show()
        } else {
            if(pw == pwCheck){
                API = retrofit.create(Service::class.java)
                API.register(RegisterBody(id,pw, email,name))
                        .enqueue(object : Callback<ResultModel>{
                            override fun onResponse(call: Call<ResultModel>, response: Response<ResultModel>) {
                                if(response.code() == 200){
                                    if(response.body()?.result == "ok"){
                                        val i = Intent(this@SignUpActivity, GenreActivity::class.java)
                                        i.putExtra("userId", id)
                                        startActivity(i)
                                    }else{
                                        Toast.makeText(this@SignUpActivity, "이미 사용 중인 ID입니다.", Toast.LENGTH_SHORT).show()
                                    }
                                }
                            }

                            override fun onFailure(call: Call<ResultModel>, t: Throwable) {

                            }
                        })
            }
            else{

            }

        }
    }

}
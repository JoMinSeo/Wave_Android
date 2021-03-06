package com.project.wave_v2.view.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.project.wave_v2.R
import com.project.wave_v2.data.request.LoginBody
import com.project.wave_v2.data.response.ResultModel
import com.project.wave_v2.network.RetrofitClient
import com.project.wave_v2.network.Service
import kotlinx.android.synthetic.main.activity_login.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class LoginActivity : AppCompatActivity() {

    private val KEY_USER = "user_info"

    var API: Service? = null
    lateinit var retrofit: Retrofit



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val sharedPreferences = getSharedPreferences(KEY_USER, MODE_PRIVATE)
        var id = sharedPreferences.getString("userId", "")
        if(id != ""){
            startActivity(Intent(applicationContext, MainActivity::class.java))
            finish()
        }

        retrofit = RetrofitClient.getInstance()
        API = RetrofitClient.getService()

        onClick()
    }

    private fun onClick() {
        noId_Tv.setOnClickListener {
            startActivity(Intent(this@LoginActivity, SignUpActivity::class.java))
        }

        btn_login.setOnClickListener {
            var id = et_id.text.toString().trim()
            var pw = et_pass.text.toString().trim()

            if (id.isEmpty() || pw.isEmpty()) {
                Toast.makeText(applicationContext, "제대로 입력해주시기 바랍니다.", Toast.LENGTH_SHORT).show()
            } else {

                login(id, pw)
            }
        }
    }

    private fun login(id: String, pw: String) {
        API?.login(
                LoginBody(
                        userId = id,
                        password = pw
                )
        )?.enqueue(object : Callback<ResultModel> {
            override fun onResponse(call: Call<ResultModel>, response: Response<ResultModel>) {
                Log.d("login success", response.code().toString())
                if(response.code() == 200){
                    val result = response.body()
                    if(result != null){
                        Log.d("login result", result.result)
                        if(result.result == "ok"){
                            val sharedPreferences = getSharedPreferences(KEY_USER, MODE_PRIVATE)
                            val editor = sharedPreferences.edit()
                            editor.putString("userId", id)
                            editor.putString("userPassword", pw)
                            editor.apply()

                            val intent = Intent(applicationContext, MainActivity::class.java)
                            startActivity(intent)
                            finish()
                        } else {
                            Toast.makeText(this@LoginActivity, "비밀번호를 확인해주세요.", Toast.LENGTH_SHORT).show()
                        }
                    }
                } else{
                    Toast.makeText(applicationContext, "잘못된 계정입니다.", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ResultModel>, t: Throwable) {
                Toast.makeText(this@LoginActivity, "잘못된 계정입니다.", Toast.LENGTH_SHORT).show()
                Log.d("login fail", t.message+"")
            }

        })


    }


}
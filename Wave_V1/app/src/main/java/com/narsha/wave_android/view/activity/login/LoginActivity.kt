package com.narsha.wave_android.view.activity.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.narsha.wave_android.R
import com.narsha.wave_android.data.Result
import com.narsha.wave_android.data.User
import com.narsha.wave_android.network.Server
import com.narsha.wave_android.view.activity.main.MainActivity
import com.narsha.wave_android.view.activity.signup.SignUpActivity
import kotlinx.android.synthetic.main.activity_login.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LoginActivity : AppCompatActivity() {

    private val KEY_USER = "user_info"

    var request: Call<Result>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        onClick()
    }

    fun onClick(){
        noId_Tv.setOnClickListener {
            val intent = Intent(applicationContext, SignUpActivity::class.java)
            startActivity(intent)
        }

        btn_login.setOnClickListener {
            var idEdit = et_id.text.toString()
            var pwEdit = et_pass.text.toString()

            Log.i("test", idEdit + " " + pwEdit);

            if (idEdit.isEmpty() || pwEdit.isEmpty()) {
                Toast.makeText(getApplicationContext(), "제대로 입력해주시기 바랍니다.", Toast.LENGTH_SHORT).show();
            }
            else{
                login(idEdit, pwEdit);
            }
        }
    }

    fun login(id: String, pw: String){
        var user : User = User(id, pw)

        request = Server.getInstance().api.login(user)
        request!!.enqueue(object : Callback<Result?> {
            override fun onResponse(call: Call<Result?>, response: Response<Result?>) {
                Log.i("test", response.code().toString() + "")
                if (response.code() == 200) {
                    val result = response.body()
                    if (result != null) {
                        if (result.result == "ok") {
                            Log.i("test", result.result)

                            val sharedPreferences = getSharedPreferences(KEY_USER, MODE_PRIVATE)
                            val editor = sharedPreferences.edit()
                            editor.putString("userId", id)
                            editor.putString("userPassword", pw)
                            editor.commit()

                            val intent = Intent(applicationContext, MainActivity::class.java)
                            startActivity(intent)
                            finish()
                        } else {
                            Toast.makeText(this@LoginActivity, "비밀번호를 확인해주세요.", Toast.LENGTH_SHORT).show()
                        }
                    }
                } else {
                    Toast.makeText(applicationContext, "존재하지 않는 계정입니다.", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Result?>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        request?.cancel()
    }
}
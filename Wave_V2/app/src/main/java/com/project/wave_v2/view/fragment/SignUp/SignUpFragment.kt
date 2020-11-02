package com.project.wave_v2.view.fragment.SignUp

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.project.wave_v2.R
import com.project.wave_v2.data.request.RegisterBody
import com.project.wave_v2.data.response.ResultModel
import com.project.wave_v2.network.RetrofitClient
import com.project.wave_v2.network.Service
import kotlinx.android.synthetic.main.fragment_signup.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class SignUpFragment : Fragment() {

    lateinit var API: Service
    lateinit var retrofit: Retrofit

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_signup, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        retrofit = RetrofitClient.getInstance()
        API = retrofit.create(Service::class.java)

        next.setOnClickListener {
            if(checkBlank()){
                API.register(RegisterBody(
                        id_regi.text.toString(),
                        pwd_regi.text.toString(),
                        email_regi.text.toString(),
                        name_regi.text.toString()
                )).enqueue(object : Callback<ResultModel> {
                    override fun onResponse(call: Call<ResultModel>, response: Response<ResultModel>) {
                        Log.d("SignUp", response.code().toString())

                        if (response.code() == 200) {
                            if (response.body()?.result == "ok") {

                            } else {
                                Toast.makeText(context, "이미 사용 중인 아이디입니다.", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }

                    override fun onFailure(call: Call<ResultModel>, t: Throwable) {

                    }

                })
            }
        }
    }

    private fun checkBlank() : Boolean{
        if(
            id_regi.text.toString() == "" || 
            pwd_regi.text.toString() == "" || 
            name_regi.text.toString() == "" || 
            pwd_check.text.toString() == "" || 
            email_regi.text.toString() == ""
        ){
            Toast.makeText(context, "빈칸을 입력해주세요.", Toast.LENGTH_SHORT).show()
            return false
        }else{
            if(pwd_regi.text.toString() != pwd_check.text.toString()){
                Toast.makeText(context, "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show()
                return false
            }
        }
        return true
    }

}
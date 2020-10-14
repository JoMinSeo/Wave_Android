package com.narsha.wave_android.view.fragment.signup

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.widget.AppCompatEditText
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.narsha.wave_android.R
import com.narsha.wave_android.data.Result
import com.narsha.wave_android.data.request.signup.UserSignUp
import com.narsha.wave_android.network.Server
import kotlinx.android.synthetic.main.fragment_sign_up.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignUpFragment : Fragment() {
    lateinit var request: Call<Result>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_up, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        next.setOnClickListener { view1: View? ->

            if (id_regi.text.toString() == "" || name_regi.text.toString() == "" || pwd_regi.text.toString() == "" || pwd_check.text.toString() == "" || email_regi.text.toString() == "") {
                Toast.makeText(context, "빈칸을 입력해 주세요", Toast.LENGTH_LONG).show()
            } else {

                if (pwd_regi.text.toString() == pwd_check.text.toString()) {
                    val user = UserSignUp(id_regi.text.toString(), pwd_regi.text.toString(), email_regi.text.toString(), name_regi.text.toString())
                    request = Server.getInstance().api.signUp(user)

                    request.enqueue(object : Callback<Result?> {
                        override fun onResponse(call: Call<Result?>, response: Response<Result?>) {
                            Log.i("test", response.code().toString() + "")

                            if (response.code() == 200) {
                                val result = response.body()

                                if (result!!.result == "ok") {
                                    Log.i("test", result.result)
                                    val controller = Navigation.findNavController(view)
                                    controller.navigate(R.id.action_signUpFragment_to_songSelectFragment1)
                                } else {
                                    Toast.makeText(context, "이미 사용중인 아이디입니다.", Toast.LENGTH_LONG).show()
                                }

                            }
                        }

                        override fun onFailure(call: Call<Result?>, t: Throwable) {
                            t.printStackTrace()
                            Log.i("test", "failed")
                        }
                    })
                }
            }
        }
    }
}
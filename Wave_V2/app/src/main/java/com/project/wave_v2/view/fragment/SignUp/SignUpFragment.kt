package com.project.wave_v2.view.fragment.SignUp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.project.wave_v2.R
import kotlinx.android.synthetic.main.fragment_signup.*

class SignUpFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_signup, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        next.setOnClickListener {

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
                
            }
        }
        return true
    }

}
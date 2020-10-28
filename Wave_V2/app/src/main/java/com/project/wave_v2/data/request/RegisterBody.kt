package com.project.wave_v2.data.request

//회원가입
data class RegisterBody(
    var userId : String,
    var password : String,
    var email : String,
    var name : String
)
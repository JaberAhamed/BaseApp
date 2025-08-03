package com.sj.baseapp.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class User(
    @Json(name = "email")
    val email: String,
    @Json(name = "empId")
    val empId: Int,
    @Json(name = "token_type")
    val tokenType: String,
    @Json(name = "user_name")
    val userName: String
)

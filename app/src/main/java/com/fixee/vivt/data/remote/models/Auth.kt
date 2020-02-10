package com.fixee.vivt.data.remote.models

data class Auth(val status: String, val token: String, val token_for_login: String, val userStatus: String, val menu: List<Menu>, val qr: String, val error: List<Error>)
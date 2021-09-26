package com.example.chattingwithdapplication.entity

/**
 * Created by Dhimas Saputra on 25/09/21
 * Jakarta, Indonesia.
 */
class Users {
    var username : String? = null
    var email : String? = null
    var uid : String? = null
    var uriUrl : String? = null

    constructor(username: String?, email: String?, uid: String?, uriUrl: String?) {
        this.username = username
        this.email = email
        this.uid = uid
        this.uriUrl = uriUrl
    }
}
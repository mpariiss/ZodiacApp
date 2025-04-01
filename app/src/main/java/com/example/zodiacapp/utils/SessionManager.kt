package com.example.zodiacapp.utils

import android.content.Context

class SessionManager (context: Context) {

    private val sharedPref = context.getSharedPreferences("zodiac session", Context.MODE_PRIVATE)

    fun setFavoriteHoroscope(id:String){
        val editor =sharedPref.edit()
        editor.putString("favorite_horoscope",id)
        editor.apply()

    }
    fun getFavoriteHoroscope():String{
   return  sharedPref.getString("favorite_horoscope","")!!
    }
}
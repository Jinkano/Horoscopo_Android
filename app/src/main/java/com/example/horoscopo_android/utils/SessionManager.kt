package com.example.horoscopo_android.utils

import android.content.Context
import android.content.SharedPreferences

class SessionManager(context: Context)
{
    /*
    * variable de tipo SharedPreferences
    * */
    val sharedPreferences: SharedPreferences = context.getSharedPreferences("horoscope_session",Context.MODE_PRIVATE)

    /*
    * funcion que sirve para guardar el favorito
    * */
    fun setFavorite(horoscopeId: String) {
        val editor = sharedPreferences.edit()
        editor.putString("FAVORITE_HOROSCOPE_ID", horoscopeId)
        editor.apply()
    }

    /*
    * funcion que nos devuelve el favorito
    * estos signo !! se utiliza cuando tienes la certeza de que la variable no será nula
    * */
    fun getFavorite(): String {
        return sharedPreferences.getString("FAVORITE_HOROSCOPE_ID", "")!!
    }

    /*
    *
    * */
    fun isFavorite(horoscopeId: String): Boolean {
        return horoscopeId == getFavorite()
    }
}
package com.example.raveesh.stormy.weather

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*


data class Current(
        var mIcon : String,
        var mTime : Long,
        var mTemperature : Double,
        var mHumidity : Double,
        var mPrecipChance : Double,
        var mSummary: String,
        var mTimeZone: String){


    @SuppressLint("SimpleDateFormat")
    fun getFormattedTime(): String{
        val formatter = SimpleDateFormat("h:mm a")
        formatter.timeZone = TimeZone.getTimeZone(this.mTimeZone)
        return formatter.format(Date(mTime*1000))
    }

    fun getIconId() : Int  = getId(mIcon)

}

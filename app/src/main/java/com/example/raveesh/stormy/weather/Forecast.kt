package com.example.raveesh.stormy.weather

import com.example.raveesh.stormy.R
import java.util.*

fun getId(mIcon: String) : Int  = when(mIcon){
    "clear-day" -> R.drawable.clear_day
    "clear-night" -> R.drawable.clear_night
    "rain" -> R.drawable.rain
    "snow" -> R.drawable.snow
    "sleet" -> R.drawable.sleet
    "wind" -> R.drawable.wind
    "fog" -> R.drawable.fog
    "cloudy" -> R.drawable.cloudy
    "partly-cloudy-day" -> R.drawable.partly_cloudy
    "partly-cloudy-night" -> R.drawable.partly_cloudy

    else -> R.drawable.clear_day
}


data class Forecast(
        val mHourlyForecast : Array<Hour?>,
        val mDailyForecast: Array<Day?>,
        val mCurrent: Current
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Forecast

        if (!Arrays.equals(mHourlyForecast, other.mHourlyForecast)) return false
        if (!Arrays.equals(mDailyForecast, other.mDailyForecast)) return false
        if (mCurrent != other.mCurrent) return false

        return true
    }

    override fun hashCode(): Int {
        var result = Arrays.hashCode(mHourlyForecast)
        result = 31 * result + Arrays.hashCode(mDailyForecast)
        result = 31 * result + mCurrent.hashCode()
        return result
    }
}
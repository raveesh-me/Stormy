package com.example.raveesh.stormy.weather

import android.os.Parcel
import android.os.Parcelable
import android.util.Log
import java.text.SimpleDateFormat
import java.util.*

data class Hour(
        val mTime : Long,
        val mSummary : String,
        val mTemperature : Double,
        val mIcon : String,
        val mTimeZone : String
) : Parcelable {

    constructor(parcel: Parcel) : this(
            parcel.readLong(),
            parcel.readString(),
            parcel.readDouble(),
            parcel.readString(),
            parcel.readString())

    override fun writeToParcel(dest: Parcel?, p1: Int) {
        dest?.writeLong(mTime)
        dest?.writeString(mSummary)
        dest?.writeDouble(mTemperature)
        dest?.writeString(mIcon)
        dest?.writeString(mTimeZone)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<Hour> {
        override fun createFromParcel(parcel: Parcel): Hour {
            return Hour(parcel)
        }

        override fun newArray(size: Int): Array<Hour?> {
            return arrayOfNulls(size)
        }
    }

    fun getIconId() : Int = getId(mIcon)
    fun getTemperature() : String = "${Math.round(mTemperature)}"
    fun getTime() : String {
        val formatter = SimpleDateFormat("h a")
        formatter.timeZone = TimeZone.getTimeZone(mTimeZone)
        val time = formatter.format(mTime*1000)
        Log.d("returning", time)
        return time
    }


}


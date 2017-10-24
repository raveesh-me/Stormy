package com.example.raveesh.stormy.weather

import android.annotation.SuppressLint
import android.os.Parcel
import android.os.Parcelable
import java.text.SimpleDateFormat
import java.util.*

data class Day (internal val mTime : Long,
                internal val mSummary : String,
                internal val mTemperatureMax : Double,
                internal val mIcon : String,
                internal val mTimezone : String) : Parcelable{

    //Auto-generated
    constructor(parcel: Parcel) : this(
            parcel.readLong(),
            parcel.readString(),
            parcel.readDouble(),
            parcel.readString(),
            parcel.readString())
    //written
    override fun writeToParcel(dest: Parcel?, flag: Int) {
        dest?.writeLong(mTime)
        dest?.writeString(mSummary)
        dest?.writeDouble(mTemperatureMax)
        dest?.writeString(mIcon)
        dest?.writeString(mTimezone)
    }
    override fun describeContents(): Int  = 0 //Not used


    fun getIconId() : Int = getId(mIcon)

    @SuppressLint("SimpleDateFormat")
    fun getDayOfTheWeek(): String? {
        val formatter = SimpleDateFormat("EEEE")
        formatter.timeZone = TimeZone.getTimeZone(mTimezone)
        val dateTime = Date(mTime *1000)
        return formatter.format(dateTime)
    }

    //Auto-generated
    companion object CREATOR : Parcelable.Creator<Day> {
        override fun createFromParcel(parcel: Parcel): Day {
            return Day(parcel)
        }

        override fun newArray(size: Int): Array<Day?> {
            return arrayOfNulls(size)
        }
    }
}
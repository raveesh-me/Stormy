package com.example.raveesh.stormy.ui

import android.app.ListActivity
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.ListView
import com.example.raveesh.stormy.R
import com.example.raveesh.stormy.adapters.DayAdapter
import com.example.raveesh.stormy.weather.Day
import org.jetbrains.anko.longToast
import java.util.*

class DailyForecastActivity : ListActivity() {
    val TAG = "DailyForecastActivity"
    lateinit var mDays: Array<Day>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor = Color.parseColor("#fffc970b")
        }
        setContentView(R.layout.activity_daily_forecast)
        val parcel = intent.getParcelableArrayExtra(MainActivity.DAILY_FORECAST)
        mDays = Arrays.copyOf(parcel, parcel.size, Array<Day>::class.java)
        listView.adapter = DayAdapter(this, mDays)
    }

    override fun onListItemClick(l: ListView?, v: View?, position: Int, id: Long) {
        super.onListItemClick(l, v, position, id)
        longToast(String.format("On %s the high will be %s and it will be %s"
                , mDays[position].getDayOfTheWeek()
                , mDays[position].mTemperatureMax
                , mDays[position].mSummary))
    }
}

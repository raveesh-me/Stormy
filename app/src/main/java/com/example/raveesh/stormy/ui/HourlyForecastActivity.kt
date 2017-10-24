package com.example.raveesh.stormy.ui

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.example.raveesh.stormy.R
import com.example.raveesh.stormy.adapters.HourAdapter
import com.example.raveesh.stormy.weather.Hour
import kotlinx.android.synthetic.main.activity_hourly_forecast.*
import java.util.*

class HourlyForecastActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor = Color.parseColor("#fffc970b")
        }
        setContentView(R.layout.activity_hourly_forecast)

        val parcel = intent.getParcelableArrayExtra(MainActivity.HOURLY_FORECAST)
        val mHours = Arrays.copyOf(parcel, parcel.size, Array<Hour>::class.java)
        hourlyRecyclerView.layoutManager = LinearLayoutManager(this)
        hourlyRecyclerView.adapter = HourAdapter(mHours)

    }
}

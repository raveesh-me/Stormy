package com.example.raveesh.stormy.adapters

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.example.raveesh.stormy.R
import com.example.raveesh.stormy.weather.Day
import kotlinx.android.synthetic.main.daily_list_item.view.*
import org.jetbrains.anko.imageResource
import org.jetbrains.anko.layoutInflater

internal class DayAdapter(mCtx: Context,
                          private val mDays: Array<Day>) : BaseAdapter() {

    private val mInflater = mCtx.layoutInflater
    private lateinit var row : View

    override fun getItem(position: Int): Day = mDays[position]
    override fun getCount(): Int = mDays.size
    override fun getItemId(position: Int): Long = 0//not used

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View{
        row = convertView ?: mInflater.inflate(R.layout.daily_list_item, parent, false)
        /*make sure to initialize each and every view in the order you want drawn.
        use an if-else block and put the non repetitive assignments into the if null block but need
        to initialise at least once:->
         */
        row.dailyCircleImageView.imageResource = R.drawable.bg_temperature
        row.dailyTemperatureLabel.text = "${Math.round(mDays[position].mTemperatureMax)}"
        row.dailyIconImageView.imageResource = mDays[position].getIconId()
        row.dailyNameLabel.text = if (position == 0) "Today" else mDays[position].getDayOfTheWeek()
        return row

    }


}
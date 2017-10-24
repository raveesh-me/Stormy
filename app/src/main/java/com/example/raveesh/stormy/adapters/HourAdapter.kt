package com.example.raveesh.stormy.adapters

import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.Adapter
import android.view.View
import android.view.ViewGroup
import com.example.raveesh.stormy.R
import com.example.raveesh.stormy.weather.Hour
import kotlinx.android.synthetic.main.hourly_list_item.view.*
import org.jetbrains.anko.imageResource
import org.jetbrains.anko.layoutInflater
import org.jetbrains.anko.longToast

class HourAdapter(private val mHours : Array<Hour>) : Adapter<HourAdapter.HourViewHolder>(){

    private lateinit var mHour : Hour
    override fun onBindViewHolder(holder: HourViewHolder, position: Int) {
        mHour = mHours[position]
        holder.mIconView.imageResource = mHour.getIconId()
        holder.mSummaryLabel.text = mHour.mSummary
        holder.mTemperatureLabel.text = mHour.getTemperature()
        holder.mTimeLabel.text = mHour.getTime()
    }

    override fun getItemCount(): Int = mHours.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HourViewHolder {
        val v = parent
                .context
                .layoutInflater
                .inflate(R.layout.hourly_list_item, parent, false)
        return HourViewHolder(v)
    }

    class HourViewHolder(itemView: View) :
            RecyclerView.ViewHolder(itemView),
            View.OnClickListener  {

        init { itemView.setOnClickListener(this) }
        val mIconView = itemView.hourlyIconView!!
        val mSummaryLabel = itemView.hourlySummaryLabel!!
        val mTemperatureLabel = itemView.hourlyTemperatureLabel!!
        val mTimeLabel = itemView.hourlyTimeLabel!!

        override fun onClick(p0: View) {
            p0.context.longToast(String.format("At %s the it will be %s and %s"
                    , mTimeLabel.text
                    , mTemperatureLabel.text
                    , mSummaryLabel.text)
            )
        }
    }

}
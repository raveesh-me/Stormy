package com.example.raveesh.stormy.ui

import android.content.Context
import android.graphics.Color
import android.net.ConnectivityManager
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import com.example.raveesh.stormy.R
import com.example.raveesh.stormy.weather.Current
import com.example.raveesh.stormy.weather.Day
import com.example.raveesh.stormy.weather.Forecast
import com.example.raveesh.stormy.weather.Hour
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.*
import org.jetbrains.anko.imageResource
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException



class MainActivity : AppCompatActivity() {

    companion object {
        val DAILY_FORECAST = "DAILY_FORECAST"
        val HOURLY_FORECAST = "HOURLY_FORECAST"
    }
    val TAG = MainActivity::class.java.simpleName!!
    private var mForecast :Forecast? = null
//    kolkata
    private val latitude: Double = 22.511437
    private val longitude: Double = 88.399471

//    Ranchi
//    private val latitude: Double = 23.327047
//    private val longitude: Double = 85.304480


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor = Color.parseColor("#fffc970b")
        }
        setContentView(R.layout.activity_main)
        progressBar.visibility = View.INVISIBLE
        refreshImageView.visibility = View.VISIBLE
        refreshImageView.onClick { getForecast(latitude, longitude) }

        getForecast(latitude, longitude)
        dailyButton.onClick {
            if (mForecast == null) {
                toast("Wait for magic to begin!")
            }else{
            val mDays = mForecast!!.mDailyForecast
            startActivity<DailyForecastActivity>(DAILY_FORECAST to mDays)
            }
        }

        hourlyButton.onClick {
            if (mForecast == null) {
                toast("Wait")
            }else {
                val mHours = mForecast!!.mHourlyForecast
                startActivity<HourlyForecastActivity>(HOURLY_FORECAST to mHours)
            }
        }
    }

    private fun toggleRefreshVisibility() {
        if (progressBar.visibility == View.VISIBLE) {
            progressBar.visibility = View.INVISIBLE
            refreshImageView.visibility = View.VISIBLE
        }else{
            progressBar.visibility = View.VISIBLE
            refreshImageView.visibility = View.INVISIBLE
        }
    }

    private fun getForecast(latitude : Double, longitude : Double) {
        val apiKey = "34887d4cbecd485d94904b7ee873f5be"
        val forecastURL = "https://api.darksky.net/forecast/$apiKey/$latitude,$longitude?units=si"

        if (isNetworkAvailable()) {
            toggleRefreshVisibility()
            val client = OkHttpClient()
            val request: Request = Request.Builder()
                    .url(forecastURL)
                    .build()


            val call: Call = client.newCall(request)
            call.enqueue(object : Callback {
                override fun onFailure(call: Call?, e: IOException?) {
                    runOnUiThread { toggleRefreshVisibility() }
                    alertUserAboutError()

                }

                override fun onResponse(call: Call?, response: Response?) {
                    runOnUiThread { toggleRefreshVisibility() }
                    try {
                        val jsonData = response!!.body()?.string()
                        if (response.isSuccessful) {
                            mForecast = getForecastDetails(jsonData)
                            runOnUiThread { updateView() }

                        } else {
                            alertUserAboutError()
                        }

                    } catch (e: IOException) {
                        Log.e(TAG, "Exception Caught", e)
                    } catch (e: JSONException) {
                        Log.e(TAG, "Exception Caught", e)
                    }
                }
            })

        } else {
            toast("network is inactive")
        }
    }

    @Throws(JSONException::class)
    private fun getForecastDetails(jsonData: String?): Forecast{
        return Forecast(
                getHourlyForecast(jsonData),
                getDailyForecast(jsonData),
                getCurrentDetails(jsonData)
        )

    }

    @Throws(JSONException::class)
    private fun getHourlyForecast(jsonData: String?): Array<Hour?> {
        val forecast = JSONObject(jsonData)
        val hourly = forecast.getJSONObject("hourly")
        val data = hourly.getJSONArray("data")
        var mdata: JSONObject

        val hourlyArray = arrayOfNulls<Hour>(data.length())
        for (i in 0 until data.length()) {

            mdata = data.getJSONObject(i)
            hourlyArray[i] = Hour(
                    mdata.getLong("time"),
                    hourly.getString("summary"),
                    mdata.getDouble("temperature"),
                    mdata.getString("icon"),
                    forecast.getString("timezone")
            )
        }

        return hourlyArray
    }

    @Throws(JSONException::class)
    private fun getDailyForecast(jsonData: String?): Array<Day?> {
        val forecast = JSONObject(jsonData)
        val daily = forecast.getJSONObject("daily")
        val data = daily.getJSONArray("data")
        var mdata : JSONObject

        val dailyArray = arrayOfNulls<Day>(data.length())
        for (i in 0 until data.length()){

            mdata = data.getJSONObject(i)
            dailyArray[i] = Day(
                    mdata.getLong("time"),
                    daily.getString("summary"),
                    mdata.getDouble("temperatureMax"),
                    daily.getString("icon"),
                    forecast.getString("timezone")
            )
        }

        return  dailyArray
    }

    @Throws(JSONException::class)
    private fun getCurrentDetails(jsonData: String?): Current {
        val forecast = JSONObject(jsonData)
        val currently = forecast.getJSONObject("currently")

        return Current(
                currently.getString("icon"),
                currently.getLong("time"),
                currently.getDouble("temperature"),
                currently.getDouble("humidity"),
                currently.getDouble("precipProbability"),
                currently.getString("summary"),
                forecast.getString("timezone")
        )
    }

    private fun updateView() {
        val current = mForecast!!.mCurrent

        temperatureLabel.text = current.mTemperature.toInt().toString()
        timeLabel.text = "At ${current.getFormattedTime()} it will be"
        iconImageView.imageResource = current.getIconId()
        humidityValue.text = current.mHumidity.toString()
        precipValue.text = current.mPrecipChance.toString()
        summaryTextView.text = current.mSummary
        location_label.text = current.mTimeZone
    }

    private fun alertUserAboutError() {
        val dialog = AlertDialogFragment()
        dialog.show(fragmentManager, "error_dialog")

    }

    private fun isNetworkAvailable(): Boolean {
        val manager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val info = manager.activeNetworkInfo
        var isAvailable = false
        if(info != null && info.isConnected){
         isAvailable = true
        }
        return isAvailable
    }

}


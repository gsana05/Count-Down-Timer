package com.example.countdowntimer

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class ActivityDateCounter : AppCompatActivity() {

    private var countDownDate : Calendar? = null
    private var mTimeLeftInMillis : Long = 0L
    private var countDown : CountDownTimer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_date_counter)

        val buttonCalendar = findViewById<Button>(R.id.button_calender)

        // show a date picker
        buttonCalendar.setOnClickListener {
            pickDate()
        }

        val restartBtn = findViewById<Button>(R.id.restart)
        restartBtn.setOnClickListener {
            countDown?.cancel()
            mTimeLeftInMillis = 0L
            val tv = findViewById<TextView>(R.id.mycountdown)

            tv.text = "Enter date"


        }

    }

    private fun startTimer(){

        countDown = object : CountDownTimer(mTimeLeftInMillis, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                mTimeLeftInMillis = millisUntilFinished

                formatTimer()

            }

            @SuppressLint("SetTextI18n")
            override fun onFinish() {

            }
        }.start()

    }

    @SuppressLint("SetTextI18n")
    fun pickDate() {
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)
        val hour = c.get(Calendar.HOUR)
        val minute = c.get(Calendar.MINUTE)

        DatePickerDialog(this, DatePickerDialog.OnDateSetListener { _, yearVal, monthOfYear, dayOfMonth ->
            TimePickerDialog(this, TimePickerDialog.OnTimeSetListener{ _, hour, minute ->
                c.set(yearVal,monthOfYear,dayOfMonth,hour,minute)

                countDownDate = c

                mTimeLeftInMillis = c.timeInMillis
                startTimer()


            }, hour,minute,false).show()
        }, year, month, day).show()
    }

    private fun formatTimer(){

        val today = Calendar.getInstance().timeInMillis

        val days = (mTimeLeftInMillis - today)/86400000
        val hours = (mTimeLeftInMillis - today)%86400000/3600000
        val minutes = (mTimeLeftInMillis - today)%86400000%3600000/60000
        val seconds = (mTimeLeftInMillis - today)%86400000%3600000%60000/1000

        val format = "$days days $hours hour $minutes minutes $seconds seconds"

        val tv = findViewById<TextView>(R.id.mycountdown)

        tv.text = format

    }
}
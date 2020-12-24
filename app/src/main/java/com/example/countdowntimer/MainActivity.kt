package com.example.countdowntimer

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.util.*


class MainActivity : AppCompatActivity() {

    companion object{
        private const val START_TIME_IN_MILLIS = 300000L // 5 minutes
    }

    private lateinit var countDown : TextView
    private lateinit var button: Button
    private var numberOfStarts = 0

    private var mTimeLeftInMillis = START_TIME_IN_MILLIS

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val dateCounterActivity = findViewById<Button>(R.id.date_count_down)
        dateCounterActivity.setOnClickListener {
            val intent = Intent(this, ActivityDateCounter::class.java)
            startActivity(intent)
        }


        button = findViewById(R.id.button)
        button.setOnClickListener {
            startTimer()
        }

        countDown = findViewById(R.id.count_down_text_view)

    }

    private fun formatTimer(){
        val minutes = (mTimeLeftInMillis/1000) / 60 //mTimeLeftInMillis/1000 = number of seconds divide by 60 number of minutes
        val seconds = (mTimeLeftInMillis/1000) % 60 // remaining seconds

        val format = String.format(Locale.UK,"%02d : %02d", minutes, seconds)

        countDown.text = format
    }

    private fun startTimer(){

        object : CountDownTimer(mTimeLeftInMillis, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                mTimeLeftInMillis = millisUntilFinished
                formatTimer()
            }

            @SuppressLint("SetTextI18n")
            override fun onFinish() {
                countDown.text = "done!"
                startTimer()
                numberOfStarts++
                button.text = numberOfStarts.toString()

            }
        }.start()
    }
}
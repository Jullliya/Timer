package dev.jullls.timer

import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {

    private lateinit var timer: CountDownTimer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val textView: TextView = findViewById(R.id.tv_time)
        val startTimer: Button = findViewById(R.id.start_button)
        val pauseTimer: Button = findViewById(R.id.pause_button)
        val resetTimer: Button = findViewById(R.id.reset_button)
        val inputTime: EditText = findViewById(R.id.et_input_time)

        var time = 7000000L

        startTimer.setOnClickListener {
            if (inputTime.text.toString() != "") {
                time = toMillisTime(inputTime.text.toString())
                inputTime.text.clear()
            }

            timer = object : CountDownTimer(time, 1000) {
                override fun onTick(remaining: Long) {
                    textView.text = toStringTime(remaining)
                }

                override fun onFinish() {
                    textView.text = getString(R.string.timer_done)
                }

            }
            timer.start()
            startTimer.text = getString(R.string.start)
        }

        pauseTimer.setOnClickListener {
            if (textView.text != getString(R.string.timer_done)) {
                time = toMillisTime(textView.text.toString())
                timer.cancel()
                startTimer.text = getString(R.string.resume)
            }
        }

        resetTimer.setOnClickListener {
            time = 0L
            textView.text = toStringTime(time)
            startTimer.text = getString(R.string.start)
            timer.cancel()

        }
    }

    private fun toMillisTime(time: String): Long {
        val massiveTime = time.split(":")
        val seconds = massiveTime[2].toLong() * 1000
        val minutes = massiveTime[1].toLong() * 60 * 1000
        val hours = massiveTime[0].toLong() * 3600 * 1000
        return hours + minutes + seconds
    }

    fun toStringTime(time: Long): String {
        val seconds = time / 1000 % 60
        val minutes = time / 1000 % 3600 / 60
        val hours = time / 1000 / 3600
        val newTime = (if (hours < 10) "0$hours:" else "$hours:") +
                (if (minutes < 10) "0$minutes:" else "$minutes:") +
                (if (seconds < 10) "0$seconds" else "$seconds")
        return newTime

    }
}
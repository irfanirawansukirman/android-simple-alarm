package id.pamoyanan_dev.gitsalarm

import android.annotation.TargetApi
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val countDownTimer = object : CountDownTimer(6000, 1000) {
            override fun onFinish() {
                var message: String

                // alarm berulang
                message = "Alarm service dimulai"
                startService(Intent(this@MainActivity, AlarmService::class.java))

                // alarm sekali jalan
                message = "Alarm akan menyala dalam hitungan waktu mundur"
                setScheduleNotification()

                txt_counter.text = message
            }

            override fun onTick(millisUntilFinished: Long) {
                txt_counter.text = TimeUnit.SECONDS.convert(millisUntilFinished / 1000, TimeUnit.SECONDS).toString()
            }
        }
        countDownTimer.start()
    }

    @TargetApi(Build.VERSION_CODES.M)
    fun setScheduleNotification() {
        // membuat objek intent yang mana akan menjadi target selanjutnya
        // bisa untuk berpindah halaman dengan dan tanpa data.
        val intent = Intent(this@MainActivity, AboutActivity::class.java)
        intent.putExtra("key", "value")

        // membuat objek PendingIntent yang berguna sebagai penampung intent dan aksi yang akan dikerjakan
        val requestCode = 0
        val pendingIntent =
            PendingIntent.getActivity(this@MainActivity, requestCode, intent, 0)

        // membuat objek AlarmManager untuk melakukan pendataran alarm yang akan dijadwalkan
        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager

        // kita buat alarm yang dapat berfungsi walaupun dalam kondisi hp idle dan tepat waktu
        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            System.currentTimeMillis() + 5000,
            pendingIntent
        )
    }
}

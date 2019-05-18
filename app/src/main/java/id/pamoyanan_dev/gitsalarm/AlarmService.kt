package id.pamoyanan_dev.gitsalarm

import android.annotation.TargetApi
import android.app.AlarmManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import java.util.*

class AlarmService : Service() {

    private var timer: Timer? = null
    private var timerTask: TimerTask? = null

    override fun onCreate() {
        super.onCreate()
        setupTimer()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    private fun setupTimer() {
        timer = Timer()

        // menjadwalkan alarm per satu jam sekali
        setupTimerTask()

        timer?.schedule(timerTask, 1000, 3600000)
    }

    private fun setupTimerTask() {
        timerTask = object : TimerTask() {
            override fun run() {
                setScheduleNotification()
            }
        }
    }

    private fun stopTimer() {
        if (timer != null) {
            timer?.cancel()
            timer = null
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    fun setScheduleNotification() {
        // membuat objek calendar dan inisialisasi parameter waktunya
        val calendar = Calendar.getInstance()
        val hour = 19
        val minute = 30
        val second = 0

        // lakukan konfigurasi berdasarkan waktu yang sudah ditetapkan sebelumnya
        calendar.apply {
            set(Calendar.HOUR_OF_DAY, hour)
            set(Calendar.MINUTE, minute)
            set(Calendar.SECOND, second)
        }

        // membuat objek intent yang mana akan menjadi target selanjutnya
        // bisa untuk berpindah halaman dengan dan tanpa data.
        val intent = Intent(applicationContext, AlarmBroadcastReceiver::class.java)
        intent.putExtra("validationTime", "19:30:00")

        // membuat objek PendingIntent yang berguna sebagai penampung intent dan aksi yang akan dikerjakan
        val requestCode = 0
        val pendingIntent =
            PendingIntent.getBroadcast(applicationContext, requestCode, intent, 0)

        // membuat objek AlarmManager untuk melakukan pendataran alarm yang akan dijadwalkan
        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager

        // kita buat alarm yang dapat berfungsi walaupun dalam kondisi hp idle dan tepat waktu
        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            pendingIntent
        )
    }

    override fun onTaskRemoved(rootIntent: Intent?) {
        super.onTaskRemoved(rootIntent)
        stopTimer()
    }

}
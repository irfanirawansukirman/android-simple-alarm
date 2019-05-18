package id.pamoyanan_dev.gitsalarm

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import id.pamoyanan_dev.gitsalarm.NotificationUtil.createNotificationChannel

class AboutActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)
        if (intent.getStringExtra("key").isNotEmpty()) {
            Toast.makeText(this@AboutActivity, intent.getStringExtra("key"), Toast.LENGTH_SHORT).show()
            createNotificationChannel(this)
        }
    }
}

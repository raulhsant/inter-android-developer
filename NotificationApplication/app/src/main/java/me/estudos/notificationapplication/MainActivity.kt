package me.estudos.notificationapplication

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var buttonSend: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        buttonSend = findViewById(R.id.btn_send_notification)
        buttonSend.setOnClickListener {
            this.showNotification("1234", "bootcamp Android", "Curso Android Kotlin")
        }
    }
}
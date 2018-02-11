package io.agora.agora

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class DisplayMessageActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display_message)

        val intent: Intent = getIntent()
        val message: String = intent.getStringExtra("agora.io.MESSAGE")

        val textView: TextView = findViewById(R.id.textView)
        textView.setText(message)
    }
}

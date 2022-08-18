package com.jin.hanmo_social

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.jin.hanmo_social.social.SocialActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //지구본 아이콘 클릭 -> 게시판 이동
        val social = findViewById<ImageView>(R.id.social)
        social.setOnClickListener {
            val intent = Intent(this, SocialActivity::class.java)
            startActivity(intent)
        }
    }
}
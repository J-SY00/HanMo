package com.jin.hanmo_social.social

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.jin.hanmo_social.R
import com.jin.hanmo_social.databinding.ActivityWriteBinding
import java.text.SimpleDateFormat
import java.util.*

class WriteActivity : AppCompatActivity() {
    private lateinit var binding : ActivityWriteBinding
    private val database = Firebase.database

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_write)

        binding.upload.setOnClickListener{
            val title = binding.title.text.toString()
            val content = binding.content.text.toString()

            val CurrentDateTime = Calendar.getInstance().time
            val dataFormat = SimpleDateFormat("yyyy.MM.dd HH:mm", Locale.KOREA).format(CurrentDateTime)
            val boardRef = database.getReference("board")
            boardRef
                .push()
                .setValue(ContentModel(title,content,"uid",dataFormat))

            Toast.makeText(this,"게시글 작성 완료", Toast.LENGTH_LONG).show()
            finish()
        }
    }
}
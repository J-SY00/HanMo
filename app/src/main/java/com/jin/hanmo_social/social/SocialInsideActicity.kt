package com.jin.hanmo_social.social

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.databinding.Observable
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.jin.hanmo_social.FBAuth
import com.jin.hanmo_social.R
import com.jin.hanmo_social.comment.CommentListAdapter
import com.jin.hanmo_social.comment.CommentModel
import com.jin.hanmo_social.comment.ListViewModel
import com.jin.hanmo_social.databinding.SocialInsideActicityBinding
import org.w3c.dom.Comment
import java.util.Observer

class SocialInsideActicity : AppCompatActivity() {

    private lateinit var CommentRecyclerView : RecyclerView
    private lateinit var binding : SocialInsideActicityBinding
    private lateinit var CommentList:ArrayList<CommentModel>
    private val database = Firebase.database
    private lateinit var dbRef : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.social_inside_acticity)

        //SocialActivity에서 받은 title,content 문자열로 바꿔서 화면에 보이기
        val title = intent.getStringExtra("title").toString()
        val content = intent.getStringExtra("content").toString()

        //activity_social_inside에서의 배치에 맞게 설정
        binding.showTitle.text=title
        binding.showContent.text=content

        //댓글 입력 후 공유 버튼 누르면 파이어베이스에 저장
        binding.CommentUpload.setOnClickListener{
            val comment = binding.writeComment.text.toString()
            val time = FBAuth.getTime()

            val boardRef = database.getReference("comment")
            boardRef
                .push()
                .setValue(CommentModel(comment,time))
            Toast.makeText(this,"댓글 작성 완료",Toast.LENGTH_LONG).show()
        }

        //댓글 목록 보이도록
        CommentRecyclerView = findViewById(R.id.CommentView)
        CommentRecyclerView.layoutManager = LinearLayoutManager(this)
        CommentRecyclerView.setHasFixedSize(true)

        CommentList = arrayListOf<CommentModel>()
        getCommentDATA()
    }

    private fun getCommentDATA(){
        CommentRecyclerView.visibility= View.GONE

        //파이어베이스의 board 내용 가져오기
        dbRef = FirebaseDatabase.getInstance().getReference("comment")

        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                CommentList.clear()
                if(snapshot.exists()){
                    for(ampSnap in snapshot.children){
                        val CommentData = ampSnap.getValue(CommentModel::class.java)
                        CommentList.add(CommentData!!)
                    }
                    //최근 생성 게시물부터 보이게
                    CommentList.reverse()
                    CommentRecyclerView.visibility = View.VISIBLE
                }
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
}

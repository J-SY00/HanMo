package com.jin.hanmo_social.social

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import com.jin.hanmo_social.R

class SocialActivity : AppCompatActivity() {

    private lateinit var ListRecyclerView : RecyclerView
    private lateinit var List:ArrayList<ContentModel>
    private lateinit var dbRef : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_social)

        //작성 아이콘 클릭 -> 작성화면으로 이동
        val write = findViewById<ImageView>(R.id.write)
        write.setOnClickListener {
            val intent = Intent(this, WriteActivity::class.java)
            startActivity(intent)
        }

        //ContentView = activity_social 에서 리사이클러뷰의 아이디
        ListRecyclerView = findViewById(R.id.ContentView)
        ListRecyclerView.layoutManager = LinearLayoutManager(this)
        ListRecyclerView.setHasFixedSize(true)

        List = arrayListOf<ContentModel>()
        getData()
    }

    private fun getData(){
        ListRecyclerView.visibility= View.GONE

        //파이어베이스의 board 내용 가져오기
        dbRef = FirebaseDatabase.getInstance().getReference("board")

        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                List.clear()
                if(snapshot.exists()){
                    for(ampSnap in snapshot.children){
                        val BoardData = ampSnap.getValue(ContentModel::class.java)
                        List.add(BoardData!!)
                    }

                    //목록 클릭 -> 화면 전환
                    val mAdapter = SocialViewAdapter(List)
                    ListRecyclerView.adapter = mAdapter
                    //목록 클릭하면 해당 목록 List에서 title,content 찾아와서 확대 페이지(SocialInsideActivity)에서 보이게 하기
                    mAdapter.setOnItemClickListener(object : SocialViewAdapter.onItemClickListener {
                        override fun onItemClick(position: Int) {
                            val intent = Intent(this@SocialActivity, SocialInsideActicity::class.java)
                            intent.putExtra("title",List[position].title)
                            intent.putExtra("content",List[position].content)
                            startActivity(intent)
                        }
                    })
                    //최근 생성 게시물부터 보이게
                    List.reverse()
                    ListRecyclerView.visibility = View.VISIBLE
                }
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
}
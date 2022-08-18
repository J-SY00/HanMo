package com.jin.hanmo_social.social

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.jin.hanmo_social.R

class SocialViewAdapter(private val contentList:ArrayList<ContentModel>):
    RecyclerView.Adapter<SocialViewAdapter.ViewHolder>(){

    private lateinit var mListener: onItemClickListener

    //리스트 클릭 시 배열 숫자로 전달
    interface onItemClickListener{
        fun onItemClick(position: Int)
    }

    //onItemClickListener 객체 전달...????
    fun setOnItemClickListener(listener: onItemClickListener){
        mListener=listener
    }

    //화면에 보이는 리사이클러 레이아웃과 클릭 이벤트
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.social_recycler_model,parent,false)
        return ViewHolder(itemView,mListener)
    }

    //리사이클러에 보이는 데이터 지정 (ContentData의 내용 : contentList)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val CurrentList = contentList[position]
        holder.title.text=CurrentList.title
        holder.context.text=CurrentList.content
        holder.time.text=CurrentList.time
    }

    override fun getItemCount(): Int {
        return contentList.size
    }

    //리사이클러 뷰 안 내용 배치와 데이터 연결 (제목,내용,시간)
    class ViewHolder(itemView: View, listener: onItemClickListener): RecyclerView.ViewHolder(itemView){
        val title: TextView = itemView.findViewById(R.id.text_title)
        val context: TextView = itemView.findViewById(R.id.text_context)
        val time: TextView = itemView.findViewById(R.id.model_time)

        //클릭 이벤트
        init{
            itemView.setOnClickListener{
                listener.onItemClick(adapterPosition)
            }
        }
    }
}
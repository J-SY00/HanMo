package com.jin.hanmo_social.comment

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.jin.hanmo_social.R
import com.jin.hanmo_social.social.ContentModel
import org.w3c.dom.Comment

class CommentListAdapter (private val commentList: ArrayList<CommentModel>):
    RecyclerView.Adapter<CommentListAdapter.ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup,viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.comment_recycler_model,parent,false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val commentList = commentList[position]
        holder.comment.text=commentList.commentTitle
        holder.time.text=commentList.commentTime
    }

    override fun getItemCount(): Int {
        return commentList.size
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val comment: TextView = itemView.findViewById(R.id.model_comment)
        val time: TextView = itemView.findViewById(R.id.model_time)
    }
}
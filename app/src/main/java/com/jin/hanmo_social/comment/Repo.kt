package com.jin.hanmo_social.comment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class Repo {
    fun getData(): LiveData<MutableList<CommentModel>>{
        val mutableList = MutableLiveData<MutableList<CommentModel>>()
        val database = Firebase.database
        val myRef = database.getReference("comment")
        myRef.addValueEventListener(object :ValueEventListener{
            val listData: MutableList<CommentModel> = mutableListOf<CommentModel>()
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    for(userSnapshot in snapshot.children){
                        val getData = userSnapshot.getValue(CommentModel::class.java)
                        listData.add(getData!!)
                        mutableList.value = listData
                    }
                }
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
        return mutableList
    }
}
package com.jin.hanmo_social.comment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ListViewModel:ViewModel() {
    private val repo = Repo()
    fun fetchData():LiveData<MutableList<CommentModel>>{
        val mutableData = MutableLiveData<MutableList<CommentModel>>()
        repo.getData().observeForever{
            mutableData.value = it
        }
        return mutableData
    }
}
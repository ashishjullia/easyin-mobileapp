package com.example.easyin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_view_identity.*
import kotlinx.android.synthetic.main.record__view_layout.*

class ViewIdentity : AppCompatActivity() {
    private var titlesList = mutableListOf<String>()
    private var imagesList = mutableListOf<Int>()

    private lateinit var linearLayoutManager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_identity)
        postToList()


        linearLayoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = linearLayoutManager


        recyclerView.adapter = RecyclerAdapter(titlesList, imagesList)
    }
    private fun addToList(title:String,image:Int) {
        titlesList.add(title)
        imagesList.add(image)
    }
    private fun postToList() {
        for(i in 1..25) {
            addToList("Title $i", R.mipmap.ic_launcher_round)
        }
    }

}
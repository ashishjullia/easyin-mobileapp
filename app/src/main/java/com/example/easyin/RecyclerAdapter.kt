package com.example.easyin

import android.icu.text.CaseMap
import android.telecom.Call
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewParent
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.recyclerview.widget.RecyclerView

class RecyclerAdapter(private var title: List<String>, private var images: List<Int>) :
    RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemTitle: TextView = itemView.findViewById(R.id.textView)
        val itemPicture: ImageView = itemView.findViewById((R.id.imageView))

        init {
            itemView.setOnClickListener {
                val position : Int = adapterPosition
                Toast.makeText(itemView.context,"Do you want to delete this item # ${position + 1}",Toast.LENGTH_SHORT).show()
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.record__view_layout,parent,false)
        return ViewHolder(v)

    }

    override fun getItemCount(): Int {
        return title.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemTitle.text=title[position]
        holder.itemPicture.setImageResource(images[position])
    }
}
package com.example.easyin

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_view_identity_items.view.*
import org.w3c.dom.Text

class IdentityAdaptor(private val keyList: List<IdentityItem>) : RecyclerView.Adapter<IdentityAdaptor.IdentityViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IdentityViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.activity_view_identity_items,
            parent, false)

        println("KEYLIST "+ " "+keyList)

        return IdentityViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: IdentityViewHolder, position: Int) {
        val currentItem = keyList[position]
        holder.keyName.text = currentItem.textKeyName
        holder.key.text = currentItem.textKey
    }

    override fun getItemCount() = keyList.size

    class IdentityViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val keyName: TextView = itemView.text_view_key_name
        val key: TextView = itemView.text_view_key
    }
}
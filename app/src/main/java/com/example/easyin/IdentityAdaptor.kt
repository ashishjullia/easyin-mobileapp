package com.example.easyin

import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_view_identity_items.view.*
import org.w3c.dom.Text

class IdentityAdaptor(
    private val keyList: List<IdentityItem>,
    private val listener: OnItemClickListener)
    :
    RecyclerView.Adapter<IdentityAdaptor.IdentityViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IdentityViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.activity_view_identity_items,
            parent, false)
        return IdentityViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: IdentityViewHolder, position: Int) {
        val currentItem = keyList[position]
        holder.email.text = currentItem.textEmail
    }

    override fun getItemCount() = keyList.size

    inner class IdentityViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
    View.OnClickListener{
          val email: TextView = itemView.text_view_email

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position: Int = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listener.onItemClick(position)
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }
}
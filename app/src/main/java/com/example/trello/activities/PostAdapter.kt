package com.example.trello.activities

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.trello.R
import com.example.trello.activities.adapters.BoardItemAdapter

open  class PostAdapter(val post: ArrayList<String>,
                        val listener: OnItemClickListener) : RecyclerView.Adapter<PostAdapter.ViewHolder>() {

//    private var onClickListener: OnClickListener? = null

   inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
    View.OnClickListener{
        val txt_name_rv: TextView = itemView.findViewById(R.id.txt_name_rv)

        init {
            itemView.setOnClickListener(this)
        }
        override fun onClick(v: View?) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listener.onItemClick(position)
            }
        }
    }
    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostAdapter.ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_board, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: PostAdapter.ViewHolder, position: Int) {
        val model = post[position]
        holder.txt_name_rv.text = post[position]

    }

    override fun getItemCount() : Int{
        return post.size
    }




}
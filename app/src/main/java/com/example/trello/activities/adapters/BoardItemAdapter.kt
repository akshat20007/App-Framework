package com.example.trello.activities.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.trello.R
import com.example.trello.activities.models.Tourni
import kotlinx.android.synthetic.main.item_board.view.*

open class BoardItemAdapter(private val context: Context,
                            private val list: ArrayList<Tourni>):
    RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    private var onClickListener: OnClickListener? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MyViewHolder(
            LayoutInflater.from(context)
                .inflate(R.layout.item_board,
                    parent,
                    false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val model = list[position]
        if (holder is MyViewHolder){
            Glide
                .with(context)
                .load(model.banner)
                .centerCrop()
                .placeholder(R.drawable.ic_board_place_holder)
                .into(holder.itemView.img_board_image_rv)

            holder.itemView.txt_name_rv.text = model.name
            holder.itemView.txt_created_by_rv.text = "Created By: ${model.slots}"

            holder.itemView.setOnClickListener {
                if (onClickListener != null){
                    onClickListener!!.onClick(position,model)
                }

            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
    fun setOnClickListener(onClickListener: OnClickListener) {
        this.onClickListener = onClickListener
    }

    interface OnClickListener{
        fun onClick(position: Int,model:Tourni)
    }

    private class MyViewHolder(view: View): RecyclerView.ViewHolder(view){

    }
}
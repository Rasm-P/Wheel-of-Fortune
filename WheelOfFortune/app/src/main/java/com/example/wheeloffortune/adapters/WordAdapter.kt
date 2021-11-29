package com.example.wheeloffortune.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.wheeloffortune.R

class WordAdapter : RecyclerView.Adapter<WordAdapter.ItemViewHolder>() {
    private val dataset = MutableLiveData<List<String>>()

    class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView = view.findViewById(R.id.word_title)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context).inflate(R.layout.list_word, parent, false)
        return ItemViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = dataset.value!![position]
        holder.textView.text = item.uppercase()
    }

    override fun getItemCount(): Int {
        return dataset.value!!.size

    }

    fun setCharList(wordList: List<String>) {
        dataset.value = wordList
        notifyDataSetChanged()
    }
}
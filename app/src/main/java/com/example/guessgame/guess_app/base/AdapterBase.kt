package com.example.guessgame.guess_app.base

import android.util.Log
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.guessgame.guess_app.base.view_holder.ViewHolderBase
import kotlin.collections.ArrayList
import kotlin.collections.HashSet

abstract class AdapterBase<Model>(private var items: ArrayList<Model>) :
    RecyclerView.Adapter<ViewHolderBase<Model>>() {
    constructor() : this(arrayListOf<Model>())

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderBase<Model> {
        return getViewHolder(parent, viewType)
    }

    override fun onBindViewHolder(holder: ViewHolderBase<Model>, position: Int) {

        val model = getItemByPos(position)

        holder.bindData(model)
    }

    private fun getItemByPos(position: Int): Model {
        return items[position]
    }

    override fun getItemCount(): Int = items.size

    abstract fun getViewHolder(parent: ViewGroup, viewType: Int): ViewHolderBase<Model>

    /**
     * Update the list
     * @param list - [List][MODEL]
     */
    fun submitList(list: List<Model>?) {
        if (items.isEmpty()) {
            if (list != null) {
                items.addAll(list)
            }
            notifyItemRangeChanged(0, list?.size ?: 0)
        } else {
            if (list != null && list.isNotEmpty()) {
                items.add(list.last())
                notifyItemInserted(items.size - 1);
            }
        }
    }

}

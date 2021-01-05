package com.example.quotes.app.favorite.list

import android.util.Log
import androidx.recyclerview.widget.DiffUtil

class FavoriteQuotesDiffCallback(
    private val newList: List<FavoriteQuoteListItem>,
    private val oldList: List<FavoriteQuoteListItem>
) : DiffUtil.Callback() {

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val old = oldList[oldItemPosition]
        val new = newList[newItemPosition]

        return old.name == new.name
    }

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        newList[newItemPosition] == oldList[oldItemPosition]
}

package com.example.quotes.app.favorite.list

import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.quotes.app.R
import com.example.quotes.domain.Quote
import com.example.quotes.ui_kit.ThreeTextWithRightIconView
import java.util.*

class FavoriteQuotesAdapter(
    private val onDeleteClickListener: (Quote) -> Unit,
    private val reorderListener: (newOrder: List<Quote>) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var list: List<FavoriteQuoteListItem> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return FavoriteQuotesViewHolder(ThreeTextWithRightIconView(parent.context).apply {
            icon = ContextCompat.getDrawable(parent.context, R.drawable.ic_cross)
        })
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        list.getOrNull(position)?.let {
            (holder as? FavoriteQuotesViewHolder)?.bind(it)
        }
    }

    fun setList(list: List<FavoriteQuoteListItem>) {
        val diff = DiffUtil.calculateDiff(FavoriteQuotesDiffCallback(list, this.list))
        this.list = list
        diff.dispatchUpdatesTo(this)
    }

    fun onItemMove(fromPosition: Int, toPosition: Int): Boolean {
        Collections.swap(list, fromPosition, toPosition)
        notifyItemMoved(fromPosition, toPosition)
        reorderListener.invoke(list.map { it.quote })
        return true
    }

    inner class FavoriteQuotesViewHolder(private val view: ThreeTextWithRightIconView) :
        RecyclerView.ViewHolder(view) {

        fun bind(item: FavoriteQuoteListItem) {
            view.text1 = item.name
            view.text2 = item.bidAsk
            view.text3 = item.spread

            view.iconClickListener = { onDeleteClickListener.invoke(item.quote) }
        }
    }
}

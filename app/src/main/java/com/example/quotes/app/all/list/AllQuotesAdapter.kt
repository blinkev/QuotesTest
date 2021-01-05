package com.example.quotes.app.all.list

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.quotes.domain.Quote
import com.example.quotes.ui_kit.TextWithCheckBoxView

class AllQuotesAdapter(
    private val onCheckListener: (quote: Quote, isChecked: Boolean) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var list: List<AllQuotesListItem> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        AllQuotesViewHolder(TextWithCheckBoxView(parent.context))

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        list.getOrNull(position)?.let {
            (holder as? AllQuotesViewHolder)?.bind(it)
        }
    }

    fun setList(list: List<AllQuotesListItem>) {
        this.list = list
        notifyDataSetChanged()
    }

    inner class AllQuotesViewHolder(private val view: TextWithCheckBoxView) : RecyclerView.ViewHolder(view) {

        fun bind(item: AllQuotesListItem) {
            view.text = item.name

            // view.isChecked вызовет коллбек, поэтому занулим его сначала
            view.checkListener = {}
            view.isChecked = item.isChecked

            // Теперь можно установить коллбек
            view.checkListener = { isChecked ->
                item.isChecked = isChecked
                onCheckListener.invoke(item.quote, isChecked)
            }
        }
    }
}

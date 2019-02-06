package com.javagic.exchangerates

import android.view.View
import com.javagic.exchangerates.api.ExchangeItemApi
import kotlinx.android.synthetic.main.item_exhchange.view.*

class ExchangeAdapter : RecyclerViewAdapter<ExchangeItemApi>(
    R.layout.item_exhchange,
    holderFactory = { ExchangeViewHolder(it) }
) {
    class ExchangeViewHolder(view: View) : RecyclerViewAdapter.ViewHolder<ExchangeItemApi>(view) {
        override fun bind(item: ExchangeItemApi) {
            with(view) {
                tvPair.text = item.symbol
                tvBid.text = item.bid.toString()
                tvAsk.text = item.ask.toString()
                tvPrice.text = item.price.toString()
            }
        }

    }
}
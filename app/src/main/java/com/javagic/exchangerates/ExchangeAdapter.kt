package com.javagic.exchangerates

import android.view.View
import com.javagic.exchangerates.api.ExchangeItem
import kotlinx.android.synthetic.main.item_exhchange.view.*

class ExchangeAdapter : RecyclerViewAdapter<ExchangeItem>(
    itemRes = R.layout.item_exhchange,
    holderFactory = { ExchangeViewHolder(it) }
) {
    class ExchangeViewHolder(view: View) : RecyclerViewAdapter.ViewHolder<ExchangeItem>(view) {
        override fun bind(item: ExchangeItem) = with(view) {
            with(item) {
                tvPair.text = symbol
                tvBid.text = context.getString(R.string.item_bid, bid)
                tvAsk.text = context.getString(R.string.item_ask, ask)
                tvPrice.text = price.toString()
                tvTimestamp.text = if (timestamp == 0L) ""
                else context.getString(
                    R.string.item_update,
                    (System.currentTimeMillis() / 1000) - timestamp
                )
            }
        }
    }
}
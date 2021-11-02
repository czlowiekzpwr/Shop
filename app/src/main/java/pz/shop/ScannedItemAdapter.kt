package pz.shop

import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import pz.shop.data.Item
import java.text.DateFormat
import java.text.SimpleDateFormat

class ScannedItemAdapter() : ListAdapter<Item, ScannedItemAdapter.ItemViewHolder>(ItemDiffCallback) {

    var onItemClick: ((Int) -> Unit)? = null

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val itemIconView: ImageView = itemView.findViewById(R.id.itemIcon)
        private val itemNameView: TextView = itemView.findViewById(R.id.itemName)
        private val itemPriceView: TextView = itemView.findViewById(R.id.itemPrice)
        private val itemQtyView: EditText = itemView.findViewById(R.id.itemQty)
        private var currentItem: Item? = null

        init {
            itemView.setOnLongClickListener {
                if (onItemClick != null) {
                    onItemClick!!.invoke(adapterPosition)
                    return@setOnLongClickListener true
                }
                else{
                    return@setOnLongClickListener false
                }
            }
        }

        fun bind(bound: Item){
            currentItem = bound
            itemNameView.text = bound.name
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.register_item, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val task = getItem(position)
        holder.bind(task)
    }




}

object ItemDiffCallback : DiffUtil.ItemCallback<Item>() {
    override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
        return oldItem == newItem
    }
    override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
        return oldItem.id == newItem.id
    }
}
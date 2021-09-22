package com.mtjin.free_room_tablet.views.date_select

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.mtjin.free_room_tablet.R
import com.mtjin.free_room_tablet.model.Reservation
import com.mtjin.free_room_tablet.databinding.ItemResrvationAvailableBinding

class ReservationAvailableAdapter :
    RecyclerView.Adapter<ReservationAvailableAdapter.ViewHolder>() {
    private val items: ArrayList<Reservation> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemResrvationAvailableBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_resrvation_available,
            parent,
            false
        )
        return ViewHolder(
            binding
        )
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        items[position].let {
            holder.bind(it)
        }
    }

    class ViewHolder(private val binding: ItemResrvationAvailableBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(reservation: Reservation) {
            binding.item = reservation
            binding.executePendingBindings()
        }
    }

    fun addItems(items: List<Reservation>) {
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    fun addItem(item: Reservation) {
        this.items.add(item)
        notifyDataSetChanged()
    }

    fun clear() {
        this.items.clear()
        notifyDataSetChanged()
    }
}
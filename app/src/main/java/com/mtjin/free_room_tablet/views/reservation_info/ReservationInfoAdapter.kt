package com.mtjin.free_room_tablet.views.reservation_info

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.mtjin.free_room_tablet.R
import com.mtjin.free_room_tablet.model.Reservation
import com.mtjin.free_room_tablet.databinding.ItemReservationInfoBinding

class ReservationInfoAdapter :
    RecyclerView.Adapter<ReservationInfoAdapter.ViewHolder>() {
    private val items = ArrayList<Reservation>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemReservationInfoBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_reservation_info,
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        items[position].let {
            holder.bind(it)
        }
    }

    class ViewHolder(private val binding: ItemReservationInfoBinding) :
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
package com.mtjin.free_room_tablet.views

import android.annotation.SuppressLint
import android.graphics.Color
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mtjin.free_room_tablet.R
import com.mtjin.free_room_tablet.model.Reservation
import com.mtjin.free_room_tablet.utils.convertTimestampToDate
import com.mtjin.free_room_tablet.utils.convertTimestampToTime
import com.mtjin.free_room_tablet.utils.getTimestamp
import com.mtjin.free_room_tablet.views.date_select.ReservationAvailableAdapter
import com.mtjin.free_room_tablet.views.reservation_info.ReservationInfoAdapter
import com.skydoves.elasticviews.ElasticButton

@BindingAdapter("urlImage")
fun ImageView.setUrlImage(url: String? = "") {
    Glide.with(this).load(url)
        .placeholder(R.drawable.ic_default_room)
        .into(this)
}

@BindingAdapter("setItems")
fun RecyclerView.setAdapterItems(items: List<Any>?) {
    when (adapter) {
        is ReservationInfoAdapter -> {
            items?.let {
                with(adapter as ReservationInfoAdapter) {
                    clear()
                    addItems(it as List<Reservation>)
                }
            }
        }
        is ReservationAvailableAdapter -> {
            items?.let {
                with(adapter as ReservationAvailableAdapter) {
                    clear()
                    addItems(it as List<Reservation>)
                }
            }
        }
    }
}

// 프로필 수정 버튼
@BindingAdapter("reviseState")
fun ElasticButton.reviseState(state: Boolean) {
    text = if (state) "수정완료" else "수정하기"
}

// timestamp -> 12:00
@BindingAdapter("timestampTime")
fun TextView.timestampTime(timestamp: Long) {
    text = timestamp.convertTimestampToTime()
}

// timestamp -> 11~20
@SuppressLint("SetTextI18n")
@BindingAdapter("startTimestamp", "endTimestamp")
fun TextView.timestampTerm(startTimestamp: Long, endTimestamp: Long) {
    text = startTimestamp.convertTimestampToTime() + "\n~\n" + endTimestamp.convertTimestampToTime()
}

// timestamp -> 11~20
@SuppressLint("SetTextI18n")
@BindingAdapter("startTimestampSingle", "endTimestampSingle")
fun TextView.timestampTermSingleLine(startTimestamp: Long, endTimestamp: Long) {
    text = startTimestamp.convertTimestampToTime() + "~" + endTimestamp.convertTimestampToTime()
}

// timestamp -> 11~20
@BindingAdapter("timestampDate")
fun TextView.timestampDate(timestamp: Long) {
    text = timestamp.convertTimestampToDate()
}

// 현재 이용중인 방인지에 따라 다른 텍스트와 배경색
@BindingAdapter("roomInUseStartTimestamp", "roomInUseEndTimestamp")
fun TextView.roomInUse(startTimestamp: Long, endTimestamp: Long) {
    if (getTimestamp() in startTimestamp until endTimestamp) {
        text = context.getString(R.string.proceeding_text)
        setBackgroundColor(Color.parseColor("#00B0FF"))
    } else {
        text = context.getString(R.string.reservation_text)
        setBackgroundColor(Color.parseColor("#2196F3"))
    }
}

// 현재 이용중이면 진행중, 아니면 비었음
@BindingAdapter("roomEmptyStartTimestamp", "roomEmptyEndTimestamp")
fun TextView.roomEmpty(startTimestamp: Long, endTimestamp: Long) {
    if (getTimestamp() in startTimestamp until endTimestamp) {
        text = context.getString(R.string.proceeding_text)
        setBackgroundColor(Color.parseColor("#6ED2FF"))
    } else {
        text = context.getString(R.string.empty_text)
        setBackgroundColor(Color.parseColor("#2BA2D8"))
    }
}


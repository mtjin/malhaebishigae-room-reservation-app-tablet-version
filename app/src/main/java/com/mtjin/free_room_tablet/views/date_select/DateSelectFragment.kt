package com.mtjin.free_room_tablet.views.date_select

import android.widget.DatePicker
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.mtjin.free_room_tablet.R
import com.mtjin.free_room_tablet.base.BaseFragment
import com.mtjin.free_room_tablet.databinding.FragmentDateSelectBinding
import com.mtjin.free_room_tablet.utils.convertDateTimestamp
import com.mtjin.free_room_tablet.utils.convertDateToTimestamp
import com.mtjin.free_room_tablet.utils.convertTimestampToDate
import com.mtjin.free_room_tablet.utils.getTimestamp
import com.mtjin.free_room_tablet.views.main.MainActivity
import com.mtjin.free_room_tablet.views.main.ReservationSharedViewModel
import timber.log.Timber
import java.util.*

class DateSelectFragment : BaseFragment<FragmentDateSelectBinding>(R.layout.fragment_date_select) {

    private lateinit var viewModel: ReservationSharedViewModel

    private val safeArgs: DateSelectFragmentArgs by navArgs()
    private lateinit var reserveAvailableAdapter: ReservationAvailableAdapter

    override fun init() {
        inject()
        initDatePicker()
        processIntent()
        initAdapter()
        initViewModelCallback()
    }

    private fun initViewModelCallback() {
        with(viewModel) {
            goStartTimeSelect.observe(this@DateSelectFragment, Observer {
                findNavController().navigate(DateSelectFragmentDirections.actionDateSelectFragmentToTimeSelectFragment())
            })
        }
    }

    private fun inject() {
        (activity as MainActivity).mainComponent.inject(this)
        viewModel =
            activity?.run { ViewModelProviders.of(this)[ReservationSharedViewModel::class.java] }
                ?: throw Exception(getString(R.string.invaild_activity_exception_text))
        binding.vm = viewModel
    }

    private fun initAdapter() {
        reserveAvailableAdapter =
            ReservationAvailableAdapter()
        binding.rvTimes.adapter = reserveAvailableAdapter
    }

    private fun processIntent() {
        viewModel.userToken = safeArgs.userToken
    }

    private fun initDatePicker() {
        val c = Calendar.getInstance()
        binding.dpDatePicker.minDate = System.currentTimeMillis() - 1000
        val listener = DatePicker.OnDateChangedListener { _, _year, _month, _dayOfMonth ->
            val year = _year.toString()
            val month =
                if (_month + 1 < 10) "0" + (_month + 1) else (_month + 1).toString()
            val date =
                if (_dayOfMonth < 10) "0$_dayOfMonth" else _dayOfMonth.toString()
            val pickedDate = "$year-$month-$date"
            Timber.d("PickedDate:: $pickedDate") //2020-07-28
            viewModel.requestAvailableTime(timestamp = pickedDate.convertDateToTimestamp())
            viewModel.setDate(pickedDate)
        }
        binding.dpDatePicker.init(c[Calendar.YEAR], c[Calendar.MONTH], c[Calendar.MONTH], listener)
        if (viewModel.date.value == "") {
            viewModel.requestAvailableTime(timestamp = getTimestamp().convertDateTimestamp())
            viewModel.setDate(getTimestamp().convertTimestampToDate())
        }
    }


}
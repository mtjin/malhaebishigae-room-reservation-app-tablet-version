package com.mtjin.free_room_tablet.views.time_select

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.mtjin.free_room_tablet.R
import com.mtjin.free_room_tablet.base.BaseFragment
import com.mtjin.free_room_tablet.databinding.FragmentTimeSelectBinding
import com.mtjin.free_room_tablet.utils.convertFullDateToTimestamp
import com.mtjin.free_room_tablet.utils.convertHourDoubleDigit
import com.mtjin.free_room_tablet.utils.convertMinuteDoubleDigit
import com.mtjin.free_room_tablet.views.date_select.ReservationAvailableAdapter
import com.mtjin.free_room_tablet.views.main.MainActivity
import com.mtjin.free_room_tablet.views.main.ReservationSharedViewModel
import timber.log.Timber
import java.util.*

class TimeSelectFragment :
    BaseFragment<FragmentTimeSelectBinding>(R.layout.fragment_time_select) {

    private lateinit var reserveAvailableAdapter: ReservationAvailableAdapter
    private val calendar = Calendar.getInstance()

    private lateinit var viewModel: ReservationSharedViewModel


    override fun init() {
        (activity as MainActivity).mainComponent.inject(this)
        viewModel =
            activity?.run { ViewModelProviders.of(this)[ReservationSharedViewModel::class.java] }
                ?: throw Exception(getString(R.string.invaild_activity_exception_text))
        binding.vm = viewModel
        initAdapter()
        initStartTimePicker()
        initEndTimePicker()
        initViewModelCallback()
    }

    private fun initViewModelCallback() {
        with(viewModel) {
            goReservation.observe(this@TimeSelectFragment, Observer {
                findNavController().navigate(TimeSelectFragmentDirections.actionTimeSelectFragmentToReservationFragment())
            })

            messageSet.observe(this@TimeSelectFragment, Observer {
                when (messageSet.value) {
                    ReservationSharedViewModel.MessageSet.NETWORK_ERROR -> showInfoSnackBar(
                        getString(R.string.network_error_msg)
                    )
                    ReservationSharedViewModel.MessageSet.RESERVATION_ERROR -> {
                        showNegativeSnackBar(getString(R.string.reservation_fail_text))
                    }
                    ReservationSharedViewModel.MessageSet.START_TIME_EMPTY -> {
                        showInfoSnackBar(getString(R.string.input_start_time_text))
                    }
                    ReservationSharedViewModel.MessageSet.END_TIME_EMPTY -> {
                        showInfoSnackBar(getString(R.string.input_end_time_text))
                    }
                    else -> Timber.d(it.toString())
                }
            })
        }
    }

    private fun initAdapter() {
        reserveAvailableAdapter =
            ReservationAvailableAdapter()
        binding.rvTimes.adapter = reserveAvailableAdapter
    }

    private fun initStartTimePicker() {
        binding.tpStartTimePicker.run {
            setIs24HourView(true)
            setOnTimeChangedListener { _, _hourOfDay, _minute ->
                val hourOfDay: String =
                    _hourOfDay.toString().convertHourDoubleDigit() //두자리수의 String 으로 변환
                val minute: String = _minute.toString().convertMinuteDoubleDigit()
                val timestamp =
                    (viewModel.date.value + '-' + hourOfDay + ':' + minute).convertFullDateToTimestamp()
                viewModel.setStartTime(timestamp)
            }
            if (viewModel.startTime.value == "") {
                hour = calendar[Calendar.HOUR_OF_DAY]
                minute = calendar[Calendar.MINUTE]
            }
        }
    }

    private fun initEndTimePicker() {
        binding.tpEndTimePicker.run {
            setIs24HourView(true)
            hour = calendar[Calendar.HOUR_OF_DAY]
            minute = calendar[Calendar.MINUTE]
            setOnTimeChangedListener { _, _hourOfDay, _minute ->
                val hourOfDay: String =
                    _hourOfDay.toString().convertHourDoubleDigit() //두자리수의 String 으로 변환
                val minute: String = _minute.toString().convertMinuteDoubleDigit()
                val timestamp =
                    (viewModel.date.value + '-' + hourOfDay + ':' + minute).convertFullDateToTimestamp()
                viewModel.setEndTime(timestamp)
            }
        }
    }
}
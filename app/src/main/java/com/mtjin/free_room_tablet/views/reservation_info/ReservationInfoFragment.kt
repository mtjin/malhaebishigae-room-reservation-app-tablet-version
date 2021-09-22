package com.mtjin.free_room_tablet.views.reservation_info

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.mtjin.free_room_tablet.R
import com.mtjin.free_room_tablet.base.BaseFragment
import com.mtjin.free_room_tablet.databinding.FragmentReservationInfoBinding
import com.mtjin.free_room_tablet.views.main.MainActivity
import javax.inject.Inject

class ReservationInfoFragment :
    BaseFragment<FragmentReservationInfoBinding>(R.layout.fragment_reservation_info) {
    private lateinit var reservationInAdapter: ReservationInfoAdapter

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: ReservationInfoViewModel by lazy {
        ViewModelProvider(this, viewModelFactory).get(ReservationInfoViewModel::class.java)
    }

    override fun init() {
        (activity as MainActivity).mainComponent.inject(this)
        binding.vm = viewModel
        initAdapter()
        initViewModelCallback()
        requestReservations()
    }

    private fun requestReservations() {
        viewModel.requestReservations()
    }

    private fun initViewModelCallback() {
        with(viewModel) {
            networkState.observe(this@ReservationInfoFragment, Observer {
                showInfoSnackBar(getString(R.string.network_error_msg))
            })

            goReservation.observe(this@ReservationInfoFragment, Observer {
                val directions =
                    ReservationInfoFragmentDirections.actionReservationInfoFragmentToReservationSelectFragment()
                findNavController().navigate(directions)
            })
        }
    }

    private fun initAdapter() {
        reservationInAdapter = ReservationInfoAdapter()
        binding.rvReservations.adapter = reservationInAdapter
    }


}

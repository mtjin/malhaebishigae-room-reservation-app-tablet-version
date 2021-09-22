package com.mtjin.free_room_tablet.views.reservation_select

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.mtjin.free_room_tablet.R
import com.mtjin.free_room_tablet.base.BaseFragment
import com.mtjin.free_room_tablet.databinding.FragmentReservationSelectBinding
import com.mtjin.free_room_tablet.utils.BUSINESS_CODE
import com.mtjin.free_room_tablet.utils.MEETING_ROOM_CODE
import com.mtjin.free_room_tablet.views.main.MainActivity
import com.mtjin.free_room_tablet.views.main.ReservationSharedViewModel
import javax.inject.Inject

class ReservationSelectFragment :
    BaseFragment<FragmentReservationSelectBinding>(R.layout.fragment_reservation_select) {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: ReservationSelectViewModel by lazy {
        ViewModelProvider(this, viewModelFactory).get(ReservationSelectViewModel::class.java)
    }

    override fun init() {
        (activity as MainActivity).mainComponent.inject(this)
        binding.vm = viewModel
        initViewModelCallback()
        initSharedViewModel()
    }

    private fun initViewModelCallback() {
        with(viewModel) {
            goQrCodeReservation.observe(this@ReservationSelectFragment, Observer {
                findNavController().navigate(ReservationSelectFragmentDirections.actionReservationSelectFragmentToQrCodeReservationFragment())
            })

            goAnonymousReservation.observe(this@ReservationSelectFragment, Observer {
                findNavController().navigate(
                    ReservationSelectFragmentDirections.actionReservationSelectFragmentToDateSelectFragment(
                        BUSINESS_CODE + MEETING_ROOM_CODE
                    )
                )
            })

            goLoginReservation.observe(this@ReservationSelectFragment, Observer {
                findNavController().navigate(ReservationSelectFragmentDirections.actionReservationSelectFragmentToLoginReservationFragment())
            })
        }
    }

    private fun initSharedViewModel() {
        val shareViewModel =
            activity?.run { ViewModelProviders.of(this)[ReservationSharedViewModel::class.java] }
                ?: throw Exception(getString(R.string.invaild_activity_exception_text))
        shareViewModel.initViewModel()
    }
}
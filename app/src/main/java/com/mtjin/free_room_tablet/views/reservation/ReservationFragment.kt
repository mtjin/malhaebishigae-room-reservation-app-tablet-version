package com.mtjin.free_room_tablet.views.reservation

import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.mtjin.free_room_tablet.R
import com.mtjin.free_room_tablet.base.BaseFragment
import com.mtjin.free_room_tablet.databinding.FragmentReservationBinding
import com.mtjin.free_room_tablet.views.date_select.ReservationAvailableAdapter
import com.mtjin.free_room_tablet.views.dialog.YesNoDialogFragment
import com.mtjin.free_room_tablet.views.main.MainActivity
import com.mtjin.free_room_tablet.views.main.ReservationSharedViewModel
import timber.log.Timber

class ReservationFragment :
    BaseFragment<FragmentReservationBinding>(R.layout.fragment_reservation) {

    private lateinit var reserveAvailableAdapter: ReservationAvailableAdapter

    private lateinit var viewModel: ReservationSharedViewModel

    override fun init() {
        (activity as MainActivity).mainComponent.inject(this)
        viewModel =
            activity?.run { ViewModelProviders.of(this)[ReservationSharedViewModel::class.java] }
                ?: throw Exception(getString(R.string.invaild_activity_exception_text))
        binding.vm = viewModel
        initAdapter()
        initView()
        initViewModelCallback()
    }

    private fun initViewModelCallback() {
        with(viewModel) {
            isLottieLoading.observe(this@ReservationFragment, Observer { loadingState ->
                when (loadingState) {
                    true -> showProgressDialog()
                    false -> hideProgressDialog()
                }
            })

            successReservation.observe(this@ReservationFragment, Observer { success ->
                if (success) {
                    showPositiveSnackBar(getString(R.string.reservation_success_text))
                    findNavController().navigate(ReservationFragmentDirections.actionReservationFragmentToReservationInfoFragment())
                } else {
                    showNegativeSnackBar(getString(R.string.reservation_fail_text))
                }
            })

            checkReservationDialog.observe(this@ReservationFragment, Observer {
                val dialog = YesNoDialogFragment { yes ->
                    if (yes) viewModel.requestReservation()
                }
                requireActivity().supportFragmentManager.let { fragmentManager ->
                    dialog.show(
                        fragmentManager,
                        ""
                    )
                }
            })

            messageSet.observe(this@ReservationFragment, Observer {
                when (messageSet.value) {
                    ReservationSharedViewModel.MessageSet.NETWORK_ERROR -> showInfoSnackBar(
                        getString(
                            R.string.network_error_msg
                        )
                    )
                    ReservationSharedViewModel.MessageSet.CONTENT_EMPTY -> {
                        binding.tilContent.error = getString(R.string.enter_conference_name_msg)
                        binding.etContent.requestFocus()
                    }
                    ReservationSharedViewModel.MessageSet.REGISTER_EMPTY -> {
                        binding.tilRegister.error = getString(R.string.enter_officer_msg)
                        binding.etRegister.requestFocus()
                    }
                    ReservationSharedViewModel.MessageSet.RESERVATION_ERROR -> {
                        showNegativeSnackBar(getString(R.string.reservation_fail_text))
                    }
                    else -> Timber.d("messageSet 예외사항")
                }
            })

        }
    }

    private fun initView() {
        binding.run {
            etRegister.doOnTextChanged { _, _, _, _ ->
                tilRegister.error = null
            }
            etContent.doOnTextChanged { _, _, _, _ ->
                tilContent.error = null
            }
        }
    }

    private fun initAdapter() {
        reserveAvailableAdapter =
            ReservationAvailableAdapter()
        binding.rvTimes.adapter = reserveAvailableAdapter
    }

}
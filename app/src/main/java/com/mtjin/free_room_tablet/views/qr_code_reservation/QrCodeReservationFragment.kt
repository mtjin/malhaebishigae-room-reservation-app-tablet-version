package com.mtjin.free_room_tablet.views.qr_code_reservation

import android.content.Intent
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.zxing.integration.android.IntentIntegrator
import com.mtjin.free_room_tablet.R
import com.mtjin.free_room_tablet.base.BaseFragment
import com.mtjin.free_room_tablet.databinding.FragmentQrCodeReservationBinding
import com.mtjin.free_room_tablet.views.main.MainActivity
import javax.inject.Inject

class QrCodeReservationFragment :
    BaseFragment<FragmentQrCodeReservationBinding>(R.layout.fragment_qr_code_reservation) {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: QrCodeReservationViewModel by lazy {
        ViewModelProvider(this, viewModelFactory).get(QrCodeReservationViewModel::class.java)
    }
    lateinit var userToken: String

    override fun init() {
        (activity as MainActivity).mainComponent.inject(this)
        viewModelCallback()
        showQrCode()
    }

    private fun viewModelCallback() {
        with(viewModel) {
            networkState.observe(this@QrCodeReservationFragment, Observer {
                showInfoSnackBar(getString(R.string.network_error_msg))
            })

            validateResult.observe(this@QrCodeReservationFragment, Observer {
                if (it) {
                    showPositiveSnackBar(getString(R.string.user_auth_success_text))
                    val directions =
                        QrCodeReservationFragmentDirections.actionQrCodeReservationFragmentToDateSelectFragment(
                            userToken
                        )
                    findNavController().navigate(directions)
                } else {
                    showNegativeSnackBar(getString(R.string.user_not_exist_text))
                    findNavController().popBackStack()
                }
            })
        }
    }

    private fun showQrCode() {
        IntentIntegrator.forSupportFragment(this).initiateScan()
        showToast(getString(R.string.show_user_app_qr_code))
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null) {
            if (result.contents == null) {
                findNavController().popBackStack()
                showToast(getString(R.string.sacn_fail_text))
            } else {
                showToast(result.contents)
                userToken = result.contents
                viewModel.requestQrCodeValidate(userToken)
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }
}
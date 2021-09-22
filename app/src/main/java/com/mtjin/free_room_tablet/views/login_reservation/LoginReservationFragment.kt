package com.mtjin.free_room_tablet.views.login_reservation

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.mtjin.free_room_tablet.R
import com.mtjin.free_room_tablet.base.BaseFragment
import com.mtjin.free_room_tablet.databinding.FragmentLoginReservationBinding
import com.mtjin.free_room_tablet.views.main.MainActivity
import javax.inject.Inject

class LoginReservationFragment :
    BaseFragment<FragmentLoginReservationBinding>(R.layout.fragment_login_reservation) {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: LoginReservationViewModel by lazy {
        ViewModelProvider(this, viewModelFactory).get(LoginReservationViewModel::class.java)
    }

    override fun init() {
        (activity as MainActivity).mainComponent.inject(this)
        binding.vm = viewModel
        initViewModelCallback()
    }

    private fun initViewModelCallback() {
        with(viewModel) {
            networkState.observe(this@LoginReservationFragment, Observer {
                showInfoSnackBar(getString(R.string.network_error_msg))
            })

            loginFail.observe(this@LoginReservationFragment, Observer {
                showNegativeSnackBar(getString(R.string.login_fail_text))
            })

            loginSuccess.observe(this@LoginReservationFragment, Observer { userToken ->
                val directions =
                    LoginReservationFragmentDirections.actionLoginReservationFragmentToDateSelectFragment(
                        userToken
                    )
                findNavController().navigate(directions)
            })
        }
    }
}
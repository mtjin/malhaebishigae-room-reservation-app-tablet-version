package com.mtjin.free_room_tablet.views.top

import androidx.lifecycle.ViewModelProvider
import com.mtjin.free_room_tablet.R
import com.mtjin.free_room_tablet.base.BaseFragment
import com.mtjin.free_room_tablet.databinding.FragmentTopBinding
import com.mtjin.free_room_tablet.views.main.MainActivity
import javax.inject.Inject

class TopFragment : BaseFragment<FragmentTopBinding>(R.layout.fragment_top) {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: TopViewModel by lazy {
        ViewModelProvider(this, viewModelFactory).get(TopViewModel::class.java)
    }

    override fun init() {
        (activity as MainActivity).mainComponent.inject(this)
        binding.vm = viewModel
        initView()
    }

    private fun initView() {
        viewModel.initDateTime()
        viewModel.requestMeetingRoomInfo()
    }
}
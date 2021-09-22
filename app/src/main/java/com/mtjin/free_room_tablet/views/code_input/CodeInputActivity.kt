package com.mtjin.free_room_tablet.views.code_input

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.mtjin.free_room_tablet.R
import com.mtjin.free_room_tablet.base.BaseActivity
import com.mtjin.free_room_tablet.databinding.ActivityCodeInputBinding
import com.mtjin.free_room_tablet.di.MyApplication
import com.mtjin.free_room_tablet.views.main.MainActivity
import javax.inject.Inject

class CodeInputActivity : BaseActivity<ActivityCodeInputBinding>(R.layout.activity_code_input) {
    private lateinit var codeInputComponent: CodeInputComponent

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: CodeInputViewModel by lazy {
        ViewModelProvider(this, viewModelFactory).get(CodeInputViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        codeInputComponent =
            (application as MyApplication).appComponent.codeInputComponent().create()
        codeInputComponent.inject(this)
        super.onCreate(savedInstanceState)
        binding.vm = viewModel
        initViewModelCallback()
        initView()
    }

    private fun initView() {
        viewModel.initEditText()
    }

    private fun initViewModelCallback() {
        with(viewModel) {
            isBusinessCodeEmpty.observe(this@CodeInputActivity, Observer {
                binding.etBusinessCode.error = getString(R.string.enter_business_code_text)
            })

            isMeetingRoomEmpty.observe(this@CodeInputActivity, Observer {
                binding.etRoomCode.error = getString(R.string.enter_meeting_room_code_text)
            })

            loginErrorMsg.observe(this@CodeInputActivity, Observer {
                showNegativeSnackBar(getString(R.string.login_error_text))
            })

            goMain.observe(this@CodeInputActivity, Observer {
                startActivity(Intent(this@CodeInputActivity, MainActivity::class.java))
                finish()
            })
        }
    }
}
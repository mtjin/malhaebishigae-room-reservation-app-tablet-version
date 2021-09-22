package com.mtjin.free_room_tablet.base

import android.graphics.Color
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.mtjin.free_room_tablet.R
import com.pd.chocobar.ChocoBar
import io.reactivex.disposables.CompositeDisposable

abstract class BaseActivity<B : ViewDataBinding>(
    @LayoutRes val layoutId: Int
) : AppCompatActivity() {
    lateinit var binding: B
    private val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, layoutId)
        binding.lifecycleOwner = this
    }

    protected fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }

    protected fun showPositiveSnackBar(msg: String) {
        ChocoBar.builder().setView(findViewById(R.id.content))
            .setText(msg)
            .setBackgroundColor(Color.parseColor("#00B0FF"))
            .centerText()
            .setDuration(ChocoBar.LENGTH_SHORT)
            .good()
            .show()
    }

    protected fun showNegativeSnackBar(msg: String) {
        ChocoBar.builder().setView(findViewById(R.id.content))
            .setText(msg)
            .setBackgroundColor(Color.parseColor("#00B0FF"))
            .centerText()
            .setDuration(ChocoBar.LENGTH_SHORT)
            .good()
            .show()
    }

    protected fun showInfoSnackBar(msg: String) {
        ChocoBar.builder().setView(findViewById(R.id.content))
            .setText(msg)
            .setBackgroundColor(Color.parseColor("#00B0FF"))
            .setTextColor(Color.parseColor("#FFFFFF"))
            .setIcon(R.drawable.ic_info_white_24dp)
            .centerText()
            .setDuration(ChocoBar.LENGTH_SHORT)
            .orange()
            .show()
    }
}


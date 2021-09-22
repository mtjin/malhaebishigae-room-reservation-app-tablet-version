package com.mtjin.free_room_tablet.views.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.mtjin.free_room_tablet.R
import kotlinx.android.synthetic.main.fragment_yes_no_dialog.view.*

class YesNoDialogFragment(private val clickYes: (Boolean) -> Unit) : DialogFragment(),
    View.OnClickListener {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_yes_no_dialog, container, false)
        view.tv_yes.setOnClickListener {
            clickYes(true)
            dismiss()
        }
        view.tv_no.setOnClickListener {
            dismiss()
        }
        return view
    }

    override fun onClick(p0: View?) {
        dismiss()
    }

}
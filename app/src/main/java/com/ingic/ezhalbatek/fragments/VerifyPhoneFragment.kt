package com.ingic.ezhalbatek.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.ingic.ezhalbatek.R
import com.ingic.ezhalbatek.fragments.abstracts.BaseFragment
import com.ingic.ezhalbatek.helpers.UIHelper
import com.ingic.ezhalbatek.ui.views.KotterKnife
import com.ingic.ezhalbatek.ui.views.PinEntryEditText
import com.ingic.ezhalbatek.ui.views.TitleBar
import com.ingic.ezhalbatek.ui.views.bindView

/**
 * Created on 5/21/18.
 */
class VerifyPhoneFragment : BaseFragment() {
    private val txtPinEntry: PinEntryEditText by bindView(R.id.txt_pin_entry)
    private val btnSubmit: Button by bindView(R.id.btn_submit)

    companion object {
        val Tag: String = "VerifyPhoneFragment"
        fun newInstance(): VerifyPhoneFragment {
            val fragment = VerifyPhoneFragment()
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun setTitleBar(titleBar: TitleBar) {
        super.setTitleBar(titleBar)
        titleBar.hideButtons()
        titleBar.showBackButton()
        titleBar.setSubHeading(getResString(R.string.verify_phone_number))
    }
    override fun onDestroyView() {
        super.onDestroyView()
        KotterKnife.reset(this)
    }
    private fun validated(): Boolean {
        if (txtPinEntry.text.toString().trim().equals("")) {
            UIHelper.showShortToastInCenter(dockActivity, getString(R.string.verification_code_error))
            return false
        }else if (txtPinEntry.text.toString().length<4) run {
            UIHelper.showShortToastInCenter(dockActivity, getString(R.string.enter_valid_code_error))

            return false
        } else {
            return true
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnSubmit.setOnClickListener { _ ->
            if (validated()) {
                dockActivity.replaceDockableFragment(UserTypeFragment.newInstance(), UserTypeFragment.Tag)
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_verify_phone, container, false)
    }
}
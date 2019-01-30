package com.ingic.ezhalbatek.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.ingic.ezhalbatek.R
import com.ingic.ezhalbatek.fragments.abstracts.BaseFragment
import com.ingic.ezhalbatek.global.WebServiceConstants
import com.ingic.ezhalbatek.helpers.UIHelper
import com.ingic.ezhalbatek.ui.views.*

/**
 * Created on 5/21/18.
 */
class VerifyPhoneFragment : BaseFragment() {
    private val txtPinEntry: PinEntryEditText by bindView(R.id.txt_pin_entry)
    private val btnSubmit: Button by bindView(R.id.btn_submit)
    private val txtResendCode: AnyTextView by bindView(R.id.txt_resend_code)
    var emailReset = ""

    companion object {
        val Tag: String = "VerifyPhoneFragment"

        fun newInstance(email: String): VerifyPhoneFragment {
            val fragment = VerifyPhoneFragment()
            fragment.emailReset = email
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
        } else if (txtPinEntry.text.toString().length < 4) run {
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
                serviceHelper.enqueueCall(webService.verifyCode(prefHelper.user.id, txtPinEntry.text.toString()), WebServiceConstants.VERIFYCODE)

            }
        }

        txtResendCode.setOnClickListener { _ ->
            serviceHelper.enqueueCall(webService.resendcode(emailReset), WebServiceConstants.RESENDCODE)

        }
    }

    override fun ResponseSuccess(result: Any?, Tag: String?, message: String?) {
        super.ResponseSuccess(result, Tag, message)
        when (Tag) {
            WebServiceConstants.VERIFYCODE -> {
                dockActivity.popBackStackTillEntry(0)
                prefHelper.setLoginStatus(true)
                prefHelper.setGuestStatus(false)
                dockActivity.replaceDockableFragment(UserTypeFragment.newInstance(), UserTypeFragment.Tag)
            }

            WebServiceConstants.RESENDCODE ->{
                UIHelper.showShortToastInCenter(dockActivity,message)
            }

        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_verify_phone, container, false)
    }
}
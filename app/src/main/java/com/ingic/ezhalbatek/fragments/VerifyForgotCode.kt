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

class VerifyForgotCode : BaseFragment() {
    private val txtPinEntry: PinEntryEditText by bindView(R.id.txt_pin_entry)
    private val btnSubmit: Button by bindView(R.id.btn_submit)
    private val txtResendCode: AnyTextView by bindView(R.id.txt_resend_code)
    var emailReset = ""
    var codeReset = ""

    companion object {
        val Tag: String = "VerifyForgotCode"

        fun newInstance(email: String,code: String): VerifyForgotCode {
            val fragment = VerifyForgotCode()
            fragment.emailReset = email
            fragment.codeReset = code
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
        titleBar.setSubHeading(getResString(R.string.verify_forgot_password))
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
              //  serviceHelper.enqueueCall(webService.verifyCode(prefHelper.user.id, txtPinEntry.text.toString()), WebServiceConstants.VERIFYCODE)
                serviceHelper.enqueueCall(webService.verifypasswordcode(emailReset, txtPinEntry.text.toString()), WebServiceConstants.VERIFYCODE)
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
                dockActivity.popBackStackTillEntry(2)
                dockActivity.replaceDockableFragment(ChangeForgotPassword.newInstance(emailReset,codeReset), "ChangeForgotPassword")
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
package com.ingic.ezhalbatek.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.ingic.ezhalbatek.R
import com.ingic.ezhalbatek.entities.ChangePhoneEnt
import com.ingic.ezhalbatek.entities.LoginModule.UserEnt
import com.ingic.ezhalbatek.fragments.abstracts.BaseFragment
import com.ingic.ezhalbatek.global.WebServiceConstants
import com.ingic.ezhalbatek.helpers.UIHelper
import com.ingic.ezhalbatek.ui.views.*

class VerifyPhoneNumberFragment : BaseFragment() {
    private val txtPinEntry: PinEntryEditText by bindView(R.id.txt_pin_entry)
    private val btnSubmit: Button by bindView(R.id.btn_submit)
    private val txtResendCode: AnyTextView by bindView(R.id.txt_resend_code)
    var resetCode = ""
    var countryCode = ""
    var number = ""

    companion object {
        val Tag: String = "VerifyPhoneFragment"
        fun newInstance(code: String, countryCodeKey: String, PhoneNumber: String): VerifyPhoneNumberFragment {
            val fragment = VerifyPhoneNumberFragment()
            fragment.resetCode = code
            fragment.countryCode = countryCodeKey
            fragment.number = PhoneNumber
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

    //    txtPinEntry.setText(resetCode);
        txtResendCode.visibility=View.GONE

        btnSubmit.setOnClickListener { _ ->
            if (validated()) {
                serviceHelper.enqueueCall(webService.verifyPhoneCode(prefHelper.user.id, txtPinEntry.text.toString(), countryCode, number), WebServiceConstants.VERIFYCODE)

            }
        }


    }

    override fun ResponseSuccess(result: Any?, Tag: String?, message: String?) {
        super.ResponseSuccess(result, Tag, message)
        when (Tag) {
            WebServiceConstants.VERIFYCODE -> {
                var changePhoneEnt: ChangePhoneEnt
                changePhoneEnt = (result as ChangePhoneEnt)
                var user = prefHelper.user
                user.countryCode = (changePhoneEnt.countryCode)
                user.phoneNo = (changePhoneEnt.phoneNo)
                prefHelper.putUser(user)
                dockActivity.popBackStackTillEntry(0)
                dockActivity.replaceDockableFragment(HomeFragment.newInstance(), "HomeFragment")
                UIHelper.showShortToastInCenter(dockActivity,"Number changed successfully")

            }


        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_verify_phone, container, false)
    }
}
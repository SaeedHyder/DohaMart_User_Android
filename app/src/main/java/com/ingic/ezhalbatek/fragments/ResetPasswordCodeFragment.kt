package com.ingic.ezhalbatek.fragments

import android.graphics.Typeface
import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.ingic.ezhalbatek.R
import com.ingic.ezhalbatek.fragments.abstracts.BaseFragment
import com.ingic.ezhalbatek.global.WebServiceConstants
import com.ingic.ezhalbatek.global.WebServiceConstants.RESENDCODE
import com.ingic.ezhalbatek.helpers.UIHelper
import com.ingic.ezhalbatek.ui.views.*
import kotlinx.android.synthetic.main.fragment_reset_password_code.*
import java.util.*
import java.util.concurrent.TimeUnit

/**
 * Created on 5/19/18.
 */
class ResetPasswordCodeFragment : BaseFragment() {
    //val txtTimer: AnyTextView? by bindView(R.id.txtTimer)
    val btnSubmit: Button by bindView(R.id.btn_submit)
    val edtCode: AnyEditTextView by bindView(R.id.edtcode)
    val txtResendCode: AnyTextView by bindView(R.id.txt_resend_code)
    lateinit var timer: CountDownTimer
    var emailReset = ""
    var codeReset = ""

    companion object {
        val Tag: String = "ResetPasswordCodeFragment"


        fun newInstance(email: String, code: String): ResetPasswordCodeFragment {
            val fragment = ResetPasswordCodeFragment()
            fragment.emailReset = email
            fragment.codeReset = code
            return fragment
        }
    }

    fun initRestartCounter() {
        timer = object : CountDownTimer(78000, 1000) {
            override fun onFinish() {
                initRestartCounter()
            }

            override fun onTick(millisUntilFinished: Long) {
                val text = String.format(Locale.getDefault(), "%2d:%02d",
                        TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) % 60,
                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) % 60)
                txtTimer?.setText(text)
                txtTimer?.setTypeface(Typeface.DEFAULT_BOLD)
            }
        }.start()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        KotterKnife.reset(this)
    }

    override fun setTitleBar(titleBar: TitleBar) {
        super.setTitleBar(titleBar)
        titleBar.hideButtons()
        titleBar.showBackButton()
        titleBar.setSubHeading(getResString(R.string.forgot_password_title))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRestartCounter()
        btnSubmit.setOnClickListener { _ ->
            if (isValidate()) {
                timer.cancel()
                serviceHelper.enqueueCall(webService.verifypasswordcode(emailReset, codeReset), WebServiceConstants.VERIFYCODE)

            }
        }

        txtResendCode.setOnClickListener(View.OnClickListener {
            serviceHelper.enqueueCall(webService.resendcode(emailReset), WebServiceConstants.RESENDCODE)
        })
    }

    override fun ResponseSuccess(result: Any?, Tag: String?, message: String?) {
        super.ResponseSuccess(result, Tag, message)
        when (Tag) {
            WebServiceConstants.VERIFYCODE -> {
                dockActivity.popBackStackTillEntry(2)
                dockActivity.replaceDockableFragment(ChangeForgotPassword.newInstance(emailReset,codeReset), "ChangeForgotPassword")

            }

            RESENDCODE->{
                UIHelper.showShortToastInCenter(dockActivity,message)
            }
        }
    }

    private fun isValidate(): Boolean {
        if (edtCode.getText().toString().isEmpty()) run {

            edtCode.setError(getString(R.string.enter_code_error))
            if (edtCode.requestFocus()) {
                setEditTextFocus(edtCode)
            }
            return false
        } else if (edtCode.getText().toString().length < 4) run {

            edtCode.setError(getString(R.string.enter_valid_code_error))
            if (edtCode.requestFocus()) {
                setEditTextFocus(edtCode)
            }
            return false
        } else
            return true
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_reset_password_code, container, false)
    }
}
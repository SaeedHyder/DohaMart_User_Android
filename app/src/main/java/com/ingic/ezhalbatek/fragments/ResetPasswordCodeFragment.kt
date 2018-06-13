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
    lateinit var timer: CountDownTimer

    companion object {
        val Tag: String = "ResetPasswordCodeFragment"
        fun newInstance(): ResetPasswordCodeFragment {
            val fragment = ResetPasswordCodeFragment()
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
                dockActivity.popBackStackTillEntry(0)
                dockActivity.replaceDockableFragment(LoginFragment.newInstance(), "LoginFragment")
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
        }else if (edtCode.getText().toString().length<4) run {

            edtCode.setError(getString(R.string.enter_valid_code_error))
            if (edtCode.requestFocus()) {
                setEditTextFocus(edtCode)
            }
            return false
        }  else
            return true
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_reset_password_code, container, false)
    }
}
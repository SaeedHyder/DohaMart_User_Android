package com.ingic.ezhalbatek.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ingic.ezhalbatek.R
import com.ingic.ezhalbatek.fragments.abstracts.BaseFragment
import com.ingic.ezhalbatek.ui.views.KotterKnife
import com.ingic.ezhalbatek.ui.views.TitleBar
import kotlinx.android.synthetic.main.fragment_reset_password.*


/**
 * Created on 5/18/18.
 */
class ResetPasswordFragment : BaseFragment() {

    companion object {
        val Tag: String = "ResetPasswordFragment"
        fun newInstance(): ResetPasswordFragment {
            val fragment = ResetPasswordFragment()
            return fragment
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btn_submit.setOnClickListener() { v ->
            if (isvalidated()) {
                dockActivity.replaceDockableFragment(ResetPasswordCodeFragment.newInstance(), ResetPasswordCodeFragment.Tag)
            }
        }
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
    private fun isvalidated(): Boolean {
        if (edtPhone.getText().toString().isEmpty()) run {

            edtPhone.setError(getString(R.string.enter_phone))
            if (edtPhone.requestFocus()) {
                setEditTextFocus(edtPhone)
            }
            return false
        } else if (edtPhone.getText().toString().length < 10) run {
            edtPhone.setError(getString(R.string.numberLength))
            if (edtPhone.requestFocus()) {
                setEditTextFocus(edtPhone)
            }

            return false
        } else
            return true
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_reset_password, container, false)
    }
}
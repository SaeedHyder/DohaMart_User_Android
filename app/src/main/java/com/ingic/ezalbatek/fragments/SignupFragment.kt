package com.ingic.ezalbatek.fragments

import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.ingic.ezalbatek.R
import com.ingic.ezalbatek.fragments.abstracts.BaseFragment
import com.ingic.ezalbatek.ui.views.*

/**
 * Created on 5/19/18.
 */
class SignupFragment : BaseFragment(), View.OnClickListener {
    private val edtCity: AutoCompleteLocation by bindView(R.id.edtCity)
    private val edtPassword: AnyEditTextView by bindView(R.id.edt_password)
    private val edtZipCode: AnyEditTextView by bindView(R.id.edtZipCode)
    private val edtName: AnyEditTextView by bindView(R.id.edt_name)
    private val edtEmail: AnyEditTextView by bindView(R.id.edt_email)
    private val edtPhone: AnyEditTextView by bindView(R.id.edtPhone)
    private val btnRegister: Button by bindView(R.id.btn_register)
    private val btnFacebookRegister: Button by bindView(R.id.facebook_signup)
    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.btn_register -> {
                if (isvalidated()) {
                    dockActivity.replaceDockableFragment(VerifyPhoneFragment.newInstance(), VerifyPhoneFragment.Tag)
                }
            }
            R.id.facebook_signup -> {
                willbeimplementedinBeta()
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
        titleBar.setSubHeading(getResString(R.string.register))
    }

    private fun isvalidated(): Boolean {
        if (edtName.getText().toString().isEmpty()) run {

            edtName.setError(getString(R.string.enter_name))
            if (edtName.requestFocus()) {
                setEditTextFocus(edtName)
            }
            return false
        } else if (edtEmail.text == null || edtEmail.text.toString().isEmpty() ||
                !Patterns.EMAIL_ADDRESS.matcher(edtEmail.text.toString()).matches()) run {
            edtEmail.error = getString(R.string.enter_valid_email)
            if (edtEmail.requestFocus()) {
                setEditTextFocus(edtEmail)
            }
            return false
        } else if (edtPhone.getText().toString().isEmpty()) run {

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
        } else if (edtPassword.text.toString().isEmpty()) run {
            edtPassword.error = getString(R.string.enter_password)
            if (edtPassword.requestFocus()) {
                setEditTextFocus(edtPassword)
            }
            return false
        } else if (edtPassword.text.toString().length < 6) run {
            edtPassword.error = getString(R.string.passwordLength)
            if (edtPassword.requestFocus()) {
                setEditTextFocus(edtPassword)
            }
            return false
        } else if (edtCity.getText().toString().isEmpty()) run {

            edtCity.setError(getString(R.string.enter_city))
            return false
        } else if (edtZipCode.getText().toString().isEmpty()) run {

            edtZipCode.setError(getString(R.string.enter_zipcode))
            if (edtZipCode.requestFocus()) {
                setEditTextFocus(edtZipCode)
            }
            return false
        } else
            return true

    }


    companion object {
        val Tag: String = "SignupFragment"
        fun newInstance(): SignupFragment {
            val fragment = SignupFragment()
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnRegister.setOnClickListener(this)
        btnFacebookRegister.setOnClickListener(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_signup, container, false)
    }
}
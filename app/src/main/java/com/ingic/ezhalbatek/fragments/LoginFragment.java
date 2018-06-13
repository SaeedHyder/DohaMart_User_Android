package com.ingic.ezhalbatek.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.ingic.ezhalbatek.R;
import com.ingic.ezhalbatek.fragments.abstracts.BaseFragment;
import com.ingic.ezhalbatek.ui.views.AnyEditTextView;
import com.ingic.ezhalbatek.ui.views.AnyTextView;
import com.ingic.ezhalbatek.ui.views.TitleBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


public class LoginFragment extends BaseFragment {

    @BindView(R.id.edt_email)
    AnyEditTextView edtEmail;
    @BindView(R.id.edt_password)
    AnyEditTextView edtPassword;
    @BindView(R.id.btn_forgot_password)
    AnyTextView btnForgotPassword;
    @BindView(R.id.loginButton)
    Button loginButton;
    @BindView(R.id.btn_register)
    AnyTextView btnRegister;
    @BindView(R.id.facebook_login)
    Button facebookLogin;
    @BindView(R.id.guest_login)
    Button guestLogin;
    Unbinder unbinder;

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
        //titleBar.showBackButton();
        if (edtEmail!=null&&edtPassword!=null){
            edtEmail.setText("");
            edtPassword.setText("");
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onViewCreated(view, savedInstanceState);
        getMainActivity().changeBackgroundResources(R.drawable.bg);

    }
    @OnClick({R.id.btn_forgot_password, R.id.loginButton, R.id.btn_register, R.id.facebook_login, R.id.guest_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_forgot_password:
                getDockActivity().replaceDockableFragment(ResetPasswordFragment.Companion.newInstance(), ResetPasswordFragment.Companion.getTag());
                break;
            case R.id.loginButton:
                if (isvalidated()) {
                    prefHelper.setLoginStatus(true);
                    getDockActivity().popBackStackTillEntry(0);
                    getDockActivity().replaceDockableFragment(HomeFragment.newInstance(), "HomeFragmnet");
                }
                break;
            case R.id.btn_register:
                getDockActivity().replaceDockableFragment(SignupFragment.Companion.newInstance(), SignupFragment.Companion.getTag());
                break;
            case R.id.facebook_login:
                willbeimplementedinBeta();
                break;
            case R.id.guest_login:
                willbeimplementedinBeta();
                break;
        }
    }

    private boolean isvalidated() {
        if (edtEmail.getText() == null || (edtEmail.getText().toString().isEmpty()) ||
                !(Patterns.EMAIL_ADDRESS.matcher(edtEmail.getText().toString()).matches())) {
            edtEmail.setError(getString(R.string.enter_valid_email));
            return false;
        } else if (edtPassword.getText().toString().isEmpty()) {
            edtPassword.setError(getString(R.string.enter_password));
            return false;
        } else if (edtPassword.getText().toString().length() < 6) {
            edtPassword.setError(getString(R.string.passwordLength));
            return false;
        } else {
            return true;
        }
    }
}

package com.ingic.ezhalbatek.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.facebook.CallbackManager;
import com.facebook.login.LoginManager;
import com.google.firebase.iid.FirebaseInstanceId;
import com.ingic.ezhalbatek.R;
import com.ingic.ezhalbatek.entities.LoginModule.UserEnt;
import com.ingic.ezhalbatek.fragments.abstracts.BaseFragment;
import com.ingic.ezhalbatek.global.AppConstants;
import com.ingic.ezhalbatek.helpers.FacebookLoginHelper;
import com.ingic.ezhalbatek.helpers.UIHelper;
import com.ingic.ezhalbatek.ui.views.AnyEditTextView;
import com.ingic.ezhalbatek.ui.views.AnyTextView;
import com.ingic.ezhalbatek.ui.views.TitleBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.ingic.ezhalbatek.global.WebServiceConstants.FACEBOOKLOGIN;
import static com.ingic.ezhalbatek.global.WebServiceConstants.LOGIN;


public class LoginFragment extends BaseFragment implements FacebookLoginHelper.FacebookLoginListener {

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

    private CallbackManager callbackManager;
    private FacebookLoginHelper facebookLoginHelper;
    private FacebookLoginHelper.FacebookLoginEnt facebookLoginEnt;

    private static boolean showBackBtn = true;

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    public static LoginFragment newInstance(boolean showBtn) {
        showBackBtn = showBtn;
        return new LoginFragment();
    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
        /*if (showBackBtn) {
            titleBar.showBackButton();
        }*/

        if (edtEmail != null && edtPassword != null) {
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

        if (prefHelper.isLanguageArabian()) {
            view.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);

        } else {
            view.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
        }

        getMainActivity().changeBackgroundResources(R.drawable.bg);

        setupFacebookLogin();
    }

    private void setupFacebookLogin() {
        callbackManager = CallbackManager.Factory.create();
        // btnfbLogin.setFragment(this);
        facebookLoginHelper = new FacebookLoginHelper(getDockActivity(), this, this);
        LoginManager.getInstance().registerCallback(callbackManager, facebookLoginHelper);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @OnClick({R.id.btn_forgot_password, R.id.loginButton, R.id.btn_register, R.id.facebook_login, R.id.guest_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_forgot_password:
                getDockActivity().replaceDockableFragment(ResetPasswordFragment.newInstance(), "ResetPasswordFragment");
                break;
            case R.id.loginButton:
                if (isValidated()) {
                    serviceHelper.enqueueCall(webService.login(edtEmail.getText().toString(), edtPassword.getText().toString(), AppConstants.UserRoleId, AppConstants.Device_Type, FirebaseInstanceId.getInstance().getToken()), LOGIN);
                }
                break;
            case R.id.btn_register:
                getDockActivity().replaceDockableFragment(SignupFragment.newInstance(), "SignupFragment");
                break;
            case R.id.facebook_login:
                LoginManager.getInstance().logInWithReadPermissions(LoginFragment.this, facebookLoginHelper.getPermissionNeeds());
                break;
            case R.id.guest_login:
                prefHelper.putUser(null);
                prefHelper.setLoginStatus(false);
                prefHelper.setGuestStatus(true);
                getDockActivity().replaceDockableFragment(HomeFragment.newInstance(), "HomeFragment");
                break;
        }
    }

    @Override
    public void ResponseSuccess(Object result, String Tag, String message) {
        super.ResponseSuccess(result, Tag, message);
        switch (Tag) {
            case LOGIN:
                UserEnt userEnt = (UserEnt) result;
                prefHelper.putUser(userEnt);
                prefHelper.setFirebase_TOKEN(FirebaseInstanceId.getInstance().getToken());

                if (userEnt.getIsVerified() == 1) {
                    prefHelper.setLoginStatus(true);
                    prefHelper.setGuestStatus(false);
                    getDockActivity().popBackStackTillEntry(0);
                    getDockActivity().replaceDockableFragment(HomeFragment.newInstance(), "HomeFragment");
                } else {
                    prefHelper.setLoginStatus(false);
                    getDockActivity().popBackStackTillEntry(2);
                    getDockActivity().replaceDockableFragment(VerifyPhoneFragment.Companion.newInstance(userEnt.getEmail()), "VerifyPhoneFragment");

                }

                break;

            case FACEBOOKLOGIN:
                UserEnt userEntity = (UserEnt) result;
                prefHelper.putUser(userEntity);
                prefHelper.setFirebase_TOKEN(FirebaseInstanceId.getInstance().getToken());

                if (userEntity.getIsVerified() == 1) {
                    prefHelper.setLoginStatus(true);
                    prefHelper.setGuestStatus(false);
                    getDockActivity().popBackStackTillEntry(0);
                    getDockActivity().replaceDockableFragment(HomeFragment.newInstance(), "HomeFragment");
                } else {
                    prefHelper.setLoginStatus(false);
                    getDockActivity().popBackStackTillEntry(2);
                    getDockActivity().replaceDockableFragment(VerifyPhoneFragment.Companion.newInstance(userEntity.getEmail()), "VerifyPhoneFragment");

                }

                break;


        }
    }

    @Override
    public void ResponseFailureNoResonse(String tag) {
        super.ResponseFailureNoResonse(tag);
        switch (tag) {
            case FACEBOOKLOGIN:
                UIHelper.showShortToastInCenter(getDockActivity(), "Please register first on facebook");
                getDockActivity().replaceDockableFragment(FacebookSignup.newInstance(), "FacebookSignup");
                break;
        }
    }

    private boolean isValidated() {
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

    @Override
    public void onSuccessfulFacebookLogin(FacebookLoginHelper.FacebookLoginEnt LoginEnt) {

        serviceHelper.enqueueCall(webService.facebookLogin(LoginEnt.getFacebookUID(), AppConstants.UserRoleId, AppConstants.Device_Type, FirebaseInstanceId.getInstance().getToken()), FACEBOOKLOGIN);


    }
}

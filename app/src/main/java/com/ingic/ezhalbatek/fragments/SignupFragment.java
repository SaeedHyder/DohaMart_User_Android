package com.ingic.ezhalbatek.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.facebook.CallbackManager;
import com.facebook.login.LoginManager;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import com.hbb20.CountryCodePicker;
import com.ingic.ezhalbatek.R;
import com.ingic.ezhalbatek.entities.LoginModule.UserEnt;
import com.ingic.ezhalbatek.fragments.abstracts.BaseFragment;
import com.ingic.ezhalbatek.global.AppConstants;
import com.ingic.ezhalbatek.global.WebServiceConstants;
import com.ingic.ezhalbatek.helpers.FacebookLoginHelper;
import com.ingic.ezhalbatek.helpers.InternetHelper;
import com.ingic.ezhalbatek.ui.views.AnyEditTextView;
import com.ingic.ezhalbatek.ui.views.TitleBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.ingic.ezhalbatek.global.WebServiceConstants.SIGNUP;


public class SignupFragment extends BaseFragment implements FacebookLoginHelper.FacebookLoginListener {
    @BindView(R.id.edt_name)
    AnyEditTextView edtName;
    @BindView(R.id.edt_email)
    AnyEditTextView edtEmail;
    @BindView(R.id.edt_password)
    AnyEditTextView edtPassword;
    @BindView(R.id.edt_confirm_password)
    AnyEditTextView edtConfirmPassword;
    @BindView(R.id.edtPhone)
    AnyEditTextView edtPhone;
    @BindView(R.id.btn_register)
    Button btnRegister;
    @BindView(R.id.facebook_signup)
    Button facebookSignup;
    Unbinder unbinder;
    @BindView(R.id.Countrypicker)
    CountryCodePicker Countrypicker;

    PhoneNumberUtil phoneUtil;

    private CallbackManager callbackManager;
    private FacebookLoginHelper facebookLoginHelper;
    private FacebookLoginHelper.FacebookLoginEnt facebookLoginEnt;
    private String mSocialMediaPlatform = "";
    private String mSocialMediaID = "";


    public static SignupFragment newInstance() {
        Bundle args = new Bundle();

        SignupFragment fragment = new SignupFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_signup, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Countrypicker.registerCarrierNumberEditText(edtPhone);
        phoneUtil = PhoneNumberUtil.getInstance();
        setupFacebookLogin();
    }

    private void setupFacebookLogin() {
        callbackManager = CallbackManager.Factory.create();
        facebookLoginHelper = new FacebookLoginHelper(getDockActivity(), this, this);
        LoginManager.getInstance().registerCallback(callbackManager, facebookLoginHelper);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private boolean isvalidated() {
        if (edtName.getText().toString().isEmpty() || edtName.getText().toString().length() < 3) {
            edtName.setError(getString(R.string.enter_name));
            if (edtName.requestFocus()) {
                setEditTextFocus(edtName);
            }
            return false;
        } else if (edtEmail.getText() == null || edtEmail.getText().toString().isEmpty() ||
                !Patterns.EMAIL_ADDRESS.matcher(edtEmail.getText().toString()).matches()) {
            edtEmail.setError(getString(R.string.enter_valid_email));
            if (edtEmail.requestFocus()) {
                setEditTextFocus(edtEmail);
            }
            return false;
        } else if (edtPassword.getText().toString().isEmpty()) {
            edtPassword.setError(getString(R.string.enter_password));
            if (edtPassword.requestFocus()) {
                setEditTextFocus(edtPassword);
            }
            return false;
        } else if (edtPassword.getText().toString().length() < 6) {
            edtPassword.setError(getString(R.string.passwordLength));
            if (edtPassword.requestFocus()) {
                setEditTextFocus(edtPassword);
            }
            return false;
        } else if (edtConfirmPassword.getText().toString().isEmpty()) {
            edtConfirmPassword.setError(getString(R.string.enter_password));
            if (edtConfirmPassword.requestFocus()) {
                setEditTextFocus(edtConfirmPassword);
            }
            return false;
        } else if (!edtConfirmPassword.getText().toString().equals(edtPassword.getText().toString())) {
            edtConfirmPassword.setError(getString(R.string.confirm_password_error));
            if (edtConfirmPassword.requestFocus()) {
                setEditTextFocus(edtConfirmPassword);
            }
            return false;
        } else if (edtPhone.getText().toString().isEmpty()) {

            edtPhone.setError(getString(R.string.enter_phone));
            if (edtPhone.requestFocus()) {
                setEditTextFocus(edtPhone);
            }
            return false;
        }  else
            return true;

    }


    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
        titleBar.showBackButton();
        titleBar.setSubHeading(getResString(R.string.register));
    }

    @OnClick({R.id.btn_register, R.id.facebook_signup})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_register:
                if (isvalidated()) {
                    if (isPhoneNumberValid()) {
                        //registerUser(Countrypicker.getFullNumberWithPlus());
                        serviceHelper.enqueueCall(webService.signup(edtName.getText().toString(), edtEmail.getText().toString(), edtPassword.getText().toString(), "+"+Countrypicker.getSelectedCountryCode(),
                                edtPhone.getText().toString().trim(), "", "", AppConstants.Device_Type, FirebaseInstanceId.getInstance().getToken(),
                                "en", mSocialMediaID, "", ""), SIGNUP);
                    }

                }
                break;
            case R.id.facebook_signup:
              //  LoginManager.getInstance().logInWithReadPermissions(SignupFragment.this, facebookLoginHelper.getPermissionNeeds());
                getDockActivity().replaceDockableFragment(FacebookSignup.newInstance(), "FacebookSignup");
                break;
        }

    }

    @Override
    public void ResponseSuccess(Object result, String Tag, String message) {
        super.ResponseSuccess(result, Tag, message);
        switch (Tag){
            case SIGNUP:
                UserEnt userEnt = (UserEnt) result;
                prefHelper.putUser(userEnt);
                prefHelper.setFirebase_TOKEN(FirebaseInstanceId.getInstance().getToken());
                getDockActivity().popBackStackTillEntry(2);
                getDockActivity().replaceDockableFragment(VerifyPhoneFragment.Companion.newInstance(userEnt.getEmail()),"VerifyPhoneFragment");
                break;
        }
    }

    @Override
    public void onSuccessfulFacebookLogin(FacebookLoginHelper.FacebookLoginEnt LoginEnt) {

        facebookLoginEnt = LoginEnt;
        edtEmail.setText(LoginEnt.getFacebookEmail() + "");
        edtName.setText(LoginEnt.getFacebookFullName() + "");
        mSocialMediaPlatform = AppConstants.SOCIAL_MEDIA_TYPE_FACEBOOK;
        mSocialMediaID = LoginEnt.getFacebookUID();
    }


    private boolean isPhoneNumberValid() {


        try {
            Phonenumber.PhoneNumber number = phoneUtil.parse(edtPhone.getText().toString(), Countrypicker.getSelectedCountryNameCode());
            if (phoneUtil.isValidNumber(number)) {
                return true;
            } else {
                edtPhone.setError(getDockActivity().getResources().getString(R.string.enter_valid_number_error));
                return false;
            }
        } catch (NumberParseException e) {
            System.err.println("NumberParseException was thrown: " + e.toString());
            edtPhone.setError(getDockActivity().getResources().getString(R.string.enter_valid_number_error));
            return false;

        }

    }


}

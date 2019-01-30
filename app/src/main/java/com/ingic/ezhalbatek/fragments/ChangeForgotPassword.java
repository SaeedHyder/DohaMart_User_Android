package com.ingic.ezhalbatek.fragments;

import android.os.Bundle;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.ingic.ezhalbatek.R;
import com.ingic.ezhalbatek.fragments.abstracts.BaseFragment;
import com.ingic.ezhalbatek.global.WebServiceConstants;
import com.ingic.ezhalbatek.helpers.UIHelper;
import com.ingic.ezhalbatek.ui.views.AnyEditTextView;
import com.ingic.ezhalbatek.ui.views.TitleBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.ingic.ezhalbatek.global.WebServiceConstants.UPDATEPASSWORD;

public class ChangeForgotPassword extends BaseFragment {
    @BindView(R.id.edtNewPassword)
    AnyEditTextView edtNewPassword;
    @BindView(R.id.edtConfirmPassword)
    AnyEditTextView edtConfirmPassword;
    @BindView(R.id.btnUpdate)
    Button btnUpdate;
    Unbinder unbinder;

    private static String Email="";
    private static String Code="";
    private long mLastClickTime = 0;

    public static ChangeForgotPassword newInstance(String emailReset, String codeReset) {
        Bundle args = new Bundle();
        Email=emailReset;
        Code=codeReset;
        ChangeForgotPassword fragment = new ChangeForgotPassword();
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
        View view = inflater.inflate(R.layout.change_forgot_pasword, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
        titleBar.showBackButton();
        titleBar.setSubHeading(getResString(R.string.forgot_password_title));
    }


    private boolean isValidated() {
        if (edtNewPassword.getText().toString().isEmpty()) {
            edtNewPassword.setError(getString(R.string.enter_password));
            if (edtNewPassword.requestFocus()) {
                setEditTextFocus(edtNewPassword);
            }
            return false;
        } else if (edtNewPassword.getText().toString().length() < 6) {
            edtNewPassword.setError(getString(R.string.passwordLength));
            if (edtNewPassword.requestFocus()) {
                setEditTextFocus(edtNewPassword);
            }
            return false;
        } else if (!edtConfirmPassword.getText().toString().equals(edtNewPassword.getText().toString())) {
            edtConfirmPassword.setError(getString(R.string.confirm_password_error));
            if (edtConfirmPassword.requestFocus()) {
                setEditTextFocus(edtConfirmPassword);
            }
            return false;
        } else {
            return true;
        }
    }


    @OnClick(R.id.btnUpdate)
    public void onViewClicked() {
        if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
            return;
        }
        mLastClickTime = SystemClock.elapsedRealtime();

        if(isValidated()){
            serviceHelper.enqueueCall(webService.updatePassword(Code,Email,edtNewPassword.getText().toString()), UPDATEPASSWORD);

        }
    }

    @Override
    public void ResponseSuccess(Object result, String Tag, String message) {
        super.ResponseSuccess(result, Tag, message);
        switch (Tag){
            case UPDATEPASSWORD:
                UIHelper.showShortToastInCenter(getDockActivity(),"Password updated successfully");
                getDockActivity().popBackStackTillEntry(0);
                getDockActivity().replaceDockableFragment(LoginFragment.newInstance(false),"LoginFragment");
                break;
        }
    }
}

package com.ingic.ezhalbatek.fragments;

import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.ingic.ezhalbatek.R;
import com.ingic.ezhalbatek.entities.ForgotPassEnt;
import com.ingic.ezhalbatek.fragments.abstracts.BaseFragment;
import com.ingic.ezhalbatek.helpers.UIHelper;
import com.ingic.ezhalbatek.ui.views.AnyEditTextView;
import com.ingic.ezhalbatek.ui.views.AnyTextView;
import com.ingic.ezhalbatek.ui.views.TitleBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.ingic.ezhalbatek.global.WebServiceConstants.FORGOTPASSWORD;

public class ResetPasswordFragment extends BaseFragment {


    @BindView(R.id.img_splash)
    ImageView imgSplash;
    @BindView(R.id.edt_email)
    AnyEditTextView edtEmail;
    @BindView(R.id.btn_submit)
    Button btnSubmit;
    @BindView(R.id.txtTimer)
    AnyTextView txtTimer;
    Unbinder unbinder;

    public static ResetPasswordFragment newInstance() {
        Bundle args = new Bundle();

        ResetPasswordFragment fragment = new ResetPasswordFragment();
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
        View view = inflater.inflate(R.layout.fragment_reset_password, container, false);

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

    private boolean isvalidated() {
        if (edtEmail.getText() == null || (edtEmail.getText().toString().isEmpty()) ||
                !(Patterns.EMAIL_ADDRESS.matcher(edtEmail.getText().toString()).matches())) {
            edtEmail.setError(getString(R.string.enter_valid_email));
            return false;
        } else
            return true;
    }

    @OnClick(R.id.btn_submit)
    public void onViewClicked() {
        if (isvalidated()) {
            serviceHelper.enqueueCall(webService.forgotPassword(edtEmail.getText().toString()), FORGOTPASSWORD);
        }
    }

    @Override
    public void ResponseSuccess(Object result, String Tag, String message) {
        super.ResponseSuccess(result, Tag, message);
        switch (Tag){
            case FORGOTPASSWORD:
                ForgotPassEnt ent=(ForgotPassEnt)result;
                getDockActivity().popBackStackTillEntry(2);
                UIHelper.showShortToastInCenter(getDockActivity(),message);
                getDockActivity().replaceDockableFragment(VerifyForgotCode.Companion.newInstance(edtEmail.getText().toString(),ent.getResetCode()), "VerifyForgotCode");
                break;
        }
    }
}

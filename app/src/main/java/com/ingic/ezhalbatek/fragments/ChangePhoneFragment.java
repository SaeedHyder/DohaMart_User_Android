package com.ingic.ezhalbatek.fragments;

import android.os.Bundle;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import com.hbb20.CountryCodePicker;
import com.ingic.ezhalbatek.R;
import com.ingic.ezhalbatek.entities.LoginModule.UserEnt;
import com.ingic.ezhalbatek.fragments.abstracts.BaseFragment;
import com.ingic.ezhalbatek.global.WebServiceConstants;
import com.ingic.ezhalbatek.ui.views.AnyEditTextView;
import com.ingic.ezhalbatek.ui.views.TitleBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.ingic.ezhalbatek.global.WebServiceConstants.CHANGENUMBER;

/**
 * Created on 6/6/18.
 */
public class ChangePhoneFragment extends BaseFragment {
    public static final String TAG = "ChangePhoneFragment";

    @BindView(R.id.btnUpdate)
    Button btnUpdate;
    Unbinder unbinder;
    @BindView(R.id.Countrypicker)
    CountryCodePicker Countrypicker;
    @BindView(R.id.edtPhone)
    AnyEditTextView edtPhone;
    PhoneNumberUtil phoneUtil;

    private long mLastClickTime = 0;

    public static ChangePhoneFragment newInstance() {
        Bundle args = new Bundle();

        ChangePhoneFragment fragment = new ChangePhoneFragment();
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
        View view = inflater.inflate(R.layout.fragment_change_phone, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Countrypicker.registerCarrierNumberEditText(edtPhone);
        phoneUtil = PhoneNumberUtil.getInstance();
    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
        titleBar.showBackButton();
        titleBar.setSubHeading(getResString(R.string.change_number));
    }


    private boolean isvalidated() {

        if (edtPhone.getText().toString().equals("") && edtPhone.getText().toString().isEmpty()) {
            edtPhone.setError(getString(R.string.enter_phone));
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

        if (isvalidated()) {
            if (isPhoneNumberValid()) {
                serviceHelper.enqueueCall(webService.changePhone(prefHelper.getUser().getId() + "", "+" + Countrypicker.getSelectedCountryCode(), edtPhone.getText().toString().trim()), CHANGENUMBER);
            }
        }
    }

    @Override
    public void ResponseSuccess(Object result, String Tag, String message) {
        super.ResponseSuccess(result, Tag, message);
        switch (Tag) {
            case CHANGENUMBER:
                UserEnt userEnt = (UserEnt) result;
                getDockActivity().popFragment();
                getDockActivity().replaceDockableFragment(VerifyPhoneNumberFragment.Companion.newInstance(userEnt.getVerificationCode(), "+" + Countrypicker.getSelectedCountryCode(), edtPhone.getText().toString().trim()), "VerifyPhoneFragment");
                break;
        }
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
package com.ingic.ezhalbatek.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.ingic.ezhalbatek.R;
import com.ingic.ezhalbatek.fragments.abstracts.BaseFragment;
import com.ingic.ezhalbatek.ui.views.AnyEditTextView;
import com.ingic.ezhalbatek.ui.views.AutoCompleteLocation;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created on 6/5/18.
 */
public class EditProfileFragment extends BaseFragment {
    public static final String TAG = "EditProfileFragment";
    @BindView(R.id.btnProfielImage)
    ImageView btnProfielImage;
    @BindView(R.id.edt_name)
    AnyEditTextView edtName;
    @BindView(R.id.edt_email)
    AnyEditTextView edtEmail;
    @BindView(R.id.edtPhone)
    AnyEditTextView edtPhone;
    @BindView(R.id.edtCity)
    AutoCompleteLocation edtCity;
    @BindView(R.id.edtZipCode)
    AnyEditTextView edtZipCode;
    @BindView(R.id.btn_register)
    Button btnRegister;
    Unbinder unbinder;

    public static EditProfileFragment newInstance() {
        Bundle args = new Bundle();

        EditProfileFragment fragment = new EditProfileFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }

    }
    private boolean isvalidated() {

        if (edtName.getText().toString().isEmpty()) {
            edtName.setError(getString(R.string.enter_name));
            return false;
        } else if (edtEmail.getText() == null || (edtEmail.getText().toString().isEmpty()) ||
                (!Patterns.EMAIL_ADDRESS.matcher(edtEmail.getText().toString()).matches())) {
            edtEmail.setError(getString(R.string.enter_valid_email));
            return false;
        } else if (edtPhone.getText().toString().equals("") && edtPhone.getText().toString().isEmpty()) {
            edtPhone.setError(getString(R.string.enter_phone));
            return false;
        } else if (edtPhone.getText().toString().length() < 9 || edtPhone.getText().toString().length() > 10) {
            edtPhone.setError(getString(R.string.numberLength));
            return false;
        }  else if (edtCity.getText().toString().isEmpty()) {
            edtCity.setError(getString(R.string.enter_city));
            return false;
        } else if (edtZipCode.getText().toString().isEmpty()) {
            edtZipCode.setError(getString(R.string.enter_zipcode));
            return false;
        }  else {
            return true;
        }

    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_profile, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.btn_register)
    public void onViewClicked() {
        if (isvalidated()){
            getDockActivity().popBackStackTillEntry(0);
            getDockActivity().replaceDockableFragment(HomeFragment.newInstance(),HomeFragment.TAG);
        }
    }
}
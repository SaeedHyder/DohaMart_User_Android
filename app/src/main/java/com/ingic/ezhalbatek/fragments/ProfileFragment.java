package com.ingic.ezhalbatek.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.ingic.ezhalbatek.R;
import com.ingic.ezhalbatek.fragments.abstracts.BaseFragment;
import com.ingic.ezhalbatek.ui.views.AnyTextView;
import com.ingic.ezhalbatek.ui.views.TitleBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class ProfileFragment extends BaseFragment {
    @BindView(R.id.edt_name)
    AnyTextView edtName;
    @BindView(R.id.edt_email)
    AnyTextView edtEmail;
    @BindView(R.id.edt_address)
    AnyTextView edtAddress;
    @BindView(R.id.edt_package)
    AnyTextView edtPackage;
    @BindView(R.id.edt_phone)
    AnyTextView edtPhone;
    @BindView(R.id.btnEdit)
    Button btnEdit;
    Unbinder unbinder;

    public static ProfileFragment newInstance() {
        Bundle args = new Bundle();

        ProfileFragment fragment = new ProfileFragment();
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
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setData();

    }

    private void setData() {

        if (prefHelper.getUser() != null) {
            edtName.setText(prefHelper.getUser().getFullName() != null ? prefHelper.getUser().getFullName() : "");
            edtEmail.setText(prefHelper.getUser().getEmail() != null ? prefHelper.getUser().getEmail() : "");
            edtAddress.setText(prefHelper.getUser().getLocation() != null ? prefHelper.getUser().getLocation() : "");
            edtPhone.setText(prefHelper.getUser().getPhoneNo() != null ? prefHelper.getUser().getCountryCode()+" "+prefHelper.getUser().getPhoneNo() : "");
            if (prefHelper.getUser().getUserSubscription() != null && prefHelper.getUser().getUserSubscription().getSubscription() != null
                    && prefHelper.getUser().getUserSubscription().getSubscription().getTitle() != null) {
                edtPackage.setVisibility(View.VISIBLE);
                edtPackage.setText(prefHelper.getUser().getUserSubscription().getSubscription().getTitle() + "");
            } else {
                edtPackage.setVisibility(View.GONE);
            }

        }
    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
        titleBar.showBackButton();
        titleBar.setSubHeading(getResString(R.string.view_profile));
    }


    @OnClick(R.id.btnEdit)
    public void onViewClicked() {
        getDockActivity().replaceDockableFragment(EditProfileFragment.newInstance(),"EditProfileFragment");
    }
}

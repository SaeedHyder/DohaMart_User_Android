package com.ingic.ezhalbatek.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.ingic.ezhalbatek.R;
import com.ingic.ezhalbatek.fragments.abstracts.BaseFragment;
import com.ingic.ezhalbatek.ui.views.AnyEditTextView;
import com.ingic.ezhalbatek.ui.views.TitleBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created on 6/6/18.
 */
public class ChangePasswordFragment extends BaseFragment {
    public static final String TAG = "ChangePasswordFragment";
    @BindView(R.id.edtOldPassword)
    AnyEditTextView edtOldPassword;
    @BindView(R.id.edtNewPassword)
    AnyEditTextView edtNewPassword;
    @BindView(R.id.edtConfirmPassword)
    AnyEditTextView edtConfirmPassword;
    @BindView(R.id.btnUpdate)
    Button btnUpdate;
    Unbinder unbinder;

    public static ChangePasswordFragment newInstance() {
        Bundle args = new Bundle();

        ChangePasswordFragment fragment = new ChangePasswordFragment();
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
        View view = inflater.inflate(R.layout.fragment_change_password, container, false);
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
        titleBar.setSubHeading(getResString(R.string.change_password));
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private boolean isValidated() {
        if (edtOldPassword.getText().toString().isEmpty()) {
            edtOldPassword.setError(getString(R.string.enter_password));
            if (edtOldPassword.requestFocus()) {
                setEditTextFocus(edtOldPassword);
            }
            return false;
        } else if (edtNewPassword.getText().toString().isEmpty()) {
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
        if (isValidated()) {
            getDockActivity().popBackStackTillEntry(0);
            getDockActivity().replaceDockableFragment(HomeFragment.newInstance(), HomeFragment.TAG);
        }
    }
}
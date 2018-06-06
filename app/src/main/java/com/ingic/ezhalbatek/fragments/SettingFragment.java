package com.ingic.ezhalbatek.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.ingic.ezhalbatek.R;
import com.ingic.ezhalbatek.fragments.abstracts.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created on 6/6/18.
 */
public class SettingFragment extends BaseFragment {
    public static final String TAG = "SettingFragment";
    @BindView(R.id.swtNotification)
    Switch swtNotification;
    Unbinder unbinder;

    public static SettingFragment newInstance() {
        Bundle args = new Bundle();

        SettingFragment fragment = new SettingFragment();
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
        View view = inflater.inflate(R.layout.fragment_setting_fragment, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        swtNotification.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                willbeimplementedinBeta();
            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.btnChangePassword, R.id.btnRateApp, R.id.btnChangePhone, R.id.btnChangeLanguage, R.id.btnAboutApp, R.id.btnContactUs, R.id.btnCallUs})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnChangePassword:
                getDockActivity().replaceDockableFragment(ChangePasswordFragment.newInstance(), ChangePasswordFragment.TAG);
                break;
            case R.id.btnRateApp:
                willbeimplementedinBeta();

                break;
            case R.id.btnChangePhone:
                getDockActivity().replaceDockableFragment(ChangePhoneFragment.newInstance(), ChangePhoneFragment.TAG);

                break;
            case R.id.btnChangeLanguage:
                getDockActivity().replaceDockableFragment(ChangeLanguageFragment.newInstance(), ChangeLanguageFragment.TAG);

                break;
            case R.id.btnAboutApp:
                getDockActivity().replaceDockableFragment(AboutAppFragment.newInstance(), AboutAppFragment.TAG);

                break;
            case R.id.btnContactUs:
                getDockActivity().replaceDockableFragment(ContactUsFragment.newInstance(), ContactUsFragment.TAG);

                break;
            case R.id.btnCallUs:
                willbeimplementedinBeta();

                break;
        }
    }
}
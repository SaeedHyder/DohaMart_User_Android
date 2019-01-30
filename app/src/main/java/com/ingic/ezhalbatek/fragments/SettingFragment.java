package com.ingic.ezhalbatek.fragments;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;

import com.ingic.ezhalbatek.R;
import com.ingic.ezhalbatek.entities.AdminEnt;
import com.ingic.ezhalbatek.entities.LoginModule.UserEnt;
import com.ingic.ezhalbatek.fragments.abstracts.BaseFragment;
import com.ingic.ezhalbatek.global.AppConstants;
import com.ingic.ezhalbatek.helpers.DialogHelper;
import com.ingic.ezhalbatek.helpers.UIHelper;
import com.ingic.ezhalbatek.ui.views.AnyTextView;
import com.ingic.ezhalbatek.ui.views.TitleBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.ingic.ezhalbatek.global.WebServiceConstants.ADMIN;
import static com.ingic.ezhalbatek.global.WebServiceConstants.toggleNotification;

/**
 * Created on 6/6/18.
 */
public class SettingFragment extends BaseFragment {
    public static final String TAG = "SettingFragment";
    @BindView(R.id.swtNotification)
    Switch swtNotification;
    Unbinder unbinder;

    private static String adminNumber;
    @BindView(R.id.btn_english)
    AnyTextView btnEnglish;
    @BindView(R.id.btn_Arabic)
    AnyTextView btnArabic;
    @BindView(R.id.btnChangeLanguage)
    LinearLayout btnChangeLanguage;
    @BindView(R.id.btnChangePassword)
    LinearLayout btnChangePassword;
    @BindView(R.id.changePassView)
    View changePassView;
    @BindView(R.id.btnChangePhone)
    LinearLayout btnChangePhone;
    @BindView(R.id.btnRateApp)
    LinearLayout btnRateApp;
    @BindView(R.id.btnAboutApp)
    LinearLayout btnAboutApp;
    @BindView(R.id.btnContactUs)
    LinearLayout btnContactUs;
    @BindView(R.id.btnCallUs)
    LinearLayout btnCallUs;

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

        if (prefHelper.getUser() != null && prefHelper.getUser().getRegistrationType() != null && !prefHelper.getUser().getRegistrationType().equals("")) {
            if (prefHelper.getUser().getRegistrationType().equals("1")) {
                btnChangePassword.setVisibility(View.GONE);
                changePassView.setVisibility(View.GONE);
            } else {
                btnChangePassword.setVisibility(View.VISIBLE);
                changePassView.setVisibility(View.VISIBLE);
            }
        }

        if(prefHelper.getUser()!=null && prefHelper.getUser().getNotification()!=null && prefHelper.getUser().getNotification().equals("1")){
            swtNotification.setChecked(true);
        }else{
            swtNotification.setChecked(false);
        }

        serviceHelper.enqueueCall(webService.getAdminDetail(), ADMIN);
        swtNotification.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (prefHelper.isGuest()) {
                    DialogHelper dialogHelper = new DialogHelper(getDockActivity());
                    dialogHelper.CreateAccountDialoge(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            getDockActivity().popBackStackTillEntry(0);
                            getDockActivity().replaceDockableFragment(LoginFragment.newInstance(false), "LoginFragment");
                            dialogHelper.hideDialog();
                        }
                    });
                    dialogHelper.showDialog();
                } else if (prefHelper.getUser() != null && prefHelper.getUser().getId() != null) {
                    UserEnt data = prefHelper.getUser();
                    data.setNotification(isChecked ? "1" : "0");
                    prefHelper.putUser(data);
                    serviceHelper.enqueueCall(webService.toggleNotification(prefHelper.getUser().getId() + "", isChecked ? "1" : "0"), toggleNotification);
                }
            }
        });

    }

    @Override
    public void ResponseSuccess(Object result, String Tag, String message) {
        super.ResponseSuccess(result, Tag, message);
        switch (Tag) {
            case ADMIN:
                AdminEnt adminEnt = (AdminEnt) result;
                adminNumber = adminEnt.getCountryCode() + adminEnt.getPhoneNo() + "";
                break;
        }
    }

    private void rateApp() {
        Uri uri = Uri.parse("market://details?id=" + getDockActivity().getPackageName());
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        // To count with Play market backstack, After pressing back button,
        // to taken back to our application, we need to add following flags to intent.
        goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        try {
            startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://play.google.com/store/apps/details?id=" + getDockActivity().getPackageName())));
        }
    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
        titleBar.showBackButton();
        titleBar.setSubHeading(getResString(R.string.app_settings));
    }

    @OnClick({R.id.btnChangePassword, R.id.btnRateApp, R.id.btnChangePhone, R.id.btnChangeLanguage, R.id.btnAboutApp, R.id.btnContactUs, R.id.btnCallUs, R.id.btn_english, R.id.btn_Arabic})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnChangePassword:
                if (prefHelper.isGuest()) {
                    DialogHelper dialogHelper = new DialogHelper(getDockActivity());
                    dialogHelper.CreateAccountDialoge(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            prefHelper.setLoginStatus(false);
                            prefHelper.setGuestStatus(false);
                            getDockActivity().popBackStackTillEntry(0);
                            getDockActivity().replaceDockableFragment(LoginFragment.newInstance(false), "LoginFragment");
                            dialogHelper.hideDialog();
                        }
                    });
                    dialogHelper.showDialog();
                } else {
                    getDockActivity().replaceDockableFragment(ChangePasswordFragment.newInstance(), ChangePasswordFragment.TAG);
                }
                break;
            case R.id.btnRateApp:
                rateApp();

                break;
            case R.id.btnChangePhone:
                if (prefHelper.isGuest()) {
                    DialogHelper dialogHelper = new DialogHelper(getDockActivity());
                    dialogHelper.CreateAccountDialoge(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            prefHelper.setLoginStatus(false);
                            prefHelper.setGuestStatus(false);
                            getDockActivity().popBackStackTillEntry(0);
                            getDockActivity().replaceDockableFragment(LoginFragment.newInstance(false), "LoginFragment");
                            dialogHelper.hideDialog();
                        }
                    });
                    dialogHelper.showDialog();
                } else {
                    getDockActivity().replaceDockableFragment(ChangePhoneFragment.newInstance(), ChangePhoneFragment.TAG);
                }

                break;
            case R.id.btnChangeLanguage:
                getDockActivity().replaceDockableFragment(ChangeLanguageFragment.newInstance(), ChangeLanguageFragment.TAG);

                break;
            case R.id.btnAboutApp:
                getDockActivity().replaceDockableFragment(AboutAppFragment.newInstance(AppConstants.ABOUT, getResString(R.string.about_app)), AboutAppFragment.TAG);

                break;
            case R.id.btnContactUs:
                if (prefHelper.isGuest()) {
                    DialogHelper dialogHelper = new DialogHelper(getDockActivity());
                    dialogHelper.CreateAccountDialoge(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            prefHelper.setLoginStatus(false);
                            prefHelper.setGuestStatus(false);
                            getDockActivity().popBackStackTillEntry(0);
                            getDockActivity().replaceDockableFragment(LoginFragment.newInstance(false), "LoginFragment");
                            dialogHelper.hideDialog();
                        }
                    });
                    dialogHelper.showDialog();
                } else {
                    getDockActivity().replaceDockableFragment(ContactUsFragment.newInstance(), ContactUsFragment.TAG);
                }

                break;
            case R.id.btnCallUs:
                if (adminNumber != null) {
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:" + adminNumber));
                    startActivity(intent);
                    getMainActivity().closeDrawer();
                }
                break;
            case R.id.btn_english:
                if (prefHelper.isGuest()) {
                    DialogHelper dialogHelper = new DialogHelper(getDockActivity());
                    dialogHelper.CreateAccountDialoge(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            prefHelper.setLoginStatus(false);
                            prefHelper.setGuestStatus(false);
                            getDockActivity().popBackStackTillEntry(0);
                            getDockActivity().replaceDockableFragment(LoginFragment.newInstance(false), "LoginFragment");
                            dialogHelper.hideDialog();
                        }
                    });
                    dialogHelper.showDialog();
                } else {
                    UIHelper.showShortToastInCenter(getDockActivity(), "Will be implemented in future version");
                }
                break;
            case R.id.btn_Arabic:
                if (prefHelper.isGuest()) {
                    DialogHelper dialogHelper = new DialogHelper(getDockActivity());
                    dialogHelper.CreateAccountDialoge(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            prefHelper.setLoginStatus(false);
                            prefHelper.setGuestStatus(false);
                            getDockActivity().popBackStackTillEntry(0);
                            getDockActivity().replaceDockableFragment(LoginFragment.newInstance(false), "LoginFragment");
                            dialogHelper.hideDialog();
                        }
                    });
                    dialogHelper.showDialog();
                } else {
                    UIHelper.showShortToastInCenter(getDockActivity(), "Will be implemented in future version");
                }
                break;
        }
    }


}
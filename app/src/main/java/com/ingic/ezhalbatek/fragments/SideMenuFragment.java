package com.ingic.ezhalbatek.fragments;

import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.iid.FirebaseInstanceId;
import com.ingic.ezhalbatek.R;
import com.ingic.ezhalbatek.entities.NavigationEnt;
import com.ingic.ezhalbatek.fragments.abstracts.BaseFragment;
import com.ingic.ezhalbatek.global.WebServiceConstants;
import com.ingic.ezhalbatek.helpers.DialogHelper;
import com.ingic.ezhalbatek.interfaces.RecyclerItemListener;
import com.ingic.ezhalbatek.ui.binders.NavigationBinder;
import com.ingic.ezhalbatek.ui.views.CustomRecyclerView;
import com.ingic.ezhalbatek.ui.views.TitleBar;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.ingic.ezhalbatek.global.WebServiceConstants.LOGOUT;

public class SideMenuFragment extends BaseFragment {
    @BindView(R.id.rv_nav)
    CustomRecyclerView rvNav;
    Unbinder unbinder;
    private ArrayList<NavigationEnt> navItems;
    private long mLastClickTime = 0;
    private RecyclerItemListener listener = (Ent, position, id) -> {
        NavigationEnt ent = (NavigationEnt) Ent;
        switch (ent.getTitle()) {
            case R.string.home:
                if (SystemClock.elapsedRealtime() - mLastClickTime < 2000) {
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime();

                getDockActivity().replaceDockableFragment(HomeFragment.newInstance(), "HomeFragment");
                getMainActivity().closeDrawer();
                break;
            case R.string.profile:
                if (SystemClock.elapsedRealtime() - mLastClickTime < 2000) {
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime();

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
                            getMainActivity().closeDrawer();
                        }
                    });
                    dialogHelper.showDialog();
                } else {
                    getDockActivity().replaceDockableFragment(ProfileFragment.newInstance(), "ProfileFragment");
                    getMainActivity().closeDrawer();
                }

                break;
            case R.string.subsctiption:
                if (SystemClock.elapsedRealtime() - mLastClickTime < 2000) {
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime();

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
                            getMainActivity().closeDrawer();
                        }
                    });
                    dialogHelper.showDialog();
                } else {

                    if (prefHelper.getUser().getUserSubscription() != null) {
                        getDockActivity().replaceDockableFragment(PackageDetailFragment.newInstance(), "PackageDetailFragment");
                    } else {
                        getDockActivity().replaceDockableFragment(SubscriptionsPackagesFragment.newInstance(), "SubscriptionsPackagesFragment");
                    }
                    getMainActivity().closeDrawer();
                }
                break;
            case R.string.my_subscription:
                if (SystemClock.elapsedRealtime() - mLastClickTime < 2000) {
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime();

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
                            getMainActivity().closeDrawer();
                        }
                    });
                    dialogHelper.showDialog();
                } else {
                    prefHelper.setIsFromVisitSubscriber(true);
                    prefHelper.setIsFromInProgressSubscriber(false);
                    prefHelper.setIsFromCompletedSubscriber(false);
                    getDockActivity().replaceDockableFragment(SubscriptionStatusFragment.newInstance(), SubscriptionStatusFragment.TAG);
                    getMainActivity().closeDrawer();
                }
                break;
            case R.string.my_services:
                if (SystemClock.elapsedRealtime() - mLastClickTime < 2000) {
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime();

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
                            getMainActivity().closeDrawer();
                        }
                    });
                    dialogHelper.showDialog();
                } else {
                    prefHelper.setIsFromPending(true);
                    getDockActivity().replaceDockableFragment(MyServicesFragment.newInstance(), MyServicesFragment.TAG);
                    getMainActivity().closeDrawer();
                }

                break;
            case R.string.settings:
                if (SystemClock.elapsedRealtime() - mLastClickTime < 2000) {
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime();
                getDockActivity().replaceDockableFragment(SettingFragment.newInstance(), SettingFragment.TAG);
                getMainActivity().closeDrawer();

                break;
            case R.string.logout:
                if (SystemClock.elapsedRealtime() - mLastClickTime < 2000) {
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime();

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
                            getMainActivity().closeDrawer();
                        }
                    });
                    dialogHelper.showDialog();
                } else {
                    getMainActivity().closeDrawer();
                    dialogHelper.showCommonDialog(v -> {
                        if (prefHelper.getUser().getId() != null) {
                            serviceHelper.enqueueCall(webService.logout(prefHelper.getUser().getId(), FirebaseInstanceId.getInstance().getToken()), LOGOUT);
                        }else{
                            prefHelper.setLoginStatus(false);
                            prefHelper.setGuestStatus(false);
                            getDockActivity().popBackStackTillEntry(0);
                            getDockActivity().replaceDockableFragment(LoginFragment.newInstance(false), "LoginFragment");
                        }
                        dialogHelper.hideDialog();
                    }, R.string.logout, R.string.logout_message, R.string.yes, R.string.no, true, true);
                    dialogHelper.setCancelable(true);
                    dialogHelper.showDialog();
                }
                break;

            case R.string.sign_in:
                if (SystemClock.elapsedRealtime() - mLastClickTime < 2000) {
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime();

                prefHelper.setLoginStatus(false);
                prefHelper.setGuestStatus(false);
                getDockActivity().popBackStackTillEntry(0);
                getDockActivity().replaceDockableFragment(LoginFragment.newInstance(false), "LoginFragment");
                getMainActivity().closeDrawer();
                break;
        }
    };

    public static SideMenuFragment newInstance() {
        return new SideMenuFragment();

    }

    @Override
    public void ResponseSuccess(Object result, String Tag, String message) {
        super.ResponseSuccess(result, Tag, message);
        switch (Tag) {
            case LOGOUT:
                prefHelper.setLoginStatus(false);
                prefHelper.setGuestStatus(false);
                getDockActivity().popBackStackTillEntry(0);
                getDockActivity().replaceDockableFragment(LoginFragment.newInstance(false), "LoginFragment");
                break;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideTitleBar();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sidemenu, container, false);

        unbinder = ButterKnife.bind(this, view);
        return view;

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bindNavItems();

    }



    private void bindNavItems() {
        navItems = new ArrayList<>();
       /* if (!prefHelper.isLogin()) {
            navItems.add(new NavigationEnt(R.drawable.home_nav, (R.string.login)));
        }*/
        navItems.add(new NavigationEnt(R.drawable.home, (R.string.home)));
        navItems.add(new NavigationEnt(R.drawable.profile, (R.string.profile)));
        navItems.add(new NavigationEnt(R.drawable.subscription, (R.string.subsctiption)));
        navItems.add(new NavigationEnt(R.drawable.subscriptionstatus, (R.string.my_subscription)));
        navItems.add(new NavigationEnt(R.drawable.servicestatus, (R.string.my_services)));
        navItems.add(new NavigationEnt(R.drawable.settings, (R.string.settings)));
        if (prefHelper.isGuest() || !prefHelper.isLogin()) {
            navItems.add(new NavigationEnt(R.drawable.logout, (R.string.sign_in)));
        } else {
            navItems.add(new NavigationEnt(R.drawable.logout, (R.string.logout)));
        }
        /*if (prefHelper.isLogin()) {
            navItems.add(new NavigationEnt(R.drawable.home_nav, (R.string.logout)));
        }*/
        rvNav.bindRecyclerView(new NavigationBinder(listener,prefHelper), navItems,
                new LinearLayoutManager(getDockActivity(), LinearLayoutManager.VERTICAL, false),
                new DefaultItemAnimator());
    }

    public void refreshMenuOption() {
        if (rvNav != null) {
            bindNavItems();
        }
    }

}

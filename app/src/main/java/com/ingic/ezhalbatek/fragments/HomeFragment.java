package com.ingic.ezhalbatek.fragments;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ingic.ezhalbatek.R;
import com.ingic.ezhalbatek.entities.AllCategoriesEnt;
import com.ingic.ezhalbatek.entities.NotificationCountEnt;
import com.ingic.ezhalbatek.fragments.abstracts.BaseFragment;
import com.ingic.ezhalbatek.global.AppConstants;
import com.ingic.ezhalbatek.global.WebServiceConstants;
import com.ingic.ezhalbatek.helpers.DialogHelper;
import com.ingic.ezhalbatek.helpers.InternetHelper;
import com.ingic.ezhalbatek.interfaces.RecyclerItemListener;
import com.ingic.ezhalbatek.retrofit.WebService;
import com.ingic.ezhalbatek.ui.binders.CategoryItemBinder;
import com.ingic.ezhalbatek.ui.views.AnyTextView;
import com.ingic.ezhalbatek.ui.views.CustomRecyclerView;
import com.ingic.ezhalbatek.ui.views.TitleBar;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.ingic.ezhalbatek.global.WebServiceConstants.ALLSERVICES;
import static com.ingic.ezhalbatek.global.WebServiceConstants.NotificationCount;


public class HomeFragment extends BaseFragment {


    public static final String TAG = "HomeFragment";
    @BindView(R.id.rvCategories)
    CustomRecyclerView rvCategories;
    @BindView(R.id.txt_no_data)
    AnyTextView txtNoData;
    protected BroadcastReceiver broadcastReceiver;
    private Integer Count;


    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getMainActivity().changeBackgroundResources(R.drawable.bg2);
        rvCategories.setNestedScrollingEnabled(false);

        getMainActivity().refreshSideMenu();
        onNotificationReceived();



        if (InternetHelper.CheckInternetConectivityandShowToast(getDockActivity())) {
            txtNoData.setVisibility(View.GONE);
            serviceHelper.enqueueCall(webService.getAllCategories(), ALLSERVICES);
        } else {
            txtNoData.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void ResponseSuccess(Object result, String Tag, String message) {
        super.ResponseSuccess(result, Tag, message);
        switch (Tag) {
            case ALLSERVICES:
                ArrayList<AllCategoriesEnt> allservices = (ArrayList<AllCategoriesEnt>) result;
                bindRecyclerView(allservices);
                break;


        }
    }


    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
        titleBar.setSubHeading(getResString(R.string.home));
        titleBar.showMenuButton();

        if (prefHelper.isGuest()) {
            titleBar.showNotificationButton(0, new View.OnClickListener() {
                @Override
                public void onClick(View view) {
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
                }
            });
        } else {
            Count = prefHelper.getNotificationCount();
            titleBar.showNotificationButton(Count != null ? Count : 0);

        }


    }


    private void bindRecyclerView(ArrayList<AllCategoriesEnt> allservices) {

       /* categoryCollection = new ArrayList<>();
        categoryCollection.add(new CategoryEnt("AC Repair", R.drawable.acrepair));
        categoryCollection.add(new CategoryEnt("Plumbing", R.drawable.plumbing));
        categoryCollection.add(new CategoryEnt("Electricity", R.drawable.electricity));
        categoryCollection.add(new CategoryEnt("Painting", R.drawable.paint));
        categoryCollection.add(new CategoryEnt("Ceiling Gypsum", R.drawable.gypsum));
        categoryCollection.add(new CategoryEnt("Carpentry", R.drawable.carpentry));
        categoryCollection.add(new CategoryEnt("Flooring", R.drawable.floor));
        categoryCollection.add(new CategoryEnt("Swimming Pool", R.drawable.swimmingpool));
        categoryCollection.add(new CategoryEnt("Others", R.drawable.others));*/

        if (allservices.size() > 0) {
            GridLayoutManager gridLayoutManager = new GridLayoutManager(getDockActivity(), 3);
            rvCategories.bindRecyclerView(new CategoryItemBinder(itemClickListener,prefHelper), allservices, gridLayoutManager, new DefaultItemAnimator());
        } else {
            rvCategories.setVisibility(View.GONE);
        }


    }


    private RecyclerItemListener itemClickListener = new RecyclerItemListener() {
        @Override
        public void onItemClicked(Object ent, int position, int id) {
            getDockActivity().replaceDockableFragment(RequestDetailFragment.newInstance(((AllCategoriesEnt) ent).getTitle(), (AllCategoriesEnt) ent), RequestDetailFragment.TAG);
        }
    };

    private void onNotificationReceived() {
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                // checking for type intent filter
                if (intent.getAction().equals(AppConstants.REGISTRATION_COMPLETE)) {
                    System.out.println("registration complete");
                    System.out.println(prefHelper.getFirebase_TOKEN());

                } else if (intent.getAction().equals(AppConstants.PUSH_NOTIFICATION)) {
                    Bundle bundle = intent.getExtras();
                    if (getTitleBar() != null)
                        getTitleBar().showNotificationButton(prefHelper.getNotificationCount());
                }
            }

        };
    }

    @Override
    public void onPause() {
        LocalBroadcastManager.getInstance(getDockActivity()).unregisterReceiver(broadcastReceiver);
        super.onPause();
    }


    @Override
    public void onResume() {
        super.onResume();
        getMainActivity().releaseDrawer();
        LocalBroadcastManager.getInstance(getDockActivity()).registerReceiver(broadcastReceiver,
                new IntentFilter(AppConstants.REGISTRATION_COMPLETE));

        LocalBroadcastManager.getInstance(getDockActivity()).registerReceiver(broadcastReceiver,
                new IntentFilter(AppConstants.PUSH_NOTIFICATION));
    }

}


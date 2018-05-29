package com.ingic.ezhalbatek.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ingic.ezhalbatek.R;
import com.ingic.ezhalbatek.entities.NavigationEnt;
import com.ingic.ezhalbatek.fragments.abstracts.BaseFragment;
import com.ingic.ezhalbatek.interfaces.RecyclerItemListener;
import com.ingic.ezhalbatek.ui.binders.NavigationBinder;
import com.ingic.ezhalbatek.ui.views.CustomRecyclerView;
import com.ingic.ezhalbatek.ui.views.TitleBar;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class SideMenuFragment extends BaseFragment {
    @BindView(R.id.rv_nav)
    CustomRecyclerView rvNav;
    Unbinder unbinder;
    private ArrayList<NavigationEnt> navItems;
    private RecyclerItemListener listener = new RecyclerItemListener() {
        @Override
        public void onItemClicked(Object Ent, int position, int id) {
            NavigationEnt ent = (NavigationEnt) Ent;
            switch (ent.getTitle()) {
                case R.string.home:
                    getMainActivity().closeDrawer();
                    break;
                case R.string.profile:
                    getMainActivity().closeDrawer();
                    break;
                case R.string.subsctiption:
                    getMainActivity().closeDrawer();
                    break;
                case R.string.my_subscription:
                    getMainActivity().closeDrawer();
                    break;
                case R.string.my_services:
                    getMainActivity().closeDrawer();

                    break;
                case R.string.settings:
                    getMainActivity().closeDrawer();

                    break;
                case R.string.logout:

                    getMainActivity().closeDrawer();

                    break;
            }
        }
    };

    public static SideMenuFragment newInstance() {
        return new SideMenuFragment();

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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();

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
        navItems.add(new NavigationEnt(R.drawable.logout, (R.string.logout)));
        /*if (prefHelper.isLogin()) {
            navItems.add(new NavigationEnt(R.drawable.home_nav, (R.string.logout)));
        }*/
        rvNav.bindRecyclerView(new NavigationBinder(listener), navItems,
                new LinearLayoutManager(getDockActivity(), LinearLayoutManager.VERTICAL, false),
                new DefaultItemAnimator());
    }
}

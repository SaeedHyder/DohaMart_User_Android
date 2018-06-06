package com.ingic.ezhalbatek.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ingic.ezhalbatek.R;
import com.ingic.ezhalbatek.entities.CategoryEnt;
import com.ingic.ezhalbatek.fragments.abstracts.BaseFragment;
import com.ingic.ezhalbatek.interfaces.RecyclerItemListener;
import com.ingic.ezhalbatek.ui.binders.CategoryItemBinder;
import com.ingic.ezhalbatek.ui.views.CustomRecyclerView;
import com.ingic.ezhalbatek.ui.views.TitleBar;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class HomeFragment extends BaseFragment {


    public static final String TAG = "HomeFragment";
    @BindView(R.id.rvCategories)
    CustomRecyclerView rvCategories;
    ArrayList<CategoryEnt> categoryCollection;
    private RecyclerItemListener itemClickListener = new RecyclerItemListener() {
        @Override
        public void onItemClicked(Object ent, int position, int id) {
            getDockActivity().replaceDockableFragment(RequestDetailFragment.newInstance(), RequestDetailFragment.TAG);
        }
    };

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        getMainActivity().releaseDrawer();
    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
        titleBar.showMenuButton();
        titleBar.setSubHeading(getResString(R.string.home));
        titleBar.showNotificationButton(0);

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
        bindRecyclerView();
    }

    private void bindRecyclerView() {
        categoryCollection = new ArrayList<>();
        categoryCollection.add(new CategoryEnt("AC Repair", R.drawable.acrepair));
        categoryCollection.add(new CategoryEnt("Plumbing", R.drawable.plumbing));
        categoryCollection.add(new CategoryEnt("Electricity", R.drawable.electricity));
        categoryCollection.add(new CategoryEnt("Painting", R.drawable.paint));
        categoryCollection.add(new CategoryEnt("Celiling Gypsum", R.drawable.gypsum));
        categoryCollection.add(new CategoryEnt("Carpentry", R.drawable.carpentry));
        categoryCollection.add(new CategoryEnt("Flooring", R.drawable.floor));
        categoryCollection.add(new CategoryEnt("Swiming Pool", R.drawable.swimmingpool));
        categoryCollection.add(new CategoryEnt("Others", R.drawable.others));
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getDockActivity(), 3);
        rvCategories.bindRecyclerView(new CategoryItemBinder(itemClickListener), categoryCollection, gridLayoutManager, new DefaultItemAnimator());
    }


}


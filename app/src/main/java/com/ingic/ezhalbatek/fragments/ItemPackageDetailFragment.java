package com.ingic.ezhalbatek.fragments;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.ingic.ezhalbatek.R;
import com.ingic.ezhalbatek.entities.LoginModule.Services;
import com.ingic.ezhalbatek.entities.SubscriptionPackagesEnt;
import com.ingic.ezhalbatek.entities.SubscriptionsDetail;
import com.ingic.ezhalbatek.fragments.abstracts.BaseFragment;
import com.ingic.ezhalbatek.ui.binders.FeatureItemBinder;
import com.ingic.ezhalbatek.ui.views.AnyTextView;
import com.ingic.ezhalbatek.ui.views.CustomRecyclerView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created on 5/21/18.
 */
public class ItemPackageDetailFragment extends BaseFragment {
    public static final String TAG = "ItemPackageDetailFragment";
    @BindView(R.id.txtDuration)
    AnyTextView txtDuration;
    @BindView(R.id.rvFeatures)
    CustomRecyclerView rvFeatures;
    @BindView(R.id.txtPrice)
    AnyTextView txtPrice;
    Unbinder unbinder;
    private ArrayList<String> featureCollection;

    private static String SUBSCRIPTIONDETAIL = "SUBSCRIPTIONDETAIL";
    private SubscriptionsDetail subscriptionsDetail;

    public static ItemPackageDetailFragment newInstance(SubscriptionsDetail item) {
        Bundle args = new Bundle();
        args.putString(SUBSCRIPTIONDETAIL, new Gson().toJson(item));
        ItemPackageDetailFragment fragment = new ItemPackageDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            String jsonString = getArguments().getString(SUBSCRIPTIONDETAIL);

            if (jsonString != null) {
                subscriptionsDetail = new Gson().fromJson(jsonString, SubscriptionsDetail.class);
            }
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_package_detail, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bindRecyclerView();
    }


    private void bindRecyclerView() {
        if (subscriptionsDetail != null) {

            txtDuration.setText(subscriptionsDetail.getSubscriptionDuration() + " MONTHS");
            txtPrice.setText("AED " + subscriptionsDetail.getAmount());

            featureCollection = new ArrayList<>();
            if (subscriptionsDetail.getServices() != null && subscriptionsDetail.getServices().size() > 0)
                for (Services item : subscriptionsDetail.getServices()) {
                    featureCollection.add("Check " + item.getService().getTitle());
                }
        }

        rvFeatures.bindRecyclerView(new FeatureItemBinder(), featureCollection, new LinearLayoutManager(getDockActivity(), LinearLayoutManager.VERTICAL, false), new DefaultItemAnimator());
    }
}
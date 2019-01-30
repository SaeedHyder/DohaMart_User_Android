package com.ingic.ezhalbatek.fragments;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.ingic.ezhalbatek.R;
import com.ingic.ezhalbatek.entities.ServiceStatus.Service;
import com.ingic.ezhalbatek.entities.ServiceStatus.Subscription;
import com.ingic.ezhalbatek.fragments.abstracts.BaseFragment;
import com.ingic.ezhalbatek.ui.binders.PendingServiceBinder;
import com.ingic.ezhalbatek.ui.binders.SubscriptionPendingJobsBinder;
import com.ingic.ezhalbatek.ui.views.AnyTextView;
import com.ingic.ezhalbatek.ui.views.CustomRecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class SubscriptionVisitFragment extends BaseFragment {
    public static final String TAG = "SubscriptionPendingFragment";
    @BindView(R.id.rvPendingJobs)
    CustomRecyclerView rvPendingJobs;
    Unbinder unbinder;
    @BindView(R.id.txt_no_data)
    AnyTextView txtNoData;

    private static String VisitdKey = "VisitdKey";
    private ArrayList<Subscription> visitRequest;

    public static SubscriptionVisitFragment newInstance() {
        Bundle args = new Bundle();

        SubscriptionVisitFragment fragment = new SubscriptionVisitFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public static SubscriptionVisitFragment newInstance(ArrayList<Subscription> visit) {
        Bundle args = new Bundle();
        args.putString(VisitdKey, new Gson().toJson(visit));
        SubscriptionVisitFragment fragment = new SubscriptionVisitFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            String jsonString = getArguments().getString(VisitdKey);

            if (jsonString != null) {
                visitRequest = new Gson().fromJson(jsonString, new TypeToken<List<Subscription>>() {
                }.getType());
            }
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_susbcription_status_pending, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        prefHelper.setIsFromVisitSubscriber(true);
        prefHelper.setIsFromInProgressSubscriber(false);
        prefHelper.setIsFromCompletedSubscriber(false);


        if (visitRequest != null && visitRequest.size() > 0) {
            rvPendingJobs.setVisibility(View.VISIBLE);
            txtNoData.setVisibility(View.GONE);
            Collections.reverse(visitRequest);
            rvPendingJobs.bindRecyclerView(new SubscriptionPendingJobsBinder(true), visitRequest, new LinearLayoutManager(getDockActivity(), LinearLayoutManager.VERTICAL, false), new DefaultItemAnimator());

        } else {
            rvPendingJobs.setVisibility(View.GONE);
            txtNoData.setVisibility(View.VISIBLE);
        }

    }



}

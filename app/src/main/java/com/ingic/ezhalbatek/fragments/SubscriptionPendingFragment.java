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
import com.ingic.ezhalbatek.entities.ServiceStatus.Subscription;
import com.ingic.ezhalbatek.fragments.abstracts.BaseFragment;
import com.ingic.ezhalbatek.ui.binders.SubscriptionPendingJobsBinder;
import com.ingic.ezhalbatek.ui.views.AnyTextView;
import com.ingic.ezhalbatek.ui.views.CustomRecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created on 6/2/18.
 */
public class SubscriptionPendingFragment extends BaseFragment {
    public static final String TAG = "SubscriptionPendingFragment";
    @BindView(R.id.rvPendingJobs)
    CustomRecyclerView rvPendingJobs;
    Unbinder unbinder;
    @BindView(R.id.txt_no_data)
    AnyTextView txtNoData;

    private static String pendingdKey = "pendingdKey";
    private ArrayList<Subscription> pendingRequest;

    public static SubscriptionPendingFragment newInstance() {
        Bundle args = new Bundle();

        SubscriptionPendingFragment fragment = new SubscriptionPendingFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public static SubscriptionPendingFragment newInstance(ArrayList<Subscription> pending) {
        Bundle args = new Bundle();
        args.putString(pendingdKey, new Gson().toJson(pending));
        SubscriptionPendingFragment fragment = new SubscriptionPendingFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            String jsonString = getArguments().getString(pendingdKey);

            if (jsonString != null) {
                pendingRequest = new Gson().fromJson(jsonString, new TypeToken<List<Subscription>>() {
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
        prefHelper.setIsFromVisitSubscriber(false);
        prefHelper.setIsFromInProgressSubscriber(true);
        prefHelper.setIsFromCompletedSubscriber(false);

        if (pendingRequest != null && pendingRequest.size() > 0) {
            rvPendingJobs.setVisibility(View.VISIBLE);
            txtNoData.setVisibility(View.GONE);
            Collections.reverse(pendingRequest);
            rvPendingJobs.bindRecyclerView(new SubscriptionPendingJobsBinder(false), pendingRequest, new LinearLayoutManager(getDockActivity(), LinearLayoutManager.VERTICAL, false), new DefaultItemAnimator());

        } else {
            txtNoData.setText(R.string.no_pending_job_found);
            txtNoData.setVisibility(View.VISIBLE);
            rvPendingJobs.setVisibility(View.GONE);
        }

    }

}
package com.ingic.ezhalbatek.fragments;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ingic.ezhalbatek.R;
import com.ingic.ezhalbatek.fragments.abstracts.BaseFragment;
import com.ingic.ezhalbatek.ui.binders.SubscriptionPendingJobsBinder;
import com.ingic.ezhalbatek.ui.views.CustomRecyclerView;

import java.util.ArrayList;

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

    public static SubscriptionPendingFragment newInstance() {
        Bundle args = new Bundle();

        SubscriptionPendingFragment fragment = new SubscriptionPendingFragment();
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
        View view = inflater.inflate(R.layout.fragment_susbcription_status_pending, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ArrayList<String> jobs = new ArrayList<>();
        jobs.add("");
        rvPendingJobs.bindRecyclerView(new SubscriptionPendingJobsBinder(), jobs, new LinearLayoutManager(getDockActivity(), LinearLayoutManager.VERTICAL, false), new DefaultItemAnimator());

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
package com.ingic.ezhalbatek.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ingic.ezhalbatek.R;
import com.ingic.ezhalbatek.fragments.abstracts.BaseFragment;
import com.ingic.ezhalbatek.interfaces.RecyclerItemListener;
import com.ingic.ezhalbatek.ui.binders.SubscriptionCompleteJobsBinder;
import com.ingic.ezhalbatek.ui.views.CustomRecyclerView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created on 6/2/18.
 */
public class SubscriptionCompleteFragment extends BaseFragment {
    public static final String TAG = "SubscriptionCompleteFragment";
    @BindView(R.id.rvCompleteJobs)
    CustomRecyclerView rvCompleteJobs;
    Unbinder unbinder;
    private RecyclerItemListener listener = (ent, position, id) -> {
        switch (id) {
            case R.id.btnReport:
                getDockActivity().replaceDockableFragment(JobReportFragment.newInstance(),JobReportFragment.TAG);
                break;
            case R.id.btnRate:
                getDockActivity().replaceDockableFragment(RateTechnicianFragment.newInstance(),RateTechnicianFragment.TAG);
                break;
        }
    };

    public static SubscriptionCompleteFragment newInstance() {
        Bundle args = new Bundle();

        SubscriptionCompleteFragment fragment = new SubscriptionCompleteFragment();
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
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_complete_subscription_status, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ArrayList<String> jobs = new ArrayList<>();
        jobs.add("");
        rvCompleteJobs.bindRecyclerView(new SubscriptionCompleteJobsBinder(listener), jobs, new LinearLayoutManager(getDockActivity(), LinearLayoutManager.VERTICAL, false), new DefaultItemAnimator());

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
package com.ingic.ezhalbatek.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
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
import com.ingic.ezhalbatek.helpers.UIHelper;
import com.ingic.ezhalbatek.interfaces.RecyclerItemListener;
import com.ingic.ezhalbatek.ui.binders.SubscriptionCompleteJobsBinder;
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
public class SubscriptionCompleteFragment extends BaseFragment {
    public static final String TAG = "SubscriptionCompleteFragment";
    @BindView(R.id.rvCompleteJobs)
    CustomRecyclerView rvCompleteJobs;
    Unbinder unbinder;
    @BindView(R.id.txt_no_data)
    AnyTextView txtNoData;

    private static String CompletedKey = "CompletedKey";
    private ArrayList<Subscription> completedRequest;

    public static SubscriptionCompleteFragment newInstance() {
        Bundle args = new Bundle();

        SubscriptionCompleteFragment fragment = new SubscriptionCompleteFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public static SubscriptionCompleteFragment newInstance(ArrayList<Subscription> completed) {
        Bundle args = new Bundle();
        args.putString(CompletedKey, new Gson().toJson(completed));
        SubscriptionCompleteFragment fragment = new SubscriptionCompleteFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            String jsonString = getArguments().getString(CompletedKey);

            if (jsonString != null) {
                completedRequest = new Gson().fromJson(jsonString, new TypeToken<List<Subscription>>() {
                }.getType());
            }
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
        prefHelper.setIsFromVisitSubscriber(false);
        prefHelper.setIsFromInProgressSubscriber(false);
        prefHelper.setIsFromCompletedSubscriber(true);

        if (prefHelper.isLanguageArabian()) {
            view.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);

        } else {
            view.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
        }

        if (completedRequest != null && completedRequest.size() > 0) {
            rvCompleteJobs.setVisibility(View.VISIBLE);
            txtNoData.setVisibility(View.GONE);
            Collections.reverse(completedRequest);
            rvCompleteJobs.bindRecyclerView(new SubscriptionCompleteJobsBinder(listener), completedRequest, new LinearLayoutManager(getDockActivity(), LinearLayoutManager.VERTICAL, false), new DefaultItemAnimator());

        } else {
            rvCompleteJobs.setVisibility(View.GONE);
            txtNoData.setVisibility(View.VISIBLE);
        }

    }


    private RecyclerItemListener listener = (ent, position, id) -> {
        switch (id) {
            case R.id.btnReport:
                Subscription data = (Subscription) ent;
                getDockActivity().replaceDockableFragment(JobReportFragment.newInstance(data), JobReportFragment.TAG);
                break;
            case R.id.btnRate:
                Subscription entity = (Subscription) ent;
                if (entity.getFeedback() == null) {
                    getDockActivity().replaceDockableFragment(RateTechnicianFragment.newInstance(entity), RateTechnicianFragment.TAG);
                } else {
                    UIHelper.showShortToastInCenter(getDockActivity(), "Already rating submitted");
                }
                break;
        }
    };
}
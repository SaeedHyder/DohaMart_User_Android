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
import com.ingic.ezhalbatek.fragments.abstracts.BaseFragment;
import com.ingic.ezhalbatek.helpers.UIHelper;
import com.ingic.ezhalbatek.interfaces.RecyclerItemListener;
import com.ingic.ezhalbatek.ui.binders.CompleteServiceBinder;
import com.ingic.ezhalbatek.ui.binders.PendingServiceBinder;
import com.ingic.ezhalbatek.ui.views.AnyTextView;
import com.ingic.ezhalbatek.ui.views.CustomRecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created on 6/6/18.
 */
public class CompleteServicesFragment extends BaseFragment {
    public static final String TAG = "CompleteServicesFragment";
    @BindView(R.id.rvCompleteJobs)
    CustomRecyclerView rvCompleteJobs;
    Unbinder unbinder;
    @BindView(R.id.txt_no_data)
    AnyTextView txtNoData;

    private static String CompletedKey = "CompletedKey";
    private ArrayList<Service> completedRequest;

    public static CompleteServicesFragment newInstance() {
        Bundle args = new Bundle();

        CompleteServicesFragment fragment = new CompleteServicesFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public static CompleteServicesFragment newInstance(ArrayList<Service> completed) {
        Bundle args = new Bundle();
        args.putString(CompletedKey, new Gson().toJson(completed));
        CompleteServicesFragment fragment = new CompleteServicesFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            String jsonString = getArguments().getString(CompletedKey);

            if (jsonString != null) {
                completedRequest = new Gson().fromJson(jsonString, new TypeToken<List<Service>>() {
                }.getType());
            }
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_complete_services, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        prefHelper.setIsFromPending(false);

        if (completedRequest != null && completedRequest.size() > 0) {
            rvCompleteJobs.setVisibility(View.VISIBLE);
            txtNoData.setVisibility(View.GONE);
            Collections.reverse(completedRequest);
            rvCompleteJobs.bindRecyclerView(new PendingServiceBinder(ItemClicklistener, true), completedRequest, new LinearLayoutManager(getDockActivity(), LinearLayoutManager.VERTICAL, false), new DefaultItemAnimator());

        } else {
            rvCompleteJobs.setVisibility(View.GONE);
            txtNoData.setVisibility(View.VISIBLE);
        }

    }

    private RecyclerItemListener ItemClicklistener = ((ent, position, id) -> {
        switch (id) {
            case R.id.btnCallRate:
                Service data = (Service) ent;
                getDockActivity().replaceDockableFragment(RateTechnicianFragment.newInstance(data), RateTechnicianFragment.TAG);


                break;
            case R.id.btnDetails:
                Service entity = (Service) ent;
                getDockActivity().replaceDockableFragment(ServiceDetailFragment.newInstance(true, entity), ServiceDetailFragment.TAG);
                break;
        }
    });

}
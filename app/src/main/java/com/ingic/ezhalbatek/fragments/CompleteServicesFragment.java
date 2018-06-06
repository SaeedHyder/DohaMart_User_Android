package com.ingic.ezhalbatek.fragments;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ingic.ezhalbatek.R;
import com.ingic.ezhalbatek.fragments.abstracts.BaseFragment;
import com.ingic.ezhalbatek.interfaces.RecyclerItemListener;
import com.ingic.ezhalbatek.ui.binders.CompleteServiceBinder;
import com.ingic.ezhalbatek.ui.views.CustomRecyclerView;

import java.util.ArrayList;

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
    private RecyclerItemListener ItemClicklistener = ((ent, position, id) -> {
        switch (id) {
            case R.id.btnCallRate:
                getDockActivity().replaceDockableFragment(RateTechnicianFragment.newInstance(), RateTechnicianFragment.TAG);

                break;
            case R.id.btnDetails:
                getDockActivity().replaceDockableFragment(ServiceDetailFragment.newInstance(), ServiceDetailFragment.TAG);
                break;
        }
    });

    public static CompleteServicesFragment newInstance() {
        Bundle args = new Bundle();

        CompleteServicesFragment fragment = new CompleteServicesFragment();
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
        View view = inflater.inflate(R.layout.fragment_complete_services, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ArrayList<String> jobs = new ArrayList<>();
        jobs.add("");
        rvCompleteJobs.bindRecyclerView(new CompleteServiceBinder(ItemClicklistener), jobs,
                new LinearLayoutManager(getDockActivity(), LinearLayoutManager.VERTICAL, false), new DefaultItemAnimator());

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
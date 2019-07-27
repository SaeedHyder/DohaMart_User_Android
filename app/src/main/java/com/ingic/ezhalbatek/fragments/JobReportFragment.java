package com.ingic.ezhalbatek.fragments;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.ingic.ezhalbatek.R;
import com.ingic.ezhalbatek.entities.ServiceStatus.AdditionalJob;
import com.ingic.ezhalbatek.entities.ServiceStatus.Subscription;
import com.ingic.ezhalbatek.fragments.abstracts.BaseFragment;
import com.ingic.ezhalbatek.global.AppConstants;
import com.ingic.ezhalbatek.ui.binders.AdditionalJobDoneBinder;
import com.ingic.ezhalbatek.ui.views.AnyTextView;
import com.ingic.ezhalbatek.ui.views.CustomRatingBar;
import com.ingic.ezhalbatek.ui.views.CustomRecyclerView;
import com.ingic.ezhalbatek.ui.views.TitleBar;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created on 6/4/18.
 */
public class JobReportFragment extends BaseFragment {
    public static final String TAG = "JobReportFragment";
    @BindView(R.id.txtDuration)
    AnyTextView txtDuration;
    @BindView(R.id.txtServiceID)
    AnyTextView txtServiceID;
    @BindView(R.id.txtDate)
    AnyTextView txtDate;
    @BindView(R.id.txtVisitDate)
    AnyTextView txtVisitDate;
    @BindView(R.id.txtTimeDuration)
    AnyTextView txtTimeDuration;
    @BindView(R.id.txtTask)
    AnyTextView txtTask;
    @BindView(R.id.txtTechnicianName)
    AnyTextView txtTechnicianName;
    @BindView(R.id.txtTechnicianNumber)
    AnyTextView txtTechnicianNumber;
    @BindView(R.id.rbRating)
    CustomRatingBar rbRating;
    Unbinder unbinder;
    @BindView(R.id.rv_jobs)
    CustomRecyclerView rvJobs;
    @BindView(R.id.task_summary)
    AnyTextView task_summary;
    @BindView(R.id.txtStatus)
    AnyTextView txtStatus;
    private Double amount = 0.0;

    private static String subscriptionEntKey = "subscriptionEntKey";
    @BindView(R.id.txtTotalEarning)
    AnyTextView txtTotalEarning;
    @BindView(R.id.ll_total)
    LinearLayout llTotal;
    private Subscription subEntity;
    private ArrayList<AdditionalJob> collection;

    public static JobReportFragment newInstance() {
        Bundle args = new Bundle();

        JobReportFragment fragment = new JobReportFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public static JobReportFragment newInstance(Subscription subscriptionEnt) {
        Bundle args = new Bundle();
        args.putString(subscriptionEntKey, new Gson().toJson(subscriptionEnt));
        JobReportFragment fragment = new JobReportFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            String jsonSubString = getArguments().getString(subscriptionEntKey);

            if (jsonSubString != null) {
                subEntity = new Gson().fromJson(jsonSubString, Subscription.class);
            }
        }

    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
        titleBar.showBackButton();
        titleBar.setSubHeading(getResString(R.string.view_report));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_job_report, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        amount = 0.0;
        setData();
    }

    private void setData() {

        collection = new ArrayList<>();
        if (subEntity != null) {
            txtServiceID.setText(subEntity.getId() + "");
            txtDate.setText(subEntity.getVisitDate() + "");
            txtVisitDate.setText(subEntity.getVisitDate() + "");
            txtTimeDuration.setText(subEntity.getStartTime() + " to " + subEntity.getEndTime());

            if (subEntity.getTechnician() != null) {
                txtTechnicianName.setText(subEntity.getTechnician().getFullName() != null ? subEntity.getTechnician().getFullName() : "-");
                txtTechnicianNumber.setText(subEntity.getTechnician().getPhoneNo() != null ? subEntity.getTechnician().getPhoneNo() : "-");
            }

            if (subEntity.getAdditionalJobs() != null && subEntity.getAdditionalJobs().size() > 0) {
                task_summary.setVisibility(View.VISIBLE);

                for (AdditionalJob item : subEntity.getAdditionalJobs()) {
                    if (item.getStatus() == 2) {
                        collection.add(item);
                        amount = amount + (Double.parseDouble(item.getItem().getAmount()) * item.getQuantity());
                    }
                }

                rvJobs.bindRecyclerView(new AdditionalJobDoneBinder(prefHelper), collection, new LinearLayoutManager(getDockActivity(), LinearLayoutManager.VERTICAL, false), new DefaultItemAnimator());

            }
            if (collection.size() <= 0) {
                rvJobs.setVisibility(View.GONE);
                task_summary.setVisibility(View.GONE);
                llTotal.setVisibility(View.GONE);
            }

            if (subEntity.getFeedback() != null) {
                rbRating.setScore(Float.parseFloat(subEntity.getFeedback().getRate()));
            } else {
                rbRating.setScore(0);
            }

            txtTotalEarning.setText(getDockActivity().getResources().getString(R.string.QAR)+ " " + amount + "");

            if (subEntity.getStatus().equals("1") || subEntity.getStatus().equals("2")) {
                txtStatus.setText(AppConstants.Technician_Assigned);
            } else if (subEntity.getStatus().equals("0")) {
                txtStatus.setText(AppConstants.Technician_Not_Assigned);
            } else if (subEntity.getStatus().equals("4")) {
                txtStatus.setText(AppConstants.Cancelled);
            } else {
                txtStatus.setText(AppConstants.Completed);
            }

        }
    }


}
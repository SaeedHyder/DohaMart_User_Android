package com.ingic.ezhalbatek.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ingic.ezhalbatek.R;
import com.ingic.ezhalbatek.fragments.abstracts.BaseFragment;
import com.ingic.ezhalbatek.ui.views.AnyTextView;
import com.ingic.ezhalbatek.ui.views.CustomRatingBar;
import com.ingic.ezhalbatek.ui.views.TitleBar;

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

    public static JobReportFragment newInstance() {
        Bundle args = new Bundle();

        JobReportFragment fragment = new JobReportFragment();
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

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
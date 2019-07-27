package com.ingic.ezhalbatek.fragments;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.ingic.ezhalbatek.R;
import com.ingic.ezhalbatek.entities.ServiceStatus.AdditionalJob;
import com.ingic.ezhalbatek.entities.getAdditionalJobsEnt;
import com.ingic.ezhalbatek.fragments.abstracts.BaseFragment;
import com.ingic.ezhalbatek.global.AppConstants;
import com.ingic.ezhalbatek.helpers.UIHelper;
import com.ingic.ezhalbatek.ui.binders.Job_Acknw_Binder;
import com.ingic.ezhalbatek.ui.views.AnyTextView;
import com.ingic.ezhalbatek.ui.views.CustomRatingBar;
import com.ingic.ezhalbatek.ui.views.CustomRecyclerView;
import com.ingic.ezhalbatek.ui.views.TitleBar;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.ingic.ezhalbatek.global.WebServiceConstants.AcceptRejectAdditionalJob;
import static com.ingic.ezhalbatek.global.WebServiceConstants.GETADDITONALJOBS;

public class AdditionalJobAcknowledFragment extends BaseFragment {


    private static String ActionId;
    private static String JobStatus;


    ArrayList<getAdditionalJobsEnt> response;
    @BindView(R.id.txtDuration)
    AnyTextView txtDuration;
    @BindView(R.id.txtJobsHead)
    AnyTextView txtJobsHead;
    @BindView(R.id.txt_no_data)
    AnyTextView txtNoData;
    @BindView(R.id.rv_jobs)
    CustomRecyclerView rvJobs;
    @BindView(R.id.txtJobs)
    AnyTextView txtJobs;
    @BindView(R.id.txtTechnicianName)
    AnyTextView txtTechnicianName;
    @BindView(R.id.txtTechnicianNumber)
    AnyTextView txtTechnicianNumber;
    @BindView(R.id.rbRating)
    CustomRatingBar rbRating;
    @BindView(R.id.Container_rate)
    LinearLayout ContainerRate;
    @BindView(R.id.btnAccept)
    Button btnAccept;
    @BindView(R.id.btnReject)
    Button btnReject;
    @BindView(R.id.mainFrame)
    LinearLayout mainFrame;
    @BindView(R.id.ll_buttons)
    LinearLayout llButtons;
    @BindView(R.id.btnStatus)
    AnyTextView btnStatus;

    Unbinder unbinder;
    @BindView(R.id.txtTotalEarning)
    AnyTextView txtTotalEarning;

    private ArrayList<getAdditionalJobsEnt> collection = new ArrayList<>();
    private ArrayList<String> additionalJobsId = new ArrayList<>();
    private Double amount = 0.0;

    public static AdditionalJobAcknowledFragment newInstance(String id) {
        Bundle args = new Bundle();
        ActionId = id;
        JobStatus=null;
        AdditionalJobAcknowledFragment fragment = new AdditionalJobAcknowledFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public static AdditionalJobAcknowledFragment newInstance(String id, String status) {
        Bundle args = new Bundle();
        ActionId = id;
        JobStatus = status;
        AdditionalJobAcknowledFragment fragment = new AdditionalJobAcknowledFragment();
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
        View view = inflater.inflate(R.layout.fragment_additional_job_acknow, container, false);

        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mainFrame.setVisibility(View.GONE);
        rvJobs.setNestedScrollingEnabled(false);

        if (JobStatus != null) {
            llButtons.setVisibility(View.GONE);
            btnStatus.setText(JobStatus + "");
            btnStatus.setVisibility(View.VISIBLE);
        }

        serviceHelper.enqueueCall(webService.getAdditionalJobs(prefHelper.getUser().getId(), ActionId), GETADDITONALJOBS);

    }

    @Override
    public void ResponseSuccess(Object result, String Tag, String message) {
        super.ResponseSuccess(result, Tag, message);
        switch (Tag) {
            case GETADDITONALJOBS:
                mainFrame.setVisibility(View.VISIBLE);
                response = (ArrayList<getAdditionalJobsEnt>) result;
                setData(response);
                break;

            case AcceptRejectAdditionalJob:
                UIHelper.showShortToastInCenter(getDockActivity(), message);
                getDockActivity().popFragment();
                break;
        }
    }

    private void setData(ArrayList<getAdditionalJobsEnt> data) {

        collection = new ArrayList<>();
        additionalJobsId = new ArrayList<>();
        if (JobStatus == null) {
            for (getAdditionalJobsEnt item : data) {
                if (item.getStatus() == 1) {
                    collection.add(item);
                    additionalJobsId.add(item.getId() + "");
                }
            }
        } else if (JobStatus != null && JobStatus.equals(AppConstants.ACCEPTED)) {
            for (getAdditionalJobsEnt item : data) {
                if (item.getStatus() == 2) {
                    collection.add(item);
                }
            }
        } else {
            for (getAdditionalJobsEnt item : data) {
                if (item.getStatus() == 3) {
                    collection.add(item);
                }
            }
        }

        amount = 0.0;
        if (collection.size() > 0) {
            txtJobsHead.setVisibility(View.VISIBLE);
            txtNoData.setVisibility(View.GONE);
            rvJobs.setVisibility(View.VISIBLE);

            for (getAdditionalJobsEnt item : collection) {
                amount = amount + (Double.parseDouble(item.getItem().getAmount()) * item.getQuantity());
            }

            rvJobs.bindRecyclerView(new Job_Acknw_Binder(prefHelper), collection, new LinearLayoutManager(getDockActivity(), LinearLayoutManager.VERTICAL, false), new DefaultItemAnimator());

        } else {
            txtJobsHead.setVisibility(View.GONE);
            rvJobs.setVisibility(View.GONE);
            txtNoData.setVisibility(View.VISIBLE);
        }

        txtTotalEarning.setText(getDockActivity().getResources().getString(R.string.QAR) + " " + amount + "");


    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
        titleBar.showBackButton();
        titleBar.setSubHeading(getResString(R.string.additional_job));
    }

    @OnClick({R.id.btnAccept, R.id.btnReject})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnAccept:

                String additionalJobIds = android.text.TextUtils.join(",", additionalJobsId);
                dialogHelper.showCommonDialog(v -> {
                    if (response != null && response.get(0).getRequestJobId() != null && !response.get(0).getRequestJobId().equals("")) {
                        serviceHelper.enqueueCall(webService.AcceptAdditionalJobTask(prefHelper.getUser().getId(), response.get(0).getRequestJobId() + "",additionalJobIds), AcceptRejectAdditionalJob);
                    } else {
                        serviceHelper.enqueueCall(webService.AcceptAdditionalJobSubscription(prefHelper.getUser().getId(), response.get(0).getUserSubsVisitId() + "",additionalJobIds), AcceptRejectAdditionalJob);
                    }
                    dialogHelper.hideDialog();
                }, R.string.additional_job, R.string.acknoowledge_additional_job, R.string.yes, R.string.no, true, true);
                dialogHelper.showDialog();
                dialogHelper.setCancelable(false);


                break;
            case R.id.btnReject:
                String additionalJobId = android.text.TextUtils.join(",", additionalJobsId);
                dialogHelper.showCommonDialog(v -> {
                    if (response != null && response.get(0).getRequestJobId() != null && !response.get(0).getRequestJobId().equals("")) {
                        serviceHelper.enqueueCall(webService.RejectAdditionalJobTask(prefHelper.getUser().getId(), response.get(0).getRequestJobId() + "",additionalJobId), AcceptRejectAdditionalJob);
                    } else {
                        serviceHelper.enqueueCall(webService.RejectAdditionalJobSubscription(prefHelper.getUser().getId(), response.get(0).getUserSubsVisitId() + "",additionalJobId), AcceptRejectAdditionalJob);
                    }
                    dialogHelper.hideDialog();
                }, R.string.additional_job, R.string.acknoowledge_additional_job_reject, R.string.yes, R.string.no, true, true);
                dialogHelper.showDialog();
                dialogHelper.setCancelable(false);


                break;
        }
    }


}

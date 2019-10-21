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
import com.ingic.ezhalbatek.entities.JobDone.JobDoneSubscriptionEnt;
import com.ingic.ezhalbatek.entities.JobDone.JobDoneTaskEnt;
import com.ingic.ezhalbatek.entities.JobDone.ServicsList;
import com.ingic.ezhalbatek.entities.JobDone.SubscriptionPaymentEnt;
import com.ingic.ezhalbatek.entities.JobDone.UserPaymentEnt;
import com.ingic.ezhalbatek.entities.ServiceStatus.AdditionalJob;
import com.ingic.ezhalbatek.fragments.abstracts.BaseFragment;
import com.ingic.ezhalbatek.global.AppConstants;
import com.ingic.ezhalbatek.global.WebServiceConstants;
import com.ingic.ezhalbatek.ui.binders.AdditionalJobDoneBinder;
import com.ingic.ezhalbatek.ui.binders.JobDoneBinder;
import com.ingic.ezhalbatek.ui.views.AnyTextView;
import com.ingic.ezhalbatek.ui.views.CustomRatingBar;
import com.ingic.ezhalbatek.ui.views.CustomRecyclerView;
import com.ingic.ezhalbatek.ui.views.TitleBar;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.ingic.ezhalbatek.global.WebServiceConstants.JobDone;
import static com.ingic.ezhalbatek.global.WebServiceConstants.RequestDetail;
import static com.ingic.ezhalbatek.global.WebServiceConstants.VisitDetail;
import static com.ingic.ezhalbatek.global.WebServiceConstants.VisitDone;

public class JobDoneAcknowledgeFragment extends BaseFragment {
    @BindView(R.id.txtDuration)
    AnyTextView txtDuration;
    @BindView(R.id.txtJobsHead)
    AnyTextView txtJobsHead;
    @BindView(R.id.No_Job_Found)
    AnyTextView NoJobFound;
    @BindView(R.id.rv_jobs)
    CustomRecyclerView rvJobs;
    @BindView(R.id.txtAdditionalJobsHead)
    AnyTextView txtAdditionalJobsHead;
    @BindView(R.id.No_Additional_Job)
    AnyTextView NoAdditionalJob;
    @BindView(R.id.rv_additional_jobs)
    CustomRecyclerView rvAdditionalJobs;
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
    Unbinder unbinder;

    private static String actionId;
    private static boolean isVisitDone = false;
    private static boolean isCompleted = false;
    @BindView(R.id.ll_jobtitle)
    LinearLayout llJobtitle;
    @BindView(R.id.ll_jobs)
    LinearLayout llJobs;
    @BindView(R.id.ll_additional_job)
    LinearLayout llAdditionalJob;
    @BindView(R.id.ll_buttons)
    LinearLayout llButtons;
    @BindView(R.id.txtTotalEarning)
    AnyTextView txtTotalEarning;
    @BindView(R.id.ll_total)
    LinearLayout llTotal;
    @BindView(R.id.txtStatus)
    AnyTextView txtStatus;
    @BindView(R.id.txtExtraEarning)
    AnyTextView txtExtraEarning;
    @BindView(R.id.ll_extra_cost)
    LinearLayout llExtraCost;
    private Double amount = 0.0;

    private ArrayList<AdditionalJob> collection = new ArrayList<>();


    public static JobDoneAcknowledgeFragment newInstance(String id, boolean isVisit, boolean completed) {
        Bundle args = new Bundle();
        actionId = id;
        isVisitDone = isVisit;
        isCompleted = completed;
        JobDoneAcknowledgeFragment fragment = new JobDoneAcknowledgeFragment();
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
        View view = inflater.inflate(R.layout.fragment_job_done_acknowledge, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (prefHelper.isLanguageArabian()) {
            view.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);

        } else {
            view.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
        }

        mainFrame.setVisibility(View.GONE);
        amount = 0.0;
        if (isCompleted) {
            llButtons.setVisibility(View.GONE);
            ContainerRate.setVisibility(View.VISIBLE);
        }
        if (isVisitDone) {
            serviceHelper.enqueueCall(webService.getVisitDetail(actionId + ""), WebServiceConstants.VisitDetail);
        } else {
            serviceHelper.enqueueCall(webService.getRequestDetail(prefHelper.getUser().getId(), actionId + ""), RequestDetail);
        }

    }


    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
        titleBar.showBackButton();
        titleBar.setSubHeading(getResString(R.string.job_done_acknowledge));
    }

    @Override
    public void ResponseSuccess(Object result, String Tag, String message) {
        super.ResponseSuccess(result, Tag, message);
        switch (Tag) {
            case RequestDetail:
                mainFrame.setVisibility(View.VISIBLE);
                UserPaymentEnt requestData = (UserPaymentEnt) result;
                setRequestData(requestData);
                break;
            case VisitDetail:
                mainFrame.setVisibility(View.VISIBLE);
                SubscriptionPaymentEnt visitData = (SubscriptionPaymentEnt) result;
                setVisitData(visitData);
                break;

            case VisitDone:
                JobDoneSubscriptionEnt VisitData = (JobDoneSubscriptionEnt) result;
                getDockActivity().popBackStackTillEntry(0);
                getDockActivity().replaceDockableFragment(RateJobDone.newInstance(VisitData), RateJobDone.TAG);
                break;

            case JobDone:
                JobDoneTaskEnt JobData = (JobDoneTaskEnt) result;
                getDockActivity().popBackStackTillEntry(0);
                getDockActivity().replaceDockableFragment(RateJobDone.newInstance(JobData), RateJobDone.TAG);
                break;


        }
    }

    private void setRequestData(UserPaymentEnt requestData) {
        if (requestData != null) {
            txtDuration.setText(requestData.getJobTitle() + "");
            if (requestData.getAssignTechnician() != null && requestData.getAssignTechnician().getTechnicianDetails() != null) {
                txtTechnicianName.setText(requestData.getAssignTechnician().getTechnicianDetails().getFullName() != null ? requestData.getAssignTechnician().getTechnicianDetails().getFullName() : "-");
                txtTechnicianNumber.setText(requestData.getAssignTechnician().getTechnicianDetails().getPhoneNo() != null ? requestData.getAssignTechnician().getTechnicianDetails().getPhoneNo() : "-");
            }

            if (requestData.getServicsList() != null && requestData.getServicsList().size() > 0) {
                llJobs.setVisibility(View.VISIBLE);

                for (ServicsList item : requestData.getServicsList()) {
                    amount = amount + (Double.parseDouble(item.getServiceDetail().getAmount()));
                }

                rvJobs.bindRecyclerView(new JobDoneBinder(prefHelper), requestData.getServicsList(), new LinearLayoutManager(getDockActivity(), LinearLayoutManager.VERTICAL, false), new DefaultItemAnimator());

            } else {
                llJobs.setVisibility(View.GONE);
            }

            collection = new ArrayList<>();
            if (requestData.getAdditionalJobs() != null && requestData.getAdditionalJobs().size() > 0) {
                for (AdditionalJob item : requestData.getAdditionalJobs()) {
                    if (item.getStatus() == 2) {
                        collection.add(item);
                    }
                }
                llAdditionalJob.setVisibility(View.VISIBLE);
                for (AdditionalJob item : collection) {
                    amount = amount + (Double.parseDouble(item.getItem().getAmount()) * item.getQuantity());
                }
                rvAdditionalJobs.bindRecyclerView(new AdditionalJobDoneBinder(prefHelper), collection, new LinearLayoutManager(getDockActivity(), LinearLayoutManager.VERTICAL, false), new DefaultItemAnimator());

            }
            if (collection.size() <= 0) {
                llAdditionalJob.setVisibility(View.GONE);
            }

            if (requestData.getFeedback() != null) {
                rbRating.setScore(Float.parseFloat(requestData.getFeedback().getRate()));
            } else {
                rbRating.setScore(0);
            }
            llExtraCost.setVisibility(View.VISIBLE);
            txtExtraEarning.setText(requestData.getUrgentCost() != null ?getDockActivity().getResources().getString(R.string.QAR) + " " + requestData.getUrgentCost() + "" : "-");
            double totalAmount = requestData.getTotalAmount() + requestData.getUrgentCost();
            txtTotalEarning.setText(getDockActivity().getResources().getString(R.string.QAR) + " " + totalAmount + "");

            // txtTotalEarning.setText("QAR" + " " + amount + "");

            if (requestData.getStatus() == 1 || requestData.getStatus() == 2) {
                txtStatus.setText(AppConstants.Technician_Assigned);
            } else if (requestData.getStatus() == 0) {
                txtStatus.setText(AppConstants.Technician_Not_Assigned);
            } else if (requestData.getStatus() == 4) {
                txtStatus.setText(AppConstants.Cancelled);
            } else {
                txtStatus.setText(AppConstants.Completed);
            }

        }
    }

    private void setVisitData(SubscriptionPaymentEnt visitData) {


        if (visitData != null) {
            llJobtitle.setVisibility(View.GONE);
            llJobs.setVisibility(View.GONE);
            if (visitData.getTechnician() != null && visitData.getTechnician() != null) {
                txtTechnicianName.setText(visitData.getTechnician().getFullName() != null ? visitData.getTechnician().getFullName() : "-");
                txtTechnicianNumber.setText(visitData.getTechnician().getPhoneNo() != null ? visitData.getTechnician().getPhoneNo() : "-");
            }

            collection = new ArrayList<>();
            if (visitData.getAdditionalJobs() != null && visitData.getAdditionalJobs().size() > 0) {
                llAdditionalJob.setVisibility(View.VISIBLE);
                for (AdditionalJob item : visitData.getAdditionalJobs()) {
                    if (item.getStatus() == 2) {
                        collection.add(item);
                    }
                }
                for (AdditionalJob item : collection) {
                    amount = amount + (Double.parseDouble(item.getItem().getAmount()) * item.getQuantity());
                }

                rvAdditionalJobs.bindRecyclerView(new AdditionalJobDoneBinder(prefHelper), collection, new LinearLayoutManager(getDockActivity(), LinearLayoutManager.VERTICAL, false), new DefaultItemAnimator());

            }
            if (collection.size() <= 0) {
                llTotal.setVisibility(View.GONE);
                llAdditionalJob.setVisibility(View.GONE);
            }
            if (visitData.getFeedback() != null) {
                rbRating.setScore(Float.parseFloat(visitData.getFeedback().getRate()));
            } else {
                rbRating.setScore(0);
            }
            txtTotalEarning.setText(getDockActivity().getResources().getString(R.string.QAR) + " " + amount + "");

            if (visitData.getStatus().equals("1") || visitData.getStatus().equals("2")) {
                txtStatus.setText(AppConstants.Technician_Assigned);
            } else if (visitData.getStatus().equals("0")) {
                txtStatus.setText(AppConstants.Technician_Not_Assigned);
            } else if (visitData.getStatus().equals("4")) {
                txtStatus.setText(AppConstants.Cancelled);
            } else {
                txtStatus.setText(AppConstants.Completed);
            }

        }
    }


    @OnClick({R.id.btnAccept})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnAccept:
                if (isVisitDone) {
                    dialogHelper.showCommonDialog(v -> {

                        serviceHelper.enqueueCall(webService.VisitDone(prefHelper.getUser().getId(), actionId + ""), VisitDone);
                        dialogHelper.hideDialog();
                    }, R.string.acknoowledge_job, R.string.acknoowledge_job_done, R.string.yes, R.string.no, true, true);
                    dialogHelper.showDialog();
                    dialogHelper.setCancelable(false);

                } else {
                    dialogHelper.showCommonDialog(v -> {

                        serviceHelper.enqueueCall(webService.JobDone(prefHelper.getUser().getId(), actionId + ""), JobDone);
                        dialogHelper.hideDialog();
                    }, R.string.acknoowledge_job, R.string.acknoowledge_job_done, R.string.yes, R.string.no, true, true);
                    dialogHelper.showDialog();
                    dialogHelper.setCancelable(false);

                }

                break;

        }
    }


}

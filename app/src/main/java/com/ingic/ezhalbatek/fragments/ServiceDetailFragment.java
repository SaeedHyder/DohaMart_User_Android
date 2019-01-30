package com.ingic.ezhalbatek.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.ingic.ezhalbatek.R;
import com.ingic.ezhalbatek.entities.ServiceStatus.AdditionalJob;
import com.ingic.ezhalbatek.entities.ServiceStatus.Service;
import com.ingic.ezhalbatek.entities.ServicsList;
import com.ingic.ezhalbatek.fragments.abstracts.BaseFragment;
import com.ingic.ezhalbatek.global.AppConstants;
import com.ingic.ezhalbatek.helpers.UIHelper;
import com.ingic.ezhalbatek.ui.binders.AdditionalJobDoneBinder;
import com.ingic.ezhalbatek.ui.binders.JobsBinder;
import com.ingic.ezhalbatek.ui.views.AnyTextView;
import com.ingic.ezhalbatek.ui.views.CustomRatingBar;
import com.ingic.ezhalbatek.ui.views.CustomRecyclerView;
import com.ingic.ezhalbatek.ui.views.TitleBar;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.ingic.ezhalbatek.global.WebServiceConstants.CANCELREQUEST;

/**
 * Created on 6/6/18.
 */
public class ServiceDetailFragment extends BaseFragment {
    public static final String TAG = "ServiceDetailFragment";
    @BindView(R.id.txtDuration)
    AnyTextView txtDuration;
    @BindView(R.id.txtJobs)
    AnyTextView txtJobs;
    @BindView(R.id.txtAdditionalNote)
    AnyTextView txtAdditionalNote;
    @BindView(R.id.txtTechnicianName)
    AnyTextView txtTechnicianName;
    @BindView(R.id.txtTechnicianNumber)
    AnyTextView txtTechnicianNumber;
    @BindView(R.id.rbRating)
    CustomRatingBar rbRating;
    @BindView(R.id.Container_rate)
    LinearLayout ContainerRate;
    @BindView(R.id.btnCall)
    Button btnCall;
    @BindView(R.id.btnCancel)
    Button btnCancel;
    @BindView(R.id.ContainerButtons)
    LinearLayout ContainerButtons;
    Unbinder unbinder;
    @BindView(R.id.rv_jobs)
    CustomRecyclerView rvJobs;
    @BindView(R.id.txtAdditionalJobsHead)
    AnyTextView txtAdditionalJobsHead;
    @BindView(R.id.No_Additional_Job)
    AnyTextView NoAdditionalJob;
    @BindView(R.id.rv_additional_jobs)
    CustomRecyclerView rvAdditionalJobs;
    @BindView(R.id.ll_additional_job)
    LinearLayout llAdditionalJob;
    @BindView(R.id.txtTotalEarning)
    AnyTextView txtTotalEarning;
    @BindView(R.id.ll_total)
    LinearLayout llTotal;
    @BindView(R.id.txtExtraEarning)
    AnyTextView txtExtraEarning;
    @BindView(R.id.ll_extra_cost)
    LinearLayout llExtraCost;
    @BindView(R.id.txtStatus)
    AnyTextView txtStatus;
    private boolean isJobCompleted;
    @BindView(R.id.txtJobsHead)
    AnyTextView txtJobsHead;
    private Double amount = 0.0;

    private ArrayList<AdditionalJob> collection = new ArrayList<>();

    private static String serviceEntKey = "serviceEntKey";
    private Service entity;

    public static ServiceDetailFragment newInstance(boolean isJobCompleted, Service serviceEnt) {
        Bundle args = new Bundle();
        args.putString(serviceEntKey, new Gson().toJson(serviceEnt));
        ServiceDetailFragment fragment = new ServiceDetailFragment();
        fragment.setArguments(args);
        fragment.setJobCompleted(isJobCompleted);
        return fragment;
    }

    public void setJobCompleted(boolean jobCompleted) {
        isJobCompleted = jobCompleted;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            String jsonString = getArguments().getString(serviceEntKey);

            if (jsonString != null) {
                entity = new Gson().fromJson(jsonString, Service.class);
            }
        }

    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
        titleBar.showBackButton();
        titleBar.setSubHeading(getResString(R.string.service_details));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_service_detail, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        amount = 0.0;
        ContainerButtons.setVisibility(isJobCompleted ? View.GONE : View.VISIBLE);
        ContainerRate.setVisibility(isJobCompleted ? View.VISIBLE : View.GONE);

        setData();
    }

    private void setData() {

        if (entity != null) {
            txtDuration.setText(entity.getJobTitle() + "");
            txtAdditionalNote.setText(entity.getDescription() + "");

            if (entity.getAssignTechnician() != null) {
                txtTechnicianName.setText(entity.getAssignTechnician().getTechnicianDetails().getFullName() != null ? entity.getAssignTechnician().getTechnicianDetails().getFullName() : "-");

                if (entity.getAssignTechnician().getTechnicianDetails().getPhoneNo() != null && !entity.getAssignTechnician().getTechnicianDetails().getPhoneNo().equals("")) {
                    if (entity.getAssignTechnician().getTechnicianDetails().getCountryCode() != null && !entity.getAssignTechnician().getTechnicianDetails().getCountryCode().equals("")) {
                        txtTechnicianNumber.setText(entity.getAssignTechnician().getTechnicianDetails().getCountryCode() + entity.getAssignTechnician().getTechnicianDetails().getPhoneNo());

                    } else {
                        txtTechnicianNumber.setText(entity.getAssignTechnician().getTechnicianDetails().getPhoneNo());
                    }

                } else {
                    txtTechnicianNumber.setText("N/A");
                }

            } else {
                txtTechnicianName.setText("N/A");
                txtTechnicianNumber.setText("N/A");
                btnCall.setAlpha((float) 0.6);
            }

            if (entity.getServicsList().size() > 0) {
                txtJobsHead.setVisibility(View.VISIBLE);
                for (ServicsList item : entity.getServicsList()) {
                    amount = amount + (Double.parseDouble(item.getServiceDetail().getAmount()));
                }
                rvJobs.bindRecyclerView(new JobsBinder(prefHelper), entity.getServicsList(), new LinearLayoutManager(getDockActivity(), LinearLayoutManager.VERTICAL, false), new DefaultItemAnimator());

            } else {
                txtJobsHead.setVisibility(View.GONE);
                rvJobs.setVisibility(View.GONE);
            }

            if (entity.getFeedback() != null) {
                rbRating.setScore(Float.parseFloat(entity.getFeedback().getRate()));
            }

            if (entity.getAdditionalJobs() != null && entity.getAdditionalJobs().size() > 0) {
                llAdditionalJob.setVisibility(View.VISIBLE);

                for (AdditionalJob item : entity.getAdditionalJobs()) {
                    if (item.getStatus() == 2) {
                        collection.add(item);
                        amount = amount + (Double.parseDouble(item.getItem().getAmount()) * item.getQuantity());
                    }
                }

                rvAdditionalJobs.bindRecyclerView(new AdditionalJobDoneBinder(prefHelper), collection, new LinearLayoutManager(getDockActivity(), LinearLayoutManager.VERTICAL, false), new DefaultItemAnimator());
            }
            if (collection.size() <= 0) {
                llAdditionalJob.setVisibility(View.GONE);
            }


            //txtTotalEarning.setText("AED" + " " + amount + "");
            txtExtraEarning.setText("AED" + " " + entity.getUrgentCost() + "");
            double totalAmount=entity.getTotalAmount()+entity.getUrgentCost();
            txtTotalEarning.setText("AED" + " " + totalAmount+ "");

            if (entity.getStatus() == 1 || entity.getStatus() == 2) {
                txtStatus.setText(AppConstants.Technician_Assigned);
            } else if (entity.getStatus() == 0) {
                txtStatus.setText(AppConstants.Technician_Not_Assigned);
            } else if (entity.getStatus() == 4) {
                txtStatus.setText(AppConstants.Cancelled);
            } else {
                txtStatus.setText(AppConstants.Completed);
            }
        }
    }


    @OnClick({R.id.btnCall, R.id.btnCancel})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnCall:
                if (entity.getAssignTechnician() != null) {
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    if (entity != null && entity.getAssignTechnician() != null && entity.getAssignTechnician().getTechnicianDetails().getPhoneNo() != null && !entity.getAssignTechnician().getTechnicianDetails().getPhoneNo().equals("")) {
                        if (entity.getAssignTechnician().getTechnicianDetails().getCountryCode() != null && !entity.getAssignTechnician().getTechnicianDetails().getCountryCode().equals("")) {
                            intent.setData(Uri.parse("tel:" + entity.getAssignTechnician().getTechnicianDetails().getCountryCode() + entity.getAssignTechnician().getTechnicianDetails().getPhoneNo()));
                        } else {
                            intent.setData(Uri.parse("tel:" + entity.getAssignTechnician().getTechnicianDetails().getPhoneNo()));
                        }

                    } else {
                        intent.setData(Uri.parse("tel:0123456789"));
                    }
                    startActivity(intent);
                } else {
                    UIHelper.showShortToastInCenter(getDockActivity(), "Technician is not assigned");
                }
                getMainActivity().closeDrawer();
                break;
            case R.id.btnCancel:
                dialogHelper.showCommonDialog(v -> {
                    serviceHelper.enqueueCall(webService.cancelRequest(entity.getId() + "", prefHelper.getUser().getId() + ""), CANCELREQUEST);
                    dialogHelper.hideDialog();
                }, R.string.cancel_Job, R.string.cancel_message, R.string.yes, R.string.no, true, true);
                dialogHelper.setCancelable(true);
                dialogHelper.showDialog();
                break;
        }
    }

    @Override
    public void ResponseSuccess(Object result, String Tag, String message) {
        super.ResponseSuccess(result, Tag, message);
        switch (Tag) {
            case CANCELREQUEST:
                getDockActivity().popBackStackTillEntry(0);
                getDockActivity().replaceDockableFragment(HomeFragment.newInstance(), HomeFragment.TAG);
                break;
        }
    }

}
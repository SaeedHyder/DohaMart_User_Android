package com.ingic.ezhalbatek.fragments;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.telephony.ServiceState;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.ingic.ezhalbatek.R;
import com.ingic.ezhalbatek.entities.ServiceStatus.Service;
import com.ingic.ezhalbatek.entities.ServiceStatus.ServicesStatus;
import com.ingic.ezhalbatek.fragments.abstracts.BaseFragment;
import com.ingic.ezhalbatek.global.AppConstants;
import com.ingic.ezhalbatek.global.WebServiceConstants;
import com.ingic.ezhalbatek.ui.binders.PendingServiceBinder;
import com.ingic.ezhalbatek.ui.views.AnyTextView;
import com.ingic.ezhalbatek.ui.views.TitleBar;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.ingic.ezhalbatek.global.WebServiceConstants.SERVICESTATUS;


public class MyServicesFragment extends BaseFragment {
    public static final String TAG = "MyServicesFragment";
    @BindView(R.id.txtPendingJobCount)
    AnyTextView txtPendingJobCount;
    @BindView(R.id.txtPendingText)
    AnyTextView txtPendingText;
    @BindView(R.id.btnPendingJobs)
    LinearLayout btnPendingJobs;
    @BindView(R.id.txtCompleteCount)
    AnyTextView txtCompleteCount;
    @BindView(R.id.txtCompleteText)
    AnyTextView txtCompleteText;
    @BindView(R.id.btnCompleteJobs)
    LinearLayout btnCompleteJobs;
    @BindView(R.id.fragmentContainer)
    LinearLayout fragmentContainer;
    @BindView(R.id.ll_mainFrame)
    LinearLayout llMainFrame;

    private ServicesStatus servicesStatus;
    private ArrayList<Service> pending;
    private ArrayList<Service> completed;

    public static MyServicesFragment newInstance() {
        Bundle args = new Bundle();

        MyServicesFragment fragment = new MyServicesFragment();
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
        View view = inflater.inflate(R.layout.fragment_my_services, container, false);

        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
        titleBar.showBackButton();
        titleBar.setSubHeading(getResString(R.string.service_status));
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (prefHelper.isLanguageArabian()) {
            view.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);

        } else {
            view.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
        }

        llMainFrame.setVisibility(View.GONE);
        serviceHelper.enqueueCall(webService.getServicesStatus(prefHelper.getUser().getId() + "", AppConstants.SERVICE), SERVICESTATUS);


    }

    @Override
    public void ResponseSuccess(Object result, String Tag, String message) {
        super.ResponseSuccess(result, Tag, message);
        switch (Tag) {
            case SERVICESTATUS:
                llMainFrame.setVisibility(View.VISIBLE);
                servicesStatus = (ServicesStatus) result;
                pending = new ArrayList<>();
                completed = new ArrayList<>();

                for (Service item : servicesStatus.getService()) {
                    if (item.getStatus() == 0 || item.getStatus() == 1 || item.getStatus() == 2) {
                        pending.add(item);
                    } else if (item.getStatus() == 3 || item.getStatus() == 4) {
                        completed.add(item);
                    }
                }

                txtPendingJobCount.setText(pending.size() + "");
                txtCompleteCount.setText(completed.size() + "");

                if (prefHelper.isFromPending()) {
                    changeColorToSelected(txtPendingJobCount, txtPendingText);
                    changeColorToUnSelected(txtCompleteCount, txtCompleteText);
                    replaceFragmentOnTab(PendingServicesFragment.newInstance(pending));
                } else {
                    changeColorToSelected(txtCompleteCount, txtCompleteText);
                    changeColorToUnSelected(txtPendingJobCount, txtPendingText);
                    replaceFragmentOnTab(CompleteServicesFragment.newInstance(completed));
                }

                break;
        }
    }


    private void changeColorToSelected(AnyTextView txtCount, AnyTextView txtTitle) {
        txtCount.setTextColor(getDockActivity().getResources().getColor(R.color.app_red));
        txtTitle.setTextColor(getDockActivity().getResources().getColor(R.color.app_red));
    }

    private void changeColorToUnSelected(AnyTextView txtCount, AnyTextView txtTitle) {
        txtCount.setTextColor(getDockActivity().getResources().getColor(R.color.app_font_black));
        txtTitle.setTextColor(getDockActivity().getResources().getColor(R.color.app_font_black));
    }

    private void replaceFragmentOnTab(BaseFragment frag) {
        try {
            FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
            transaction.replace(R.id.fragmentContainer, frag);
            transaction.commitAllowingStateLoss();
        }catch (Exception e){
            e.printStackTrace();
        }


    }


    @OnClick({R.id.btnPendingJobs, R.id.btnCompleteJobs})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnCompleteJobs:
                changeColorToSelected(txtCompleteCount, txtCompleteText);
                changeColorToUnSelected(txtPendingJobCount, txtPendingText);
                replaceFragmentOnTab(CompleteServicesFragment.newInstance(completed));
                break;
            case R.id.btnPendingJobs:
                changeColorToSelected(txtPendingJobCount, txtPendingText);
                changeColorToUnSelected(txtCompleteCount, txtCompleteText);
                replaceFragmentOnTab(PendingServicesFragment.newInstance(pending));
                break;
        }
    }


}
package com.ingic.ezhalbatek.fragments;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.ingic.ezhalbatek.R;
import com.ingic.ezhalbatek.entities.ServiceStatus.Service;
import com.ingic.ezhalbatek.entities.ServiceStatus.ServicesStatus;
import com.ingic.ezhalbatek.entities.ServiceStatus.Subscription;
import com.ingic.ezhalbatek.fragments.abstracts.BaseFragment;
import com.ingic.ezhalbatek.global.AppConstants;
import com.ingic.ezhalbatek.ui.views.AnyTextView;
import com.ingic.ezhalbatek.ui.views.TitleBar;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.ingic.ezhalbatek.global.WebServiceConstants.SERVICESTATUS;

/**
 * Created on 6/2/18.
 */
public class SubscriptionStatusFragment extends BaseFragment {
    public static final String TAG = "SubscriptionStatusFragment";
    @BindView(R.id.txtVisitCount)
    AnyTextView txtVisitCount;
    @BindView(R.id.txtVisitText)
    AnyTextView txtVisitText;
    @BindView(R.id.btnVisits)
    LinearLayout btnVisits;
    @BindView(R.id.txtInProgressJobCount)
    AnyTextView txtInProgressJobCount;
    @BindView(R.id.txtInProgressText)
    AnyTextView txtInProgressText;
    @BindView(R.id.btnInProgress)
    LinearLayout btnInProgress;
    @BindView(R.id.txtCompletedJobCount)
    AnyTextView txtCompletedJobCount;
    @BindView(R.id.txtCompletedText)
    AnyTextView txtCompletedText;
    @BindView(R.id.btnCompletedJobs)
    LinearLayout btnCompletedJobs;
    @BindView(R.id.fragmentContainer)
    LinearLayout fragmentContainer;
    @BindView(R.id.ll_mainFrame)
    LinearLayout llMainFrame;
    Unbinder unbinder;

    private ServicesStatus servicesStatus;
    private ArrayList<Subscription> visitCollection;
    private ArrayList<Subscription> pendingCollection;
    private ArrayList<Subscription> completedCollection;


    public static SubscriptionStatusFragment newInstance() {
        Bundle args = new Bundle();

        SubscriptionStatusFragment fragment = new SubscriptionStatusFragment();
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
        titleBar.setSubHeading(getResString(R.string.subscription_status));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_subscription_status, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        llMainFrame.setVisibility(View.GONE);
        serviceHelper.enqueueCall(webService.getServicesStatus(prefHelper.getUser().getId() + "", AppConstants.SUBSCRIPTION), SERVICESTATUS);


    }

    @Override
    public void ResponseSuccess(Object result, String Tag, String message) {
        super.ResponseSuccess(result, Tag, message);
        switch (Tag) {
            case SERVICESTATUS:
                llMainFrame.setVisibility(View.VISIBLE);
                servicesStatus = (ServicesStatus) result;
                visitCollection = new ArrayList<>();
                pendingCollection = new ArrayList<>();
                completedCollection = new ArrayList<>();

                for (Subscription item : servicesStatus.getSubscription()) {
                    if (item.getStatus().equals("0") || item.getStatus().equals("1") ) {
                        visitCollection.add(item);
                    } else if (item.getStatus().equals("5")) {
                        pendingCollection.add(item);
                    } else if (item.getStatus().equals("3")) {
                        completedCollection.add(item);
                    }
                }

                txtVisitCount.setText(visitCollection.size() + "");
                txtInProgressJobCount.setText(pendingCollection.size() + "");
                txtCompletedJobCount.setText(completedCollection.size() + "");

                if (prefHelper.isFromVisitSub()) {
                    changeColorToSelected(txtVisitCount, txtVisitText);
                    changeColorToUnSelected(txtInProgressJobCount, txtInProgressText);
                    changeColorToUnSelected(txtCompletedJobCount, txtCompletedText);
                    replaceFragmentOnTab(SubscriptionVisitFragment.newInstance(visitCollection));
                } else if (prefHelper.isFromInProgressSub()) {
                    changeColorToUnSelected(txtVisitCount, txtVisitText);
                    changeColorToSelected(txtInProgressJobCount, txtInProgressText);
                    changeColorToUnSelected(txtCompletedJobCount, txtCompletedText);
                    replaceFragmentOnTab(SubscriptionPendingFragment.newInstance(pendingCollection));
                } else if (prefHelper.isFromCompletedSub()) {
                    changeColorToUnSelected(txtVisitCount, txtVisitText);
                    changeColorToUnSelected(txtInProgressJobCount, txtInProgressText);
                    changeColorToSelected(txtCompletedJobCount, txtCompletedText);
                    replaceFragmentOnTab(SubscriptionCompleteFragment.newInstance(completedCollection));
                } else {
                    changeColorToSelected(txtVisitCount, txtVisitText);
                    changeColorToUnSelected(txtInProgressJobCount, txtInProgressText);
                    changeColorToUnSelected(txtCompletedJobCount, txtCompletedText);
                    replaceFragmentOnTab(SubscriptionVisitFragment.newInstance(visitCollection));
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
        FragmentTransaction transaction = getChildFragmentManager()
                .beginTransaction();
        transaction.replace(R.id.fragmentContainer, frag);
        transaction.commitAllowingStateLoss();

    }


    @OnClick({R.id.btnVisits, R.id.btnInProgress, R.id.btnCompletedJobs})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnVisits:
                changeColorToSelected(txtVisitCount, txtVisitText);
                changeColorToUnSelected(txtInProgressJobCount, txtInProgressText);
                changeColorToUnSelected(txtCompletedJobCount, txtCompletedText);
                replaceFragmentOnTab(SubscriptionVisitFragment.newInstance(visitCollection));
                break;
            case R.id.btnInProgress:
                changeColorToUnSelected(txtVisitCount, txtVisitText);
                changeColorToSelected(txtInProgressJobCount, txtInProgressText);
                changeColorToUnSelected(txtCompletedJobCount, txtCompletedText);
                replaceFragmentOnTab(SubscriptionPendingFragment.newInstance(pendingCollection));
                break;
            case R.id.btnCompletedJobs:
                changeColorToUnSelected(txtVisitCount, txtVisitText);
                changeColorToUnSelected(txtInProgressJobCount, txtInProgressText);
                changeColorToSelected(txtCompletedJobCount, txtCompletedText);
                replaceFragmentOnTab(SubscriptionCompleteFragment.newInstance(completedCollection));
                break;
        }
    }


}
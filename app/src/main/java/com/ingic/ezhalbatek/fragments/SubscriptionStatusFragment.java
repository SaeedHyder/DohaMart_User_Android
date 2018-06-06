package com.ingic.ezhalbatek.fragments;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.ingic.ezhalbatek.R;
import com.ingic.ezhalbatek.fragments.abstracts.BaseFragment;
import com.ingic.ezhalbatek.ui.views.AnyTextView;
import com.ingic.ezhalbatek.ui.views.TitleBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created on 6/2/18.
 */
public class SubscriptionStatusFragment extends BaseFragment {
    public static final String TAG = "SubscriptionStatusFragment";
    @BindView(R.id.txtCompleteCount)
    AnyTextView txtCompleteCount;
    @BindView(R.id.txtCompleteText)
    AnyTextView txtCompleteText;
    @BindView(R.id.btnCompleteJobs)
    LinearLayout btnCompleteJobs;
    @BindView(R.id.txtPendingJobCount)
    AnyTextView txtPendingJobCount;
    @BindView(R.id.txtPendingText)
    AnyTextView txtPendingText;
    @BindView(R.id.btnPendingJobs)
    LinearLayout btnPendingJobs;
    Unbinder unbinder;

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
        titleBar.setSubHeading(getResString(R.string.my_subscription));
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
        changeColorToSelected(txtPendingJobCount, txtPendingText);
        changeColorToUnSelected(txtCompleteCount, txtCompleteText);
        replaceFragmentOnTab(SubscriptionPendingFragment.newInstance());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
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
        transaction.commit();

    }

    @OnClick({R.id.btnCompleteJobs, R.id.btnPendingJobs})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnCompleteJobs:
                changeColorToSelected(txtCompleteCount, txtCompleteText);
                changeColorToUnSelected(txtPendingJobCount, txtPendingText);
                replaceFragmentOnTab(SubscriptionCompleteFragment.newInstance());
                break;
            case R.id.btnPendingJobs:
                changeColorToSelected(txtPendingJobCount, txtPendingText);
                changeColorToUnSelected(txtCompleteCount, txtCompleteText);
                replaceFragmentOnTab(SubscriptionPendingFragment.newInstance());
                break;
        }
    }
}
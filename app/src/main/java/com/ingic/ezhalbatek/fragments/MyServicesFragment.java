package com.ingic.ezhalbatek.fragments;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.ingic.ezhalbatek.R;
import com.ingic.ezhalbatek.fragments.abstracts.BaseFragment;
import com.ingic.ezhalbatek.ui.views.AnyTextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created on 6/6/18.
 */
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
    Unbinder unbinder;

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
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        changeColorToSelected(txtPendingJobCount, txtPendingText);
        changeColorToUnSelected(txtCompleteCount, txtCompleteText);
        replaceFragmentOnTab(PendingServicesFragment.newInstance());
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
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.btnPendingJobs, R.id.btnCompleteJobs})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnCompleteJobs:
                changeColorToSelected(txtCompleteCount, txtCompleteText);
                changeColorToUnSelected(txtPendingJobCount, txtPendingText);
                replaceFragmentOnTab(CompleteServicesFragment.newInstance());
                break;
            case R.id.btnPendingJobs:
                changeColorToSelected(txtPendingJobCount, txtPendingText);
                changeColorToUnSelected(txtCompleteCount, txtCompleteText);
                replaceFragmentOnTab(PendingServicesFragment.newInstance());
                break;
        }
    }
}
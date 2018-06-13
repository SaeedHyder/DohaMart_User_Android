package com.ingic.ezhalbatek.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.ingic.ezhalbatek.R;
import com.ingic.ezhalbatek.fragments.abstracts.BaseFragment;
import com.ingic.ezhalbatek.ui.views.AnyTextView;
import com.ingic.ezhalbatek.ui.views.CustomRatingBar;
import com.ingic.ezhalbatek.ui.views.TitleBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

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
    private boolean isJobCompleted;

    public static ServiceDetailFragment newInstance(boolean isJobCompleted) {
        Bundle args = new Bundle();

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
        ContainerButtons.setVisibility(isJobCompleted ? View.GONE : View.VISIBLE);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.btnCall, R.id.btnCancel})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnCall:
                willbeimplementedinBeta();
                break;
            case R.id.btnCancel:
                dialogHelper.showCommonDialog(v -> {
                    dialogHelper.hideDialog();
                    getDockActivity().popBackStackTillEntry(0);
                    getDockActivity().replaceDockableFragment(HomeFragment.newInstance(), HomeFragment.TAG);
                }, R.string.cancel_Job, R.string.cancel_message, R.string.yes, R.string.no, true, true);
                dialogHelper.setCancelable(true);
                dialogHelper.showDialog();
                break;
        }
    }
}
package com.ingic.ezhalbatek.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.gson.Gson;
import com.ingic.ezhalbatek.R;
import com.ingic.ezhalbatek.entities.FeedbackEnt;
import com.ingic.ezhalbatek.entities.JobDone.JobDoneSubscriptionEnt;
import com.ingic.ezhalbatek.entities.JobDone.JobDoneTaskEnt;
import com.ingic.ezhalbatek.fragments.abstracts.BaseFragment;
import com.ingic.ezhalbatek.helpers.UIHelper;
import com.ingic.ezhalbatek.ui.views.AnyEditTextView;
import com.ingic.ezhalbatek.ui.views.AnyTextView;
import com.ingic.ezhalbatek.ui.views.CustomRatingBar;
import com.ingic.ezhalbatek.ui.views.TitleBar;
import com.nostra13.universalimageloader.core.ImageLoader;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.ingic.ezhalbatek.global.WebServiceConstants.FEEDBACK;


public class RateJobDone extends BaseFragment {
    public static final String TAG = "RateJobDone";
    @BindView(R.id.imgProfilePic)
    CircleImageView imgProfilePic;
    @BindView(R.id.txtTechnicainName)
    AnyTextView txtTechnicainName;
    @BindView(R.id.rbRating)
    CustomRatingBar rbRating;
    @BindView(R.id.edtFeedback)
    AnyEditTextView edtFeedback;
    @BindView(R.id.btnConfirm)
    Button btnConfirm;
    Unbinder unbinder;


    private static String serviceEntKey = "serviceEntKey";
    private static String subscriptionEntKey = "subscriptionEntKey";
    private JobDoneTaskEnt serviceEntity;
    private JobDoneSubscriptionEnt subEntity;
    private ImageLoader imageLoader;

    public static RateJobDone newInstance() {
        Bundle args = new Bundle();

        RateJobDone fragment = new RateJobDone();
        fragment.setArguments(args);
        return fragment;
    }

    public static RateJobDone newInstance(JobDoneTaskEnt serviceEnt) {
        Bundle args = new Bundle();
        args.putString(serviceEntKey, new Gson().toJson(serviceEnt));
        RateJobDone fragment = new RateJobDone();
        fragment.setArguments(args);
        return fragment;
    }

    public static RateJobDone newInstance(JobDoneSubscriptionEnt subscriptionEnt) {
        Bundle args = new Bundle();
        args.putString(subscriptionEntKey, new Gson().toJson(subscriptionEnt));
        RateJobDone fragment = new RateJobDone();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        imageLoader = ImageLoader.getInstance();
        if (getArguments() != null) {
            String jsonString = getArguments().getString(serviceEntKey);
            String jsonSubString = getArguments().getString(subscriptionEntKey);

            if (jsonString != null) {
                serviceEntity = new Gson().fromJson(jsonString, JobDoneTaskEnt.class);
            }
            if (jsonSubString != null) {
                subEntity = new Gson().fromJson(jsonSubString, JobDoneSubscriptionEnt.class);
            }
        }


    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rate_technician, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
        titleBar.setSubHeading(getResString(R.string.rate_review));
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rbRating.setOnScoreChanged(new CustomRatingBar.IRatingBarCallbacks() {
            @Override
            public void scoreChanged(float score) {
                if (score < 1.0f)
                    rbRating.setScore(1.0f);
            }
        });

        if (serviceEntity != null) {
            imageLoader.displayImage(serviceEntity.getAssignTechnician().getTechnicianDetails().getProfileImage(), imgProfilePic);
            txtTechnicainName.setText(serviceEntity.getAssignTechnician().getTechnicianDetails().getFullName() != null ? serviceEntity.getAssignTechnician().getTechnicianDetails().getFullName() : "-");

        } else if (subEntity != null) {
            imageLoader.displayImage(subEntity.getTechnician().getProfileImage(), imgProfilePic);
            txtTechnicainName.setText(subEntity.getTechnician().getFullName() != null ? subEntity.getTechnician().getFullName() : "-");
        } else {
            imgProfilePic.setImageResource(R.drawable.image_placeholder);
            txtTechnicainName.setText("-");
        }

        if(getTitleBar()!=null){
            getTitleBar().showHideDeleteAllButton(false);
        }
    }

    private boolean isValidate() {
        if (edtFeedback.getText().toString().isEmpty() || edtFeedback.getText().toString().trim().equals("")) {
            edtFeedback.setError(getResString(R.string.write_feedback));
            return false;
        } else {
            return true;
        }
    }


    @OnClick(R.id.btnConfirm)
    public void onViewClicked() {

        if (isValidate()) {
            if (serviceEntity != null) {
                FeedbackEnt feedbackEnt = new FeedbackEnt(serviceEntity.getId() + "", null, prefHelper.getUser().getId() + "", serviceEntity.getAssignTechnician().getTechnicianId() + "", edtFeedback.getText().toString(), rbRating.getScore() + "");
                serviceHelper.enqueueCall(webService.feedback(feedbackEnt), FEEDBACK);

            } else if (subEntity != null) {
                FeedbackEnt feedbackEnt = new FeedbackEnt(null, subEntity.getId() + "", prefHelper.getUser().getId() + "", subEntity.getTechnicianId() + "", edtFeedback.getText().toString(), rbRating.getScore() + "");
                serviceHelper.enqueueCall(webService.feedback(feedbackEnt), FEEDBACK);

            } else {
                UIHelper.showShortToastInCenter(getDockActivity(), "will be implemented");
            }
        }
    }

    @Override
    public void ResponseSuccess(Object result, String Tag, String message) {
        super.ResponseSuccess(result, Tag, message);
        switch (Tag) {
            case FEEDBACK:
                UIHelper.showShortToastInCenter(getDockActivity(),getResString(R.string.feedback_submitted_succesfully));
                getDockActivity().popBackStackTillEntry(0);
                getDockActivity().replaceDockableFragment(HomeFragment.newInstance(), "HomeFragment");
                break;
        }
    }

    @Override
    public void ResponseFailureNoResonse(String tag) {
        super.ResponseFailureNoResonse(tag);
        switch (tag){
            case FEEDBACK:
                getDockActivity().popBackStackTillEntry(0);
                getDockActivity().replaceDockableFragment(HomeFragment.newInstance(), "HomeFragment");
                break;
        }
    }
}


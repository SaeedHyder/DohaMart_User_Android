package com.ingic.ezhalbatek.fragments;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.gson.Gson;
import com.ingic.ezhalbatek.R;
import com.ingic.ezhalbatek.entities.LoginModule.Subscription;
import com.ingic.ezhalbatek.entities.LoginModule.UserEnt;
import com.ingic.ezhalbatek.entities.SubscriptionsDetail;
import com.ingic.ezhalbatek.fragments.abstracts.BaseFragment;
import com.ingic.ezhalbatek.ui.binders.PackageTasksBinder;
import com.ingic.ezhalbatek.ui.views.AnyTextView;
import com.ingic.ezhalbatek.ui.views.CustomRecyclerView;
import com.ingic.ezhalbatek.ui.views.TitleBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.ingic.ezhalbatek.global.WebServiceConstants.PURCHASESUBSCRIPTION;

/**
 * Created on 5/22/18.
 */
public class PackageDetailFragment extends BaseFragment {
    public static final String TAG = "PackageDetailFragment";
    @BindView(R.id.txtDuration)
    AnyTextView txtDuration;
    @BindView(R.id.txtSubscriptionFee)
    AnyTextView txtSubscriptionFee;
    @BindView(R.id.txtCustomerName)
    AnyTextView txtCustomerName;
    @BindView(R.id.txtSubscriberID)
    AnyTextView txtSubscriberID;
    @BindView(R.id.txtAddress)
    AnyTextView txtAddress;
    @BindView(R.id.txtPhone)
    AnyTextView txtPhone;
    @BindView(R.id.txtEmail)
    AnyTextView txtEmail;
    @BindView(R.id.btnProceedPayment)
    Button btnProceedPayment;
    @BindView(R.id.btnUpdate)
    Button btnUpdate;
    Unbinder unbinder;
    @BindView(R.id.txtFeatures)
    AnyTextView txtFeatures;


    private static String SUBSCRIPTIONDETAIL = "SUBSCRIPTIONDETAIL";
    private static String Address;
    private static String Latitude;
    private static String Longitude;
    @BindView(R.id.rv_jobs)
    CustomRecyclerView rvJobs;
    @BindView(R.id.txtFacilities)
    AnyTextView txtFacilities;
    private SubscriptionsDetail subscriptionsDetail;

    public static PackageDetailFragment newInstance() {
        Bundle args = new Bundle();

        PackageDetailFragment fragment = new PackageDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public static PackageDetailFragment newInstance(SubscriptionsDetail data, String address, String lat, String lng) {
        Bundle args = new Bundle();
        args.putString(SUBSCRIPTIONDETAIL, new Gson().toJson(data));
        Address = address;
        Latitude = lat;
        Longitude = lng;
        PackageDetailFragment fragment = new PackageDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            String jsonString = getArguments().getString(SUBSCRIPTIONDETAIL);

            if (jsonString != null) {
                subscriptionsDetail = new Gson().fromJson(jsonString, SubscriptionsDetail.class);
            }
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_package_detail, container, false);
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

        if (subscriptionsDetail != null) {
            setData();
        } else {
            btnProceedPayment.setVisibility(View.GONE);
            btnUpdate.setVisibility(View.VISIBLE);
            setDataPreference();
        }


    }

    private void setDataPreference() {
        if (prefHelper.getUser().getUserSubscription() != null && prefHelper.getUser().getUserSubscription().getSubscription() != null) {
            Subscription entity = prefHelper.getUser().getUserSubscription().getSubscription();
            txtDuration.setText(entity.getSubscriptionDuration() + " Month("+ entity.getTitle()+")");
            txtSubscriptionFee.setText(getResString(R.string.QAR)+" " + entity.getAmount());
            txtSubscriberID.setText(entity.getId() + "");

            if (prefHelper.getUser() != null) {
                txtCustomerName.setText(prefHelper.getUser().getFullName() != null ? prefHelper.getUser().getFullName() : "-");
                txtPhone.setText(prefHelper.getUser().getPhoneNo() != null ? prefHelper.getUser().getCountryCode() + " " + prefHelper.getUser().getPhoneNo() : "-");
                txtEmail.setText(prefHelper.getUser().getEmail() != null ? prefHelper.getUser().getEmail() : "-");
                txtAddress.setText(prefHelper.getUser().getFullAddress() != null ? prefHelper.getUser().getFullAddress() : "-");

            }

            if (entity.getServices() != null && entity.getServices().size() > 0) {
                txtFacilities.setVisibility(View.VISIBLE);
                rvJobs.bindRecyclerView(new PackageTasksBinder(prefHelper), entity.getServices(), new LinearLayoutManager(getDockActivity(), LinearLayoutManager.VERTICAL, false), new DefaultItemAnimator());

            } else {
                txtFacilities.setVisibility(View.GONE);
                rvJobs.setVisibility(View.GONE);
            }
        }
    }

    private void setData() {
        if (subscriptionsDetail != null) {
            txtDuration.setText(subscriptionsDetail.getSubscriptionDuration() + " Months Package");
            txtSubscriptionFee.setText(getResString(R.string.QAR) + " " + subscriptionsDetail.getAmount());
            txtSubscriberID.setText(subscriptionsDetail.getId() + "");
            txtAddress.setText(Address != null ? Address : "-");
            if (prefHelper.getUser() != null) {
                txtCustomerName.setText(prefHelper.getUser().getFullName() != null ? prefHelper.getUser().getFullName() : "-");
                txtPhone.setText(prefHelper.getUser().getPhoneNo() != null ? prefHelper.getUser().getCountryCode() + " " + prefHelper.getUser().getPhoneNo() : "-");
                txtEmail.setText(prefHelper.getUser().getEmail() != null ? prefHelper.getUser().getEmail() : "-");

            }

            if (subscriptionsDetail.getServices() != null && subscriptionsDetail.getServices().size() > 0) {
                txtFacilities.setVisibility(View.VISIBLE);
                rvJobs.bindRecyclerView(new PackageTasksBinder(prefHelper), subscriptionsDetail.getServices(), new LinearLayoutManager(getDockActivity(), LinearLayoutManager.VERTICAL, false), new DefaultItemAnimator());

            } else {
                txtFacilities.setVisibility(View.GONE);
                rvJobs.setVisibility(View.GONE);
            }
        }

    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
        titleBar.showBackButton();
        titleBar.setSubHeading(getResString(R.string.package_detail));
    }

    @OnClick({R.id.btnProceedPayment, R.id.btnUpdate})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnProceedPayment:
                serviceHelper.enqueueCall(webService.subscriptionPurchase(subscriptionsDetail.getId() + "", prefHelper.getUser().getId() + "", Latitude, Longitude, Address), PURCHASESUBSCRIPTION);

                break;
            case R.id.btnUpdate:
                if (prefHelper.getUser().getUserSubscription() != null && prefHelper.getUser().getUserSubscription().getSubscription() != null) {
                    Subscription entity = prefHelper.getUser().getUserSubscription().getSubscription();
                    getDockActivity().replaceDockableFragment(SubscriptionsPackagesFragment.newInstance(entity.getCategoryId()), "SubscriptionsPackagesFragment");
                }
                break;
        }
    }


    @Override
    public void ResponseSuccess(Object result, String Tag, String message) {
        super.ResponseSuccess(result, Tag, message);
        switch (Tag) {
            case PURCHASESUBSCRIPTION:
                UserEnt userEnt = (UserEnt) result;
                prefHelper.putUser(userEnt);
                prefHelper.setLoginStatus(true);
                prefHelper.setGuestStatus(false);
                dialogHelper.showCommonDialog(v -> {
                    getDockActivity().popBackStackTillEntry(0);
                    getDockActivity().replaceDockableFragment(HomeFragment.newInstance(), "HomeFragment");
                    dialogHelper.hideDialog();
                }, R.string.empty, R.string.thankyou, R.string.ok, R.string.empty, false, false);
                dialogHelper.setCancelable(false);
                dialogHelper.showDialog();

                break;
        }
    }



}
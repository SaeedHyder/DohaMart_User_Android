package com.ingic.ezhalbatek.fragments;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.gson.Gson;
import com.ingic.ezhalbatek.R;
import com.ingic.ezhalbatek.entities.AllCategoriesEnt;
import com.ingic.ezhalbatek.entities.SubscriptionPackagesEnt;
import com.ingic.ezhalbatek.entities.SubscriptionsDetail;
import com.ingic.ezhalbatek.fragments.abstracts.BaseFragment;
import com.ingic.ezhalbatek.global.WebServiceConstants;
import com.ingic.ezhalbatek.helpers.UIHelper;
import com.ingic.ezhalbatek.ui.adapters.ViewPagerAdapter;
import com.ingic.ezhalbatek.ui.views.TitleBar;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static android.app.Activity.RESULT_CANCELED;
import static com.ingic.ezhalbatek.global.AppConstants.RESULT_OK;
import static com.ingic.ezhalbatek.global.WebServiceConstants.PACKAGEDETAIL;

public class SubscriptionPackageDetail extends BaseFragment {
    @BindView(R.id.pager)
    ViewPager pager;
    @BindView(R.id.btn_continue)
    Button btnContinue;
    Unbinder unbinder;

    private String address = "";
    private String addressLat = "";
    private String addressLng = "";
    private static int PLACE_AUTOCOMPLETE_REQUEST_CODE = 1;
    private ViewPagerAdapter pagerAdapter;
    private ArrayList<SubscriptionsDetail> subscriptionsDetails;

    private static String SUBSCRIPTIONDETAIL = "SUBSCRIPTIONDETAIL";
    private SubscriptionPackagesEnt subscriptionPackagesEnt;

    public static SubscriptionPackageDetail newInstance() {
        Bundle args = new Bundle();
        SubscriptionPackageDetail fragment = new SubscriptionPackageDetail();
        fragment.setArguments(args);
        return fragment;
    }

    public static SubscriptionPackageDetail newInstance(SubscriptionPackagesEnt data) {
        Bundle args = new Bundle();
        args.putString(SUBSCRIPTIONDETAIL, new Gson().toJson(data));
        SubscriptionPackageDetail fragment = new SubscriptionPackageDetail();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pagerAdapter = new ViewPagerAdapter(getChildFragmentManager());
        if (getArguments() != null) {
            String jsonString = getArguments().getString(SUBSCRIPTIONDETAIL);

            if (jsonString != null) {
                subscriptionPackagesEnt = new Gson().fromJson(jsonString, SubscriptionPackagesEnt.class);
            }
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_subscription_detail, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        serviceHelper.enqueueCall(webService.getPackageDetail(subscriptionPackagesEnt.getId() + ""), PACKAGEDETAIL);


    }

    @Override
    public void ResponseSuccess(Object result, String Tag, String message) {
        super.ResponseSuccess(result, Tag, message);
        switch (Tag) {
            case PACKAGEDETAIL:
                subscriptionsDetails=(ArrayList<SubscriptionsDetail>) result;
                bindViewPager(subscriptionsDetails);
                break;
        }
    }

    private void bindViewPager(ArrayList<SubscriptionsDetail> result) {

        if (result != null && result.size() > 0) {
            for(SubscriptionsDetail item: result){
                pagerAdapter.addFragment(ItemPackageDetailFragment.newInstance(item));
            }

        }

        setPagerSetting();
        pager.setAdapter(pagerAdapter);
    }

    private void setPagerSetting() {
        pager.setClipToPadding(false);
        pager.setPageMargin(10);
        pager.setPadding(20, 8, 20, 8);
        pager.setOffscreenPageLimit(3);

        pager.setPageTransformer(false, new ViewPager.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {
                int pageWidth = pager.getMeasuredWidth() - pager.getPaddingLeft() - pager.getPaddingRight();
                int pageHeight = pager.getHeight();
                int paddingLeft = pager.getPaddingLeft();
                int transformPos = (page.getLeft() - (pager.getScrollX() + paddingLeft)) / pageWidth;
                int max = pageHeight / 10;

                if (transformPos < -1) {
                    page.setAlpha(0.7f);// to make left transparent
                    page.setScaleY(0.9f);
                } else if (transformPos <= 1) { // [-1,1]
                    page.setAlpha(1f);
                    page.setScaleY(1f);
                } else { // (1,+Infinity]
                    // This page is way off-screen to the right.
                    page.setAlpha(0.7f);
                    page.setScaleY(0.9f);
                }
            }
        });
    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
        titleBar.showBackButton();
        titleBar.setSubHeading(getResString(R.string.subscriptions));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PLACE_AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {

                Place place = PlaceAutocomplete.getPlace(getDockActivity(), data);
                if (place != null) {
                    address = place.getAddress().toString();
                    addressLat = place.getLatLng().latitude + "";
                    addressLng = place.getLatLng().longitude + "";
                    getDockActivity().replaceDockableFragment(PackageDetailFragment.newInstance(subscriptionsDetails.get(pager.getCurrentItem()),address,addressLat,addressLng), PackageDetailFragment.TAG);
                }


            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(getDockActivity(), data);
                Log.i("SubscriptionFragment", status.getStatusMessage());

            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }
        }
    }

    @OnClick(R.id.btn_continue)
    public void onViewClicked() {

        requestLocationPermission();
       /* openLocationSelector();*/
    }

    private void openLocationSelector() {

        try {
            /* Intent intent =
                    new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN)
                           .build(getDockActivity());*/
            PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();

            this.startActivityForResult(builder.build(getMainActivity()), PLACE_AUTOCOMPLETE_REQUEST_CODE);
            //this.startActivityForResult(intent, PLACE_AUTOCOMPLETE_REQUEST_CODE);
        } catch (Exception e) {

            e.printStackTrace();
        }


    }

    private void requestLocationPermission() {
        Dexter.withActivity(getDockActivity())
                .withPermissions(
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {

                        if (report.areAllPermissionsGranted()) {
                            openLocationSelector();
                        }

                        // check for permanent denial of any permission
                        if (report.isAnyPermissionPermanentlyDenied()) {
                            requestLocationPermission();

                        } else if (report.getDeniedPermissionResponses().size() > 0) {
                            requestLocationPermission();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).
                withErrorListener(new PermissionRequestErrorListener() {
                    @Override
                    public void onError(DexterError error) {
                        UIHelper.showShortToastInCenter(getDockActivity(), "Grant Location Permission to processed");
                        openSettings();
                    }
                })

                .onSameThread()
                .check();

    }

    private void openSettings() {

        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        intent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        Uri uri = Uri.fromParts("package", getDockActivity().getPackageName(), null);
        intent.setData(uri);
        startActivity(intent);
    }
}

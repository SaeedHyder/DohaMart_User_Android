package com.ingic.ezhalbatek.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.location.places.Place;
import com.ingic.ezhalbatek.R;
import com.ingic.ezhalbatek.entities.LoginModule.UserEnt;
import com.ingic.ezhalbatek.fragments.abstracts.BaseFragment;
import com.ingic.ezhalbatek.global.AppConstants;
import com.ingic.ezhalbatek.global.WebServiceConstants;
import com.ingic.ezhalbatek.helpers.UIHelper;
import com.ingic.ezhalbatek.ui.views.AnyEditTextView;
import com.ingic.ezhalbatek.ui.views.AutoCompleteLocation;
import com.ingic.ezhalbatek.ui.views.TitleBar;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import droidninja.filepicker.FilePickerBuilder;
import droidninja.filepicker.FilePickerConst;
import droidninja.filepicker.utils.Orientation;

import static com.ingic.ezhalbatek.global.WebServiceConstants.UPDATEPROFILE;

/**
 * Created on 6/5/18.
 */
public class EditProfileFragment extends BaseFragment {
    public static final String TAG = "EditProfileFragment";
    @BindView(R.id.btnProfielImage)
    ImageView btnProfielImage;
    @BindView(R.id.edt_name)
    EditText edtName;
    @BindView(R.id.edt_email)
    TextView edtEmail;
    @BindView(R.id.edtPhone)
    AnyEditTextView edtPhone;
    @BindView(R.id.edtCity)
    AnyEditTextView edtCity;
    @BindView(R.id.edtZipCode)
    AnyEditTextView edtZipCode;
    @BindView(R.id.btn_register)
    Button btnRegister;
    Unbinder unbinder;
    @BindView(R.id.btnImagePick)
    ImageView btnImagePick;
    @BindView(R.id.AutoComplete)
    AutoCompleteLocation AutoComplete;

    private String location = "";
    private String latitude = "";
    private String longitude = "";
    private long mLastClickTime = 0;

    public static EditProfileFragment newInstance() {
        Bundle args = new Bundle();

        EditProfileFragment fragment = new EditProfileFragment();
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
        titleBar.setSubHeading(getResString(R.string.edit_profile));
    }

    private boolean isvalidated() {

        if (edtName.getText().toString().isEmpty() || edtName.getText().toString().length() < 3) {
            edtName.setError(getString(R.string.enter_name));
            return false;
        } /*else if (edtEmail.getText() == null || (edtEmail.getText().toString().isEmpty()) ||
                (!Patterns.EMAIL_ADDRESS.matcher(edtEmail.getText().toString()).matches())) {
            edtEmail.setError(getString(R.string.enter_valid_email));
            return false;
        } */ else if (AutoComplete.getText().toString().equals("") || AutoComplete.getText().toString().isEmpty() || AutoComplete.getText().toString().trim().equals("")) {
            AutoComplete.setError(getString(R.string.location_error));
            return false;
        }/* else if (edtPhone.getText().toString().length() < 9 || edtPhone.getText().toString().length() > 16) {
            edtPhone.setError(getString(R.string.numberLength));
            return false;
        } else if (edtCity.getText().toString().isEmpty()) {
            edtCity.setError(getString(R.string.enter_city));
            return false;
        } else if (edtCity.getText().toString().length() < 3) {

            edtCity.setError(getString(R.string.enter_valid_city));
            return false;
        } else if (edtZipCode.getText().toString().isEmpty() || edtZipCode.getText().toString().length() < 3) {
            edtZipCode.setError(getString(R.string.enter_zipcode));
            return false;
        }*/ else {
            return true;
        }

    }

    public void pickImageForUser() {
        AndPermission.with(this)
                .runtime()
                .permission(Permission.READ_EXTERNAL_STORAGE, Permission.CAMERA)
                .onGranted(permissions -> {
                    FilePickerBuilder.getInstance().setMaxCount(1)
                            .enableCameraSupport(true)
                            .enableVideoPicker(false)
                            .enableDocSupport(false)
                            .enableSelectAll(false)
                            .showGifs(false)
                            .withOrientation(Orientation.PORTRAIT_ONLY)
                            .showFolderView(false)
                            .setActivityTheme(R.style.AppTheme)
                            .pickPhoto(EditProfileFragment.this);
                })
                .onDenied(permStrings -> {
                    UIHelper.showShortToastInCenter(getDockActivity(), getResString(R.string.permission_error));
                })
                .start();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == FilePickerConst.REQUEST_CODE_PHOTO) {
            if (data != null) {
                ImageLoader.getInstance().displayImage(AppConstants.FILE_PATH + data.getStringArrayListExtra(FilePickerConst.KEY_SELECTED_MEDIA).get(0), btnProfielImage);
                btnProfielImage.setScaleType(ImageView.ScaleType.FIT_XY);
            }

        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_profile, container, false);
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

        setData();
        autoCompleteListner();
    }

    private void setData() {

        if (prefHelper.getUser() != null) {
            edtName.setText(prefHelper.getUser().getFullName() + "");
            edtEmail.setText(prefHelper.getUser().getEmail() + "");
            if (prefHelper.getUser().getLocation() != null && !prefHelper.getUser().getLocation().equals("")) {
                AutoComplete.setText(prefHelper.getUser().getLocation() + "");
            }

            location = prefHelper.getUser().getLocation() != null ? prefHelper.getUser().getLocation() : "";
            latitude = prefHelper.getUser().getLatitude() != null ? prefHelper.getUser().getLatitude() : "";
            longitude = prefHelper.getUser().getLongitude() != null ? prefHelper.getUser().getLongitude() : "";
        }
    }

    private void autoCompleteListner() {

        AutoComplete.setAutoCompleteTextListener(new AutoCompleteLocation.AutoCompleteLocationListener() {
            @Override
            public void onTextClear() {

            }

            @Override
            public void onItemSelected(Place selectedPlace) {
                location = selectedPlace.getAddress() + "";
                latitude = selectedPlace.getLatLng().latitude + "";
                longitude = selectedPlace.getLatLng().longitude + "";
            }
        });
    }


    @OnClick({R.id.btnProfielImage, R.id.btn_register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnProfielImage:
                pickImageForUser();
                break;

            case R.id.btn_register:
                if (SystemClock.elapsedRealtime() - mLastClickTime < 2000) {
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime();

                if (isvalidated()) {
                    serviceHelper.enqueueCall(webService.updateProfile(prefHelper.getUser().getId() + "", edtName.getText().toString(), prefHelper.getUser().getEmail() != null ? prefHelper.getUser().getEmail() : "",
                            location, location, latitude, longitude), UPDATEPROFILE);

                }
                break;
        }
    }

    @Override
    public void ResponseSuccess(Object result, String Tag, String message) {
        super.ResponseSuccess(result, Tag, message);
        switch (Tag) {
            case UPDATEPROFILE:
                UserEnt userEnt = (UserEnt) result;
                prefHelper.putUser(userEnt);
                getDockActivity().popFragment();
                break;
        }
    }
}
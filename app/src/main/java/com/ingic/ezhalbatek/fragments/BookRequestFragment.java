package com.ingic.ezhalbatek.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.ingic.ezhalbatek.R;
import com.ingic.ezhalbatek.fragments.abstracts.BaseFragment;
import com.ingic.ezhalbatek.helpers.UIHelper;
import com.ingic.ezhalbatek.interfaces.onDeleteImage;
import com.ingic.ezhalbatek.ui.binders.RecyclerViewAdapterImages;
import com.ingic.ezhalbatek.ui.binders.SelectedJobBinder;
import com.ingic.ezhalbatek.ui.views.AnyEditTextView;
import com.ingic.ezhalbatek.ui.views.AnySpinner;
import com.ingic.ezhalbatek.ui.views.AnyTextView;
import com.ingic.ezhalbatek.ui.views.AutoCompleteLocation;
import com.ingic.ezhalbatek.ui.views.CustomRecyclerView;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import droidninja.filepicker.FilePickerBuilder;
import droidninja.filepicker.FilePickerConst;
import droidninja.filepicker.utils.Orientation;

/**
 * Created on 5/24/18.
 */
public class BookRequestFragment extends BaseFragment {
    public static final String TAG = "BookRequestFragment";
    @BindView(R.id.edt_email)
    AnyEditTextView edtEmail;
    @BindView(R.id.spn_jobtype)
    AnySpinner spnJobtype;
    @BindView(R.id.txt_jobselected)
    AnyTextView txtJobselected;
    @BindView(R.id.listView_jobselected)
    CustomRecyclerView rvSelectedJobs;
    @BindView(R.id.txt_jobadditional)
    AnyTextView txtJobadditional;
    @BindView(R.id.edt_addtional_job)
    AnyEditTextView edtAddtionalJob;
    @BindView(R.id.txt_joblocation)
    AnyTextView txtJoblocation;
    @BindView(R.id.edt_locationgps)
    AutoCompleteLocation edtLocationgps;
    @BindView(R.id.img_gps)
    ImageView imgGps;
    @BindView(R.id.txt_address)
    AnyTextView txtAddress;
    @BindView(R.id.img_location_image)
    ImageView imgLocationImage;
    @BindView(R.id.edt_locationspecific)
    AnyEditTextView edtLocationspecific;
    @BindView(R.id.txt_jobpreferedDate)
    AnyTextView txtJobpreferedDate;
    @BindView(R.id.btn_preferreddate)
    AnyTextView btnPreferreddate;
    @BindView(R.id.ll_btnDate)
    LinearLayout llBtnDate;
    @BindView(R.id.btn_preferredtime)
    AnyTextView btnPreferredtime;
    @BindView(R.id.ll_btnTime)
    LinearLayout llBtnTime;
    @BindView(R.id.btn_addimage)
    LinearLayout btnAddimage;
    @BindView(R.id.addimages)
    CustomRecyclerView rvAddImages;
    @BindView(R.id.chkPayment)
    CheckBox chkPayment;
    @BindView(R.id.chkTermPrivacy)
    CheckBox chkTermPrivacy;
    @BindView(R.id.btnBook)
    Button btnBook;
    Unbinder unbinder;
    RecyclerViewAdapterImages adapterImages;
    private ArrayList<String> jobsCollection;
    private ArrayList<String> selectedJobsCollection;
    private ArrayList<String> imagesCollection = new ArrayList<>();
    private onDeleteImage onDeleteListener = new onDeleteImage() {
        @Override
        public void onDelete(int position) {
            imagesCollection.remove(position);
            adapterImages.notifyDataSetChanged();
        }

        @Override
        public void OnDeleteJobs(int position) {
            selectedJobsCollection.remove(position);
            rvSelectedJobs.notifyDataSetChanged();
        }
    };

    public static BookRequestFragment newInstance() {
        Bundle args = new Bundle();

        BookRequestFragment fragment = new BookRequestFragment();
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == FilePickerConst.REQUEST_CODE_PHOTO) {
            if (data != null) {
                imagesCollection.addAll(data.getStringArrayListExtra(FilePickerConst.KEY_SELECTED_MEDIA));
                adapterImages.notifyDataSetChanged();
            }

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_book_request_service, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bindJobsViews();
        bindImageViews();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void bindJobsViews() {
        jobsCollection = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            jobsCollection.add("Sample Job");
        }

        selectedJobsCollection = new ArrayList<>();

        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<String>(getDockActivity(), android.R.layout.simple_spinner_item, jobsCollection);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnJobtype.setAdapter(categoryAdapter);

        spnJobtype.setOnItemSelectedEvenIfUnchangedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedJobsCollection.add((String) spnJobtype.getSelectedItem());
                rvSelectedJobs.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        rvSelectedJobs.bindRecyclerView(new SelectedJobBinder(onDeleteListener, prefHelper), selectedJobsCollection, new LinearLayoutManager(getDockActivity(), LinearLayoutManager.VERTICAL, false), new DefaultItemAnimator());

    }

    private void bindImageViews() {
        adapterImages = new RecyclerViewAdapterImages(imagesCollection, getDockActivity(), onDeleteListener);
        rvAddImages.setLayoutManager(new LinearLayoutManager(getDockActivity(), LinearLayoutManager.VERTICAL, false));
        rvAddImages.setItemAnimator(new DefaultItemAnimator());
        rvAddImages.setAdapter(adapterImages);
    }

    public void pickImageForUser(int count) {
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
                            .pickPhoto(BookRequestFragment.this);
                })
                .onDenied(permissions -> {
                    UIHelper.showShortToastInCenter(getDockActivity(), "Permission is required to access this feature");
                })
                .start();

    }

    @OnClick({R.id.btn_preferreddate, R.id.btn_preferredtime, R.id.btn_addimage, R.id.btnBook})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_preferreddate:
                break;
            case R.id.btn_preferredtime:
                break;
            case R.id.btn_addimage:
                if (imagesCollection.size() == 5) {
                    UIHelper.showShortToastInCenter(getDockActivity(), "Max 5 Images are allowed.");
                    return;
                } else {
                    pickImageForUser(5 - imagesCollection.size());
                }
                break;
            case R.id.btnBook:
                break;
        }
    }
}
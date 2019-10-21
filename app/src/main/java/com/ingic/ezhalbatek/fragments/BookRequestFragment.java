package com.ingic.ezhalbatek.fragments;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Spannable;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.android.gms.location.places.Place;
import com.google.gson.Gson;
import com.ingic.ezhalbatek.R;
import com.ingic.ezhalbatek.entities.BookingObject;
import com.ingic.ezhalbatek.entities.CreateRequest;
import com.ingic.ezhalbatek.entities.LocationModel;
import com.ingic.ezhalbatek.entities.SubServiceEnt;
import com.ingic.ezhalbatek.fragments.abstracts.BaseFragment;
import com.ingic.ezhalbatek.global.AppConstants;
import com.ingic.ezhalbatek.helpers.DateHelper;
import com.ingic.ezhalbatek.helpers.DatePickerHelper;
import com.ingic.ezhalbatek.helpers.DialogHelper;
import com.ingic.ezhalbatek.helpers.TimePickerHelper;
import com.ingic.ezhalbatek.helpers.UIHelper;
import com.ingic.ezhalbatek.interfaces.onDeleteImage;
import com.ingic.ezhalbatek.ui.binders.RecyclerViewAdapterImages;
import com.ingic.ezhalbatek.ui.binders.SelectedJobBinder;
import com.ingic.ezhalbatek.ui.views.AnyEditTextView;
import com.ingic.ezhalbatek.ui.views.AnySpinner;
import com.ingic.ezhalbatek.ui.views.AnyTextView;
import com.ingic.ezhalbatek.ui.views.AutoCompleteLocation;
import com.ingic.ezhalbatek.ui.views.CustomRecyclerView;
import com.ingic.ezhalbatek.ui.views.TitleBar;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import droidninja.filepicker.FilePickerBuilder;
import droidninja.filepicker.FilePickerConst;
import droidninja.filepicker.utils.Orientation;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import static com.ingic.ezhalbatek.global.WebServiceConstants.ALLSUBSERVICES;
import static com.ingic.ezhalbatek.global.WebServiceConstants.CREATEREQUEST;

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
    @BindView(R.id.urgent_task)
    CheckBox urgentTask;
    private Date DateSelected;
    private String selectedDate = "";
    private ArrayList<SubServiceEnt> selectedJobsCollection = new ArrayList<>();
    private ArrayList<String> imagesCollection = new ArrayList<>();
    private ArrayList<SubServiceEnt> subServicesCollection;

    private String predate = "";
    private String preTime = "";
    private static String serviceID;
    private double locationLat = 0.0;
    private double locationLng = 0.0;

    public static BookRequestFragment newInstance(String id) {
        Bundle args = new Bundle();
        serviceID = id;
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_book_request_service, container, false);
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

        serviceHelper.enqueueCall(webService.getAllSubServices(serviceID), ALLSUBSERVICES);

        bindImageViews();
     //   if (!prefHelper.isLanguageArabian()) {
            bindSpannableText();


        edtLocationgps.setAutoCompleteTextListener(new AutoCompleteLocation.AutoCompleteLocationListener() {
            @Override
            public void onTextClear() {

            }

            @Override
            public void onItemSelected(Place selectedPlace) {

                locationLat = selectedPlace.getLatLng().latitude;
                locationLng = selectedPlace.getLatLng().longitude;
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        if (predate != null && !predate.isEmpty())
            btnPreferreddate.setText(predate);
        if (preTime != null && !preTime.isEmpty())
            btnPreferredtime.setText(preTime);

        if (selectedJobsCollection != null && selectedJobsCollection.size() > 0) {
            txtJobselected.setVisibility(View.VISIBLE);
            rvSelectedJobs.bindRecyclerView(new SelectedJobBinder(onDeleteListener, prefHelper), selectedJobsCollection, new LinearLayoutManager(getDockActivity(), LinearLayoutManager.VERTICAL, false), new DefaultItemAnimator());
            rvSelectedJobs.notifyDataSetChanged();
        } else {
            selectedJobsCollection = new ArrayList<>();
            rvSelectedJobs.bindRecyclerView(new SelectedJobBinder(onDeleteListener, prefHelper), selectedJobsCollection, new LinearLayoutManager(getDockActivity(), LinearLayoutManager.VERTICAL, false), new DefaultItemAnimator());
            txtJobselected.setVisibility(View.GONE);
        }
    }

    private void bindSpannableText() {

        setClickableText(getResString(R.string.term_option_value), getResString(R.string.terms), getResString(R.string.privacy), chkTermPrivacy, new ClickableSpan() {
            @Override
            public void onClick(View widget) {

                getDockActivity().replaceDockableFragment(TermsConditionFragment.newInstance(AppConstants.TERM, getResString(R.string.term_condition)), AboutAppFragment.TAG);

            }

            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(getDockActivity().getResources().getColor(R.color.app_red));
                ds.setUnderlineText(false);
            }
        }, new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                getDockActivity().replaceDockableFragment(AboutAppFragment.newInstance(AppConstants.PRIVCAY, getResString(R.string.privacy)), AboutAppFragment.TAG);
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(getDockActivity().getResources().getColor(R.color.app_red));
                ds.setUnderlineText(false);
            }
        });
    }

   /* private void initDatePicker(final TextView textView) {
        Calendar calendar = Calendar.getInstance();
        final DatePickerHelper datePickerHelper = new DatePickerHelper();
        datePickerHelper.initDateDialog(
                getDockActivity(),
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
                , new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        Date date = new Date();
                        Calendar c = Calendar.getInstance();
                        c.set(Calendar.YEAR, year);
                        c.set(Calendar.MONTH, month);
                        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);

// and get that as a Date
                        Date dateSpecified = c.getTime();
                        if (dateSpecified.before(date)) {
                            UIHelper.showShortToastInCenter(getDockActivity(), getString(R.string.date_before_error));
                        } else {
                            DateSelected = dateSpecified;
                            if (prefHelper.isLanguageArabic())
                                textView.setText(new SimpleDateFormat("yyyy-MM-dd", new Locale("ar")).format(c.getTime()));
                            else
                                textView.setText(new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).format(c.getTime()));
                            predate = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).format(c.getTime());

                        }

                    }
                }, "PreferredDate");

        datePickerHelper.showDate();
}
*/

    private void initDatePicker(final TextView textView) {
        Calendar calendar = Calendar.getInstance();
        final DatePickerHelper datePickerHelper = new DatePickerHelper();
        datePickerHelper.initDateDialog(
                getDockActivity(),
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
                , "PreferredDate");
        datePickerHelper.setListener(new DatePickerHelper.OnDateSelectedListener() {
            @Override
            public void onDatePicked(Calendar date) {
                DateSelected = date.getTime();
                selectedDate = new SimpleDateFormat(AppConstants.SERVER_DATE_FORMAT, Locale.ENGLISH)
                        .format(date.getTime());
                textView.setText(new SimpleDateFormat(AppConstants.DATE_FORMAT, Locale.ENGLISH)
                        .format(date.getTime()));
                predate = new SimpleDateFormat(AppConstants.DATE_FORMAT, Locale.ENGLISH)
                        .format(date.getTime());
            }
        });
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.set(Calendar.YEAR, c.get(Calendar.YEAR));
        datePickerHelper.setminDate(c.getTime().getTime());
        datePickerHelper.showDate();
    }

    private void initTimePicker(final TextView textView) {
        if (DateSelected != null) {
            Calendar calendar = Calendar.getInstance();
            final TimePickerHelper timePicker = new TimePickerHelper();

            timePicker.initTimeDialog(getDockActivity(), calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    Date date = new Date();
                    if (DateHelper.isSameDay(DateSelected, date) && !DateHelper.isTimeAfter(date.getHours(), date.getMinutes(), hourOfDay, minute)) {
                        UIHelper.showShortToastInCenter(getDockActivity(), getResString(R.string.less_time_error));
                    } else if (DateHelper.isSameDay(DateSelected, date) && !DateHelper.TwoHoursCheck(date.getHours(), date.getMinutes(), hourOfDay, minute)) {
                        UIHelper.showShortToastInCenter(getDockActivity(), getResString(R.string.please_select_time_atleast_2_hour));
                    } else {
                        Calendar c = Calendar.getInstance();
                        int year = c.get(Calendar.YEAR);
                        int month = c.get(Calendar.MONTH);
                        int day = c.get(Calendar.DAY_OF_MONTH);
                        c.set(year, month, day, hourOfDay, minute);
                        preTime = new SimpleDateFormat("HH:mm", Locale.ENGLISH).format(c.getTime());
                        if (prefHelper.isLanguageArabic())
                            textView.setText(new SimpleDateFormat("HH:mm", new Locale("ar")).format(c.getTime()));
                        else
                            textView.setText(new SimpleDateFormat("HH:mm", Locale.ENGLISH).format(c.getTime()));
                    }

                }
            }, true);

            timePicker.showTime();
        } else {
            UIHelper.showShortToastInCenter(getDockActivity(), getString(R.string.select_date_error));
        }
    }

    private void bindJobsViews(ArrayList<SubServiceEnt> ent) {

        subServicesCollection = new ArrayList<>();
        SubServiceEnt subServiceEnt = new SubServiceEnt();
        subServiceEnt.setTitle(getResString(R.string.select_sub_service));
        subServiceEnt.setArTitle(getResString(R.string.select_sub_service));
        subServiceEnt.setId(0);
        subServicesCollection.add(subServiceEnt);
        subServicesCollection.addAll((ArrayList<SubServiceEnt>) ent);

        ArrayList<String> subServiceList = new ArrayList();
        for (SubServiceEnt item : subServicesCollection) {
            subServiceList.add(item.getTitle());
        }

        ArrayAdapter<String> categoryAdapter;
        categoryAdapter = new ArrayAdapter<String>(getDockActivity(), R.layout.spinner_item, subServiceList);

        categoryAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spnJobtype.setAdapter(categoryAdapter);

        if (selectedJobsCollection == null || selectedJobsCollection.size() <= 0) {
            selectedJobsCollection = new ArrayList<>();
        }

        spnJobtype.setOnItemSelectedEvenIfUnchangedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0) {
                    selectedJobsCollection.add(subServicesCollection.get(spnJobtype.getSelectedItemPosition()));

                    Set<SubServiceEnt> hs = new HashSet<>();
                    hs.addAll(selectedJobsCollection);
                    selectedJobsCollection.clear();
                    selectedJobsCollection.addAll(hs);

                    txtJobselected.setVisibility(View.VISIBLE);
                    rvSelectedJobs.notifyDataSetChanged();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        if (selectedJobsCollection.size() > 0) {

            Set<SubServiceEnt> hs = new HashSet<>();
            hs.addAll(selectedJobsCollection);
            selectedJobsCollection.clear();
            selectedJobsCollection.addAll(hs);

            txtJobselected.setVisibility(View.VISIBLE);
            rvSelectedJobs.bindRecyclerView(new SelectedJobBinder(onDeleteListener, prefHelper), selectedJobsCollection, new LinearLayoutManager(getDockActivity(), LinearLayoutManager.VERTICAL, false), new DefaultItemAnimator());
        } else {
            rvSelectedJobs.bindRecyclerView(new SelectedJobBinder(onDeleteListener, prefHelper), selectedJobsCollection, new LinearLayoutManager(getDockActivity(), LinearLayoutManager.VERTICAL, false), new DefaultItemAnimator());
            txtJobselected.setVisibility(View.GONE);
        }


    }

    private void bindImageViews() {
        adapterImages = new RecyclerViewAdapterImages(imagesCollection, getDockActivity(), onDeleteListener);
        rvAddImages.setLayoutManager(new LinearLayoutManager(getDockActivity(), LinearLayoutManager.HORIZONTAL, false));
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

    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
        titleBar.showBackButton();
        titleBar.setSubHeading(getResString(R.string.request_for_service));
    }

    @OnClick({R.id.btn_preferreddate, R.id.btn_preferredtime, R.id.btn_addimage, R.id.btnBook, R.id.img_gps})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_preferreddate:
                initDatePicker(btnPreferreddate);
                break;
            case R.id.img_gps:
                UIHelper.hideSoftKeyboard(getDockActivity(), view);
                requestLocationPermission();

                break;
            case R.id.btn_preferredtime:
                initTimePicker(btnPreferredtime);
                break;
            case R.id.btn_addimage:
                if (imagesCollection.size() == 5) {
                    UIHelper.showShortToastInCenter(getDockActivity(), getResString(R.string.max_5_images_allowed));
                    return;
                } else {
                    pickImageForUser(5 - imagesCollection.size());
                }
                break;
            case R.id.btnBook:
                if (!prefHelper.isGuest()) {
                    if (isvalidated()) {

                        ArrayList<MultipartBody.Part> files = new ArrayList<>();

                        for (String item : imagesCollection) {
                            if (!item.contains("https")) {
                                File file = new File(item);
                                files.add(MultipartBody.Part.createFormData("images[]", file.getName(), RequestBody.create(MediaType.parse("image/*"), file)));
                            }
                        }

                        ArrayList<String> ids = new ArrayList<>();
                        double totalAmount = 0;
                        for (SubServiceEnt item : selectedJobsCollection) {
                            ids.add(item.getId() + "-" + item.getQuantity());
                            try {
                                totalAmount = totalAmount + Double.parseDouble(item.getAmount());
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }
                        String serviceIds = TextUtils.join(",", ids);

                        RequestBody jobTitle = RequestBody.create(MediaType.parse("text/plain"), edtEmail.getText().toString());
                        RequestBody serviceId = RequestBody.create(MediaType.parse("text/plain"), serviceIds);
                        RequestBody description = RequestBody.create(MediaType.parse("text/plain"), edtAddtionalJob.getText().toString());
                        RequestBody latitude = RequestBody.create(MediaType.parse("text/plain"), locationLat + "");
                        RequestBody longitude = RequestBody.create(MediaType.parse("text/plain"), locationLng + "");
                        RequestBody location = RequestBody.create(MediaType.parse("text/plain"), edtLocationgps.getText().toString());
                        RequestBody full_address = RequestBody.create(MediaType.parse("text/plain"), edtLocationspecific.getText().toString());
                        RequestBody date = RequestBody.create(MediaType.parse("text/plain"), btnPreferreddate.getText().toString());
                        RequestBody time = RequestBody.create(MediaType.parse("text/plain"), btnPreferredtime.getText().toString());
                        RequestBody total = RequestBody.create(MediaType.parse("text/plain"), totalAmount + "");
                        RequestBody payment_type = RequestBody.create(MediaType.parse("text/plain"), AppConstants.PAYMENTTYPE);
                        RequestBody currency_code = RequestBody.create(MediaType.parse("text/plain"), AppConstants.COUNTRYCURRENCY);
                        RequestBody user_id = RequestBody.create(MediaType.parse("text/plain"), prefHelper.getUser().getId() + "");
                        RequestBody is_urgent = RequestBody.create(MediaType.parse("text/plain"), urgentTask.isChecked() ? "1" : "0");

                        BookingObject bookingObject = new BookingObject(edtEmail.getText().toString(), serviceIds, edtAddtionalJob.getText().toString(), locationLat + "",
                                locationLng + "", edtLocationgps.getText().toString(), edtLocationspecific.getText().toString(), btnPreferreddate.getText().toString(),
                                btnPreferredtime.getText().toString(), totalAmount + "", AppConstants.PAYMENTTYPE, AppConstants.COUNTRYCURRENCY, prefHelper.getUser().getId() + "",
                                urgentTask.isChecked() ? "1" : "0", "");

                        RequestBody body = RequestBody.create(MediaType.parse("text/plain"), new Gson().toJson(bookingObject));

                        serviceHelper.enqueueCall(webService.createBodyRequest(body, files), CREATEREQUEST);
                        // serviceHelper.enqueueCall(webService.createRequest(jobTitle, serviceId, description, latitude, longitude, location, full_address, date, time, payment_type,total, currency_code, user_id, is_urgent, files), CREATEREQUEST);

                          /*  CreateRequest createRequest = new CreateRequest(edtEmail.getText().toString(), serviceIds,
                                edtAddtionalJob.getText().toString(), locationLat + "", locationLng + "", edtLocationgps.getText().toString(),
                                edtLocationspecific.getText().toString(), btnPreferreddate.getText().toString(), btnPreferredtime.getText().toString(), totalAmount+"",
                                AppConstants.PAYMENTTYPE, AppConstants.COUNTRYCURRENCY, prefHelper.getUser().getId(), urgentTask.isChecked() ? 1 : 0);

                        serviceHelper.enqueueCall(webService.createRequest(createRequest), CREATEREQUEST);*/
                    }
                } else {
                    DialogHelper dialogHelper = new DialogHelper(getDockActivity());
                    dialogHelper.CreateAccountDialoge(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            getDockActivity().popBackStackTillEntry(0);
                            getDockActivity().replaceDockableFragment(LoginFragment.newInstance(false), "LoginFragment");
                            dialogHelper.hideDialog();
                        }
                    });
                    dialogHelper.showDialog();
                }

                break;
        }
    }

    private void setClickableText(String text, String clickableItem, String clickableItem2, CheckBox textView, ClickableSpan clickableSpan, ClickableSpan clickableSpan2) {

        textView.setMovementMethod(LinkMovementMethod.getInstance());
        textView.setText(text, TextView.BufferType.SPANNABLE);
        Spannable mySpannable = (Spannable) textView.getText();
        int startPosition = text.indexOf(clickableItem);
        int endPosition = text.lastIndexOf(clickableItem) + clickableItem.length();
        int startPosition2 = text.indexOf(clickableItem2);
        int endPosition2 = text.lastIndexOf(clickableItem2) + clickableItem2.length();
        mySpannable.setSpan(clickableSpan, startPosition, endPosition, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        mySpannable.setSpan(clickableSpan2, startPosition2, endPosition2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
    }

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
            if (selectedJobsCollection.size() <= 0) {
                txtJobselected.setVisibility(View.GONE);
            }

        }
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == FilePickerConst.REQUEST_CODE_PHOTO) {
            if (data != null) {
                imagesCollection.addAll(data.getStringArrayListExtra(FilePickerConst.KEY_SELECTED_MEDIA));
                //    adapterImages.addAllItem(data.getStringArrayListExtra(FilePickerConst.KEY_SELECTED_MEDIA));
                adapterImages.notifyItemInserted(imagesCollection.size() - 1);
                adapterImages.notifyDataSetChanged();
            }

        }
    }

    private void getLocation(AutoCompleteTextView textView) {
        if (getMainActivity().statusCheck()) {
            LocationModel locationModel = getMainActivity().getMyCurrentLocation();
            if (locationModel != null) {
                textView.setText(locationModel.getAddress());
                locationLat = locationModel.getLat();
                locationLng = locationModel.getLng();
            } else {
                getLocation(edtLocationgps);
            }
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
                            getLocation(edtLocationgps);
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

    private boolean isvalidated() {

        boolean isvalidQuantity = true;
        String title = "";
        for (SubServiceEnt item : selectedJobsCollection) {
            if (item.getQuantity() == null || item.getQuantity().equals("")) {
                isvalidQuantity = false;
                title = item.getTitle();
            }
        }

        if (edtEmail.getText().toString().isEmpty() || edtEmail.getText().toString().length() < 3 || edtEmail.getText().toString().trim().equals("")) {
            edtEmail.setError(getDockActivity().getResources().getString(R.string.enter_job_title));
            if (edtEmail.requestFocus()) {
                setEditTextFocus(edtEmail);
            }
            return false;
            //   } else if (spnJobtype.getSelectedItemPosition() == 0 || selectedJobsCollection.size() <= 0) {
        } else if (selectedJobsCollection.size() <= 0) {
            UIHelper.showShortToastInCenter(getDockActivity(), getResString(R.string.select_job_to_proceed));
            return false;
        } else if (selectedJobsCollection.size() > 0 && !isvalidQuantity) {
            UIHelper.showShortToastInCenter(getDockActivity(), getResString(R.string.enter_quantity_of) + " " + title);
            return false;

        } else if (edtAddtionalJob.getText().toString().isEmpty() || edtAddtionalJob.getText().toString().length() < 3 || edtAddtionalJob.getText().toString().trim().equals("")) {
            edtAddtionalJob.setError(getResString(R.string.write_your_job_desc));
            if (edtAddtionalJob.requestFocus()) {
                setEditTextFocus(edtAddtionalJob);
            }
            return false;
        } else if (edtLocationgps.getText().toString().isEmpty() || edtLocationgps.getText().toString().trim().equals("")) {
            UIHelper.showShortToastInCenter(getDockActivity(), getResString(R.string.enter_your_location));
            //      edtLocationgps.setError("");

            return false;
        } else if (edtLocationspecific.getText().toString().isEmpty() || edtLocationspecific.getText().toString().length() < 3 || edtLocationspecific.getText().toString().trim().equals("")) {
            edtLocationspecific.setError(getResString(R.string.enter_your_address));
            if (edtLocationspecific.requestFocus()) {
                setEditTextFocus(edtLocationspecific);
            }
            return false;
        } else if (btnPreferreddate.getText().toString().isEmpty()) {
            UIHelper.showShortToastInCenter(getDockActivity(), getResString(R.string.select_date));
            //  btnPreferreddate.setError("");

            return false;
        } else if (btnPreferredtime.getText().toString().isEmpty()) {
            UIHelper.showShortToastInCenter(getDockActivity(), getResString(R.string.select_time));
            //  btnPreferredtime.setError("");
            return false;
        } else
            return true;

    }

    @Override
    public void ResponseSuccess(Object result, String Tag, String message) {
        super.ResponseSuccess(result, Tag, message);
        switch (Tag) {
            case ALLSUBSERVICES:
                ArrayList<SubServiceEnt> ent = (ArrayList<SubServiceEnt>) result;
                bindJobsViews(ent);
                break;

            case CREATEREQUEST:
                dialogHelper.showCommonDialog(v -> {
                    dialogHelper.hideDialog();
                    getDockActivity().popBackStackTillEntry(0);
                    getDockActivity().replaceDockableFragment(HomeFragment.newInstance(), "HomeFragment");
                }, R.string.empty, R.string.request_send, R.string.ok, R.string.empty, false, false);
                dialogHelper.setCancelable(false);
                dialogHelper.showDialog();
                break;
        }
    }


}
package com.ingic.ezhalbatek.activities;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.text.Html;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.google.firebase.FirebaseApp;
import com.google.firebase.iid.FirebaseInstanceId;
import com.ingic.ezhalbatek.R;
import com.ingic.ezhalbatek.entities.LocationModel;
import com.ingic.ezhalbatek.entities.ResponseWrapper;
import com.ingic.ezhalbatek.fragments.AdditionalJobAcknowledFragment;
import com.ingic.ezhalbatek.fragments.HomeFragment;
import com.ingic.ezhalbatek.fragments.JobDoneAcknowledgeFragment;
import com.ingic.ezhalbatek.fragments.LanguageSelectionFragment;
import com.ingic.ezhalbatek.fragments.LoginFragment;
import com.ingic.ezhalbatek.fragments.MyServicesFragment;
import com.ingic.ezhalbatek.fragments.NotificationsFragment;
import com.ingic.ezhalbatek.fragments.SideMenuFragment;
import com.ingic.ezhalbatek.fragments.SubscriptionStatusFragment;
import com.ingic.ezhalbatek.fragments.TutorialFragment;
import com.ingic.ezhalbatek.fragments.abstracts.BaseFragment;
import com.ingic.ezhalbatek.global.AppConstants;
import com.ingic.ezhalbatek.global.SideMenuChooser;
import com.ingic.ezhalbatek.global.SideMenuDirection;
import com.ingic.ezhalbatek.global.WebServiceConstants;
import com.ingic.ezhalbatek.helpers.BasePreferenceHelper;
import com.ingic.ezhalbatek.helpers.DialogHelper;
import com.ingic.ezhalbatek.helpers.ScreenHelper;
import com.ingic.ezhalbatek.helpers.UIHelper;
import com.ingic.ezhalbatek.residemenu.ResideMenu;
import com.ingic.ezhalbatek.retrofit.WebService;
import com.ingic.ezhalbatek.retrofit.WebServiceFactory;
import com.ingic.ezhalbatek.ui.views.TitleBar;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import droidninja.filepicker.FilePickerBuilder;
import droidninja.filepicker.utils.Orientation;
import me.leolin.shortcutbadger.ShortcutBadger;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends DockActivity implements OnClickListener {
    public TitleBar titleBar;
    @BindView(R.id.sideMneuFragmentContainer)
    public FrameLayout sideMneuFragmentContainer;
    @BindView(R.id.header_main)
    TitleBar header_main;
    @BindView(R.id.mainFrameLayout)
    FrameLayout mainFrameLayout;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.content_frame)
    RelativeLayout contentFrame;
    private MainActivity mContext;
    private boolean loading;

    private ResideMenu resideMenu;
    private LocationManager locationManager;
    private String address = "";
    private String country = "";

    private float lastTranslate = 0.0f;

    private String sideMenuType;
    private String sideMenuDirection;
    protected BroadcastReceiver broadcastReceiver;
    private WebService webservice;
    private BasePreferenceHelper preferenceHelper;

    public View getDrawerView() {
        return getLayoutInflater().inflate(getSideMenuFrameLayoutId(), null);
    }

    public void changeBackgroundResources(int resID) {
        contentFrame.setBackgroundResource(resID);
    }

    public void pickImageForUser(int count) {
        FilePickerBuilder.getInstance().setMaxCount(count)
                .enableCameraSupport(true)
                .enableVideoPicker(false)
                .enableDocSupport(false)
                .enableSelectAll(false)
                .showGifs(false)
                .withOrientation(Orientation.PORTRAIT_ONLY)
                .showFolderView(false)
                .setActivityTheme(R.style.AppTheme)
                .pickPhoto(this);
    }

    private void setCurrentLocale() {
        if (prefHelper.isLanguageArabian()) {
            Resources resources = getResources();
            DisplayMetrics dm = resources.getDisplayMetrics();
            Configuration conf = resources.getConfiguration();
            Locale locale=new Locale("ar");
            //   conf.setLayoutDirection(locale);
            conf.locale = locale;
            resources.updateConfiguration(conf, dm);


        } else {
            Resources resources = getResources();
            DisplayMetrics dm = resources.getDisplayMetrics();
            Configuration conf = resources.getConfiguration();
            Locale locale=new Locale("en");
            //  conf.setLayoutDirection(locale);
            conf.locale = locale;
            resources.updateConfiguration(conf, dm);


        }
    }
    public void restartActivity() {
        Intent intent = getIntent();
        if (getIntent().getExtras() != null) {
            Bundle bundle = new Bundle();
            bundle.putString("actionType", "");
            bundle.putString("action_type", "");
            bundle.putString("title", "");
            intent.putExtras(bundle);
        }

        finish();
        startActivity(intent);
    }

    private void settingSideMenu(String type, String direction) {

        if (type.equals(SideMenuChooser.DRAWER.getValue())) {


            DisplayMetrics matrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(matrics);
            Long longwidth = Math.round(matrics.widthPixels * 0.70);
            int drawerwidth = longwidth.intValue();
            DrawerLayout.LayoutParams params = new DrawerLayout.LayoutParams(drawerwidth, (int) DrawerLayout.LayoutParams.MATCH_PARENT);


            if (direction.equals(SideMenuDirection.LEFT.getValue())) {
                params.gravity = Gravity.LEFT;
                sideMneuFragmentContainer.setLayoutParams(params);
            } else {
                params.gravity = Gravity.RIGHT;
                sideMneuFragmentContainer.setLayoutParams(params);
            }
            drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

            sideMenuFragment = SideMenuFragment.newInstance();
            FragmentTransaction transaction = getSupportFragmentManager()
                    .beginTransaction();
            transaction.replace(getSideMenuFrameLayoutId(), sideMenuFragment).commit();
            drawerLayout.setScrimColor(getResources().getColor(R.color.semi_tranparent));
            setDrawerListeners();
            drawerLayout.closeDrawers();
        } else {
            resideMenu = new ResideMenu(this);
            resideMenu.attachToActivity(this);
            resideMenu.setMenuListener(getMenuListener());
            resideMenu.setScaleValue(0.52f);

            setMenuItemDirection(direction);
        }
    }

    public void pickImageForUser(int count, BaseFragment fragment) {
        FilePickerBuilder.getInstance().setMaxCount(count)
                .enableCameraSupport(true)
                .enableVideoPicker(false)
                .enableDocSupport(false)
                .enableSelectAll(false)
                .showGifs(false)
                .withOrientation(Orientation.PORTRAIT_ONLY)
                .showFolderView(false)
                .setActivityTheme(R.style.AppTheme)
                .pickPhoto(fragment);
    }

    private void setMenuItemDirection(String direction) {

        if (direction.equals(SideMenuDirection.LEFT.getValue())) {

            SideMenuFragment leftSideMenuFragment = SideMenuFragment.newInstance();
            resideMenu.addMenuItem(leftSideMenuFragment, "LeftSideMenuFragment", direction);

        } else if (direction.equals(SideMenuDirection.RIGHT.getValue())) {

            SideMenuFragment rightSideMenuFragment = SideMenuFragment.newInstance();
            resideMenu.addMenuItem(rightSideMenuFragment, "RightSideMenuFragment", direction);

        }

    }

    private int getSideMenuFrameLayoutId() {
        return R.id.sideMneuFragmentContainer;

    }

    public DisplayImageOptions getImageLoaderRoundCornerTransformation(int raduis) {
        return new DisplayImageOptions.Builder()
                .showImageForEmptyUri(R.drawable.placeholder)
                .showImageOnFail(R.drawable.placeholder).resetViewBeforeLoading(true)
                .cacheInMemory(true).cacheOnDisc(true)
                .imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2)
                .displayer(new RoundedBitmapDisplayer(raduis))
                .bitmapConfig(Bitmap.Config.RGB_565).build();

    }


    public void initFragment() {
        getSupportFragmentManager().addOnBackStackChangedListener(getListener());
        if (prefHelper.isLogin()) {
            replaceDockableFragment(HomeFragment.newInstance(), "HomeFragment");
        } else {
           /* if (prefHelper.isLanguageSelected()) {
                replaceDockableFragment(LoginFragment.newInstance(), "LoginFragment");
            } else {
                replaceDockableFragment(LanguageSelectionFragment.newInstance(), "LanguageSelectionFragment");
            }*/
            replaceDockableFragment(TutorialFragment.newInstance(), "TutorialFragment");
        }

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            String type = bundle.getString("action_type");
            final String actionId = bundle.getString("action_id");
            final String notificationId = bundle.getString("notification_id");

            if (notificationId != null && !notificationId.equals("")) {
                MarkUnReadNotification(notificationId);
            }

            if (type != null && type.equals(AppConstants.ADDITIONALJOBREQUEST)) {
                getDockActivity().replaceDockableFragment(AdditionalJobAcknowledFragment.newInstance(actionId), "AdditionalJobAcknowledFragment");
            } else if (type != null && type.equals(AppConstants.ADDITIONALJOBSUBSCRIPTION)) {
                getDockActivity().replaceDockableFragment(AdditionalJobAcknowledFragment.newInstance(actionId), "AdditionalJobAcknowledFragment");
            } else if (type != null && type.equals(AppConstants.JobPush)) {
                getDockActivity().replaceDockableFragment(MyServicesFragment.newInstance(), "MyServicesFragment");
            } else if (type != null && type.equals(AppConstants.SubscriptionPush)) {
                getDockActivity().replaceDockableFragment(SubscriptionStatusFragment.newInstance(), "SubscriptionStatusFragment");
            } else if (type != null && type.equals(AppConstants.JOBDONE)) {
                getDockActivity().replaceDockableFragment(JobDoneAcknowledgeFragment.newInstance(actionId + "", false, false), "JobDoneAcknowledgeFragment");
            } else if (type != null && type.equals(AppConstants.SubscriptionDone)) {
                getDockActivity().replaceDockableFragment(JobDoneAcknowledgeFragment.newInstance(actionId + "", true, false), "JobDoneAcknowledgeFragment");
            } else if (type != null && type.equals(AppConstants.JobAcknowledge)) {
                getDockActivity().replaceDockableFragment(JobDoneAcknowledgeFragment.newInstance(actionId + "", false, true), "JobDoneAcknowledgeFragment");
            } else if (type != null && type.equals(AppConstants.SubscriptionAcknowledge)) {
                getDockActivity().replaceDockableFragment(JobDoneAcknowledgeFragment.newInstance(actionId + "", true, true), "JobDoneAcknowledgeFragment");
            } else if (type != null && type.equals(AppConstants.AdditionalJobAccepted)) {
                getDockActivity().replaceDockableFragment(AdditionalJobAcknowledFragment.newInstance(actionId, AppConstants.ACCEPTED), "AdditionalJobAcknowledFragment");
            } else if (type != null && type.equals(AppConstants.AdditionalJobRejected)) {
                getDockActivity().replaceDockableFragment(AdditionalJobAcknowledFragment.newInstance(actionId, AppConstants.REJECTED), "AdditionalJobAcknowledFragment");
            } else if (type != null && type.equals(AppConstants.AdditionalJobSubscriptionAccepted)) {
                getDockActivity().replaceDockableFragment(AdditionalJobAcknowledFragment.newInstance(actionId, AppConstants.ACCEPTED), "AdditionalJobAcknowledFragment");
            } else if (type != null && type.equals(AppConstants.AdditionalJobSubscriptionRejected)) {
                getDockActivity().replaceDockableFragment(AdditionalJobAcknowledFragment.newInstance(actionId, AppConstants.REJECTED), "AdditionalJobAcknowledFragment");
            } else if (type != null && type.equals(AppConstants.JobReminder)) {
                getDockActivity().replaceDockableFragment(MyServicesFragment.newInstance(), "MyServicesFragment");
            } else if (type != null && type.equals(AppConstants.SubscriptionReminder)) {
                getDockActivity().replaceDockableFragment(SubscriptionStatusFragment.newInstance(), "SubscriptionStatusFragment");
            } else if (type != null && type.equals(AppConstants.block_user)) {
               /* prefHelper.setLoginStatus(false);
                getDockActivity().popBackStackTillEntry(0);
                replaceDockableFragment(LoginFragment.newInstance(false), "LoginFragment");*/
            }

        }
    }

    private void onNotificationReceived() {
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                // checking for type intent filter
                if (intent.getAction().equals(AppConstants.REGISTRATION_COMPLETE)) {
                    System.out.println("registration complete");
                    System.out.println(prefHelper.getFirebase_TOKEN());

                } else if (intent.getAction().equals(AppConstants.PUSH_NOTIFICATION)) {
                    Bundle bundle = intent.getExtras();
                    if (bundle != null) {
                        String type = bundle.getString("action_type");
                        final String actionId = bundle.getString("action_id");
                        final String notificationId = bundle.getString("notification_id");

                        if (notificationId != null && !notificationId.equals("")) {
                            MarkUnReadNotification(notificationId);
                        }

                        if (type != null && type.equals(AppConstants.ADDITIONALJOBREQUEST)) {

                        } else if (type != null && type.equals(AppConstants.ADDITIONALJOBSUBSCRIPTION)) {

                        } else if (type != null && type.equals(AppConstants.JobPush)) {

                        } else if (type != null && type.equals(AppConstants.SubscriptionPush)) {

                        } else if (type != null && type.equals(AppConstants.JOBDONE)) {

                        } else if (type != null && type.equals(AppConstants.block_user)) {

                            DialogHelper dialogHelper = new DialogHelper(getDockActivity());
                            dialogHelper.BlockAccountDialoge(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    dialogHelper.hideDialog();
                                }
                            });
                            dialogHelper.showDialog();
                            Logout();

                        }
                    }
                }
            }

        };
    }

    private FragmentManager.OnBackStackChangedListener getListener() {
        FragmentManager.OnBackStackChangedListener result = new FragmentManager.OnBackStackChangedListener() {
            public void onBackStackChanged() {
                FragmentManager manager = getSupportFragmentManager();

                if (manager != null) {
                    BaseFragment currFrag = (BaseFragment) manager.findFragmentById(getDockFrameLayoutId());
                    if (currFrag != null) {
                        currFrag.fragmentResume();
                    }
                }
            }
        };

        return result;
    }

    /*@Override
    public void onLoadingStarted() {

        if (mainFrameLayout != null) {
            mainFrameLayout.setVisibility(View.VISIBLE);
            if (progressBar != null) {
                progressBar.setVisibility(View.VISIBLE);
            }
            loading = true;
        }
    }

    @Override
    public void onLoadingFinished() {
        mainFrameLayout.setVisibility(View.VISIBLE);

        if (progressBar != null) {
            progressBar.setVisibility(View.INVISIBLE);
        }
        loading = false;

    }
*/
    @Override
    public void onLoadingStarted() {

        if (mainFrameLayout != null) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            mainFrameLayout.setVisibility(View.VISIBLE);
            if (progressBar != null) {
                progressBar.setVisibility(View.VISIBLE);
                getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                        WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            }
            loading = true;
        }
    }

    @Override
    public void onLoadingFinished() {
        mainFrameLayout.setVisibility(View.VISIBLE);

        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        if (progressBar != null) {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            progressBar.setVisibility(View.INVISIBLE);
        }
        loading = false;

    }

    @Override
    public void onProgressUpdated(int percentLoaded) {

    }

    public void lockDrawer() {
        try {
            drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void releaseDrawer() {
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
    }

    @Override
    public int getDockFrameLayoutId() {
        return R.id.mainFrameLayout;
    }

    private void setDrawerListeners() {
        drawerLayout.setScrimColor(getResources().getColor(android.R.color.transparent));
        final DrawerLayout.LayoutParams params = new DrawerLayout.LayoutParams(DrawerLayout.LayoutParams.MATCH_PARENT,
                DrawerLayout.LayoutParams.MATCH_PARENT);
        drawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {

                contentFrame.setTranslationX(slideOffset * drawerView.getWidth());
                drawerLayout.bringChildToFront(drawerView);
                drawerLayout.requestLayout();

            }

            @Override
            public void onDrawerOpened(@NonNull View drawerView) {

            }

            @Override
            public void onDrawerClosed(@NonNull View drawerView) {
                drawerLayout.setScrimColor(getResources().getColor(android.R.color.transparent));
            }

            @Override
            public void onDrawerStateChanged(int newState) {
            }
        });
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dock);
        ButterKnife.bind(this);

        titleBar = header_main;
        changeBackgroundResources(R.drawable.bg);
        preferenceHelper = new BasePreferenceHelper(getDockActivity());
        // setBehindContentView(R.layout.fragment_frame);
        mContext = this;
        Log.i("Screen Density", ScreenHelper.getDensity(this) + "");

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        FirebaseApp.initializeApp(this);

        sideMenuType = SideMenuChooser.DRAWER.getValue();
        sideMenuDirection = SideMenuDirection.LEFT.getValue();

        settingSideMenu(sideMenuType, sideMenuDirection);

        setCurrentLocale();


        titleBar.setMenuButtonListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (sideMenuType.equals(SideMenuChooser.DRAWER.getValue()) && getDrawerLayout() != null) {
                    if (sideMenuDirection.equals(SideMenuDirection.LEFT.getValue())) {
                        drawerLayout.openDrawer(Gravity.LEFT);
                    } else {
                        drawerLayout.openDrawer(Gravity.RIGHT);

                    }
                } else {
                    resideMenu.openMenu(sideMenuDirection);
                }

            }
        });

        titleBar.setBackButtonListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (loading) {
                    UIHelper.showLongToastInCenter(getApplicationContext(),
                            R.string.message_wait);
                } else {

                    popFragment();
                    UIHelper.hideSoftKeyboard(getApplicationContext(),
                            titleBar);
                }
            }
        });

        titleBar.setNotificationButtonListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceDockableFragment(NotificationsFragment.newInstance(), "NotificationsFragment");
            }
        });

        onNotificationReceived();
        initFragment();

    }

    @Override
    public void onBackPressed() {
        if (loading) {
            UIHelper.showLongToastInCenter(getApplicationContext(),
                    R.string.message_wait);
        } else
            super.onBackPressed();

    }

    @Override
    public void onMenuItemActionCalled(int actionId, String data) {

    }

    @Override
    public void setSubHeading(String subHeadText) {

    }

    @Override
    public boolean isLoggedIn() {
        return false;
    }

    @Override
    public void hideHeaderButtons(boolean leftBtn, boolean rightBtn) {
    }

    private void notImplemented() {
        UIHelper.showLongToastInCenter(this, "Coming Soon");
    }

    @Override
    public void onClick(View view) {

    }

    public boolean statusCheck() {
        if (isNetworkAvailable()) {
            final LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                buildAlertMessageNoGps();
                return false;
            } else {
                return true;
            }
        } else {

            UIHelper.showShortToastInCenter(this, getString(R.string.internet_not_connected));
            return false;
        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private void buildAlertMessageNoGps() {

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage(getString(R.string.gps_question))
                .setCancelable(false)
                .setPositiveButton(getResources().getString(R.string.gps_yes), new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        // startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                        startActivityForResult(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS), AppConstants.LocationUpdateListner);
                    }
                })
                .setNegativeButton(getResources().getString(R.string.gps_no), new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        dialog.cancel();
                    }
                });

        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                AlertDialog alert = builder.create();
                alert.show();
            }
        });

    }

    public LocationModel getMyCurrentLocation() {


        String address = "";
        LocationModel locationObj = new LocationModel(address, 35.705240, 51.435577);
        //  LocationModel locationObj = new LocationModel(address,24.829759,67.073822);


// instantiate the location manager, note you will need to request permissions in your manifest
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);


// get the last know location from your location manager.
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return null;
        }
// now get the lat/lon from the location and do something with it.
        Location gpslocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        Location networklocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        Location passivelocation = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
        Location locationchangelocation = locationManager.getLastKnownLocation(LocationManager.KEY_LOCATION_CHANGED);


        if (gpslocation != null) {

            Log.d("Location", "GPS::" + gpslocation.getLatitude() + "," + gpslocation.getLongitude());
            address = getCurrentAddress(gpslocation.getLatitude(), gpslocation.getLongitude());
            locationObj = new LocationModel(address, gpslocation.getLatitude(), gpslocation.getLongitude());

            return locationObj;

        } else if (networklocation != null) {

            Log.d("Location", "NETWORK::" + networklocation.getLatitude() + "," + networklocation.getLongitude());
            address = getCurrentAddress(networklocation.getLatitude(), networklocation.getLongitude());
            locationObj = new LocationModel(address, networklocation.getLatitude(), networklocation.getLongitude());

            return locationObj;
        } else if (passivelocation != null) {

            Log.d("Location", "PASSIVE::" + passivelocation.getLatitude() + "," + passivelocation.getLongitude());
            address = getCurrentAddress(passivelocation.getLatitude(), passivelocation.getLongitude());
            locationObj = new LocationModel(address, passivelocation.getLatitude(), passivelocation.getLongitude());

            return locationObj;
        } else if (locationchangelocation != null) {

            Log.d("Location", "CHAGELOCATION::" + locationchangelocation.getLatitude() + "," + locationchangelocation.getLongitude());
            address = getCurrentAddress(locationchangelocation.getLatitude(), locationchangelocation.getLongitude());
            locationObj = new LocationModel(address, locationchangelocation.getLatitude(), locationchangelocation.getLongitude());

            return locationObj;
        }

        return locationObj;


    }

    private String getCurrentAddress(double lat, double lng) {
        try {


            Geocoder geocoder;
            List<Address> addresses;
            geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
            addresses = geocoder.getFromLocation(lat, lng, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
            if (addresses.size() > 0) {
                address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
            }
// String city = addresses.get(0).getLocality();
// String state = addresses.get(0).getAdminArea();
            if (addresses.size() > 0) {
                country = addresses.get(0).getCountryName();
            }
// String postalCode = addresses.get(0).getPostalCode();
// String knownName = addresses.get(0).getFeatureName(); // Only if available else return NULL
            return address + ", " + country;

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void onPause() {
        LocalBroadcastManager.getInstance(getDockActivity()).unregisterReceiver(broadcastReceiver);
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(getDockActivity()).registerReceiver(broadcastReceiver,
                new IntentFilter(AppConstants.REGISTRATION_COMPLETE));

        LocalBroadcastManager.getInstance(getDockActivity()).registerReceiver(broadcastReceiver,
                new IntentFilter(AppConstants.PUSH_NOTIFICATION));


    }

    private void MarkUnReadNotification(String notificationId) {
        webservice = WebServiceFactory.getWebServiceInstanceWithCustomInterceptor(this, WebServiceConstants.SERVICE_URL);
        preferenceHelper = new BasePreferenceHelper(this);
        Call<ResponseWrapper> call = webservice.markUnRead(preferenceHelper.getUser().getId(), Integer.parseInt(notificationId));
        call.enqueue(new Callback<ResponseWrapper>() {
            @Override
            public void onResponse(Call<ResponseWrapper> call, Response<ResponseWrapper> response) {

            }

            @Override
            public void onFailure(Call<ResponseWrapper> call, Throwable t) {

            }
        });
    }

    private void Logout() {
        webservice = WebServiceFactory.getWebServiceInstanceWithCustomInterceptor(this, WebServiceConstants.SERVICE_URL);
        preferenceHelper = new BasePreferenceHelper(this);
        Call<ResponseWrapper> call = webservice.logout(preferenceHelper.getUser().getId(), FirebaseInstanceId.getInstance().getToken());
        call.enqueue(new Callback<ResponseWrapper>() {
            @Override
            public void onResponse(Call<ResponseWrapper> call, Response<ResponseWrapper> response) {
                prefHelper.setLoginStatus(false);
                prefHelper.setGuestStatus(false);
                getDockActivity().popBackStackTillEntry(0);
                replaceDockableFragment(LoginFragment.newInstance(false), "LoginFragment");

            }

            @Override
            public void onFailure(Call<ResponseWrapper> call, Throwable t) {

            }
        });
    }

    public void refreshSideMenu() {
       /* sideMenuFragment = SideMenuFragment.newInstance();
        FragmentTransaction transaction = getSupportFragmentManager()
                .beginTransaction();
        transaction.remove(sideMenuFragment).commit();
        settingSideMenu();*/
        if (sideMenuFragment != null) {
            sideMenuFragment.refreshMenuOption();
        }
    }

}

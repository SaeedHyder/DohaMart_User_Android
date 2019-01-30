package com.ingic.ezhalbatek.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.ingic.ezhalbatek.R;
import com.ingic.ezhalbatek.entities.ServiceStatus.Service;
import com.ingic.ezhalbatek.entities.ServiceStatus.ServicesStatus;
import com.ingic.ezhalbatek.fragments.abstracts.BaseFragment;
import com.ingic.ezhalbatek.global.AppConstants;
import com.ingic.ezhalbatek.interfaces.RecyclerItemListener;
import com.ingic.ezhalbatek.ui.binders.PendingServiceBinder;
import com.ingic.ezhalbatek.ui.views.AnyTextView;
import com.ingic.ezhalbatek.ui.views.CustomRecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.ingic.ezhalbatek.global.WebServiceConstants.SERVICESTATUS;

/**
 * Created on 6/6/18.
 */
public class PendingServicesFragment extends BaseFragment {
    public static final String TAG = "PendingServicesFragment";
    @BindView(R.id.rvPendingJobs)
    CustomRecyclerView rvPendingJobs;
    Unbinder unbinder;
    @BindView(R.id.txt_no_data)
    AnyTextView txtNoData;

    private static String PendingKey = "PendingKey";
    private ArrayList<Service> pendingRequest;

    public static PendingServicesFragment newInstance() {
        Bundle args = new Bundle();

        PendingServicesFragment fragment = new PendingServicesFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public static PendingServicesFragment newInstance(ArrayList<Service> pending) {
        Bundle args = new Bundle();
        args.putString(PendingKey, new Gson().toJson(pending));
        PendingServicesFragment fragment = new PendingServicesFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            String jsonString = getArguments().getString(PendingKey);

            if (jsonString != null) {
                pendingRequest = new Gson().fromJson(jsonString, new TypeToken<List<Service>>() {
                }.getType());
            }
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pending_services, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        prefHelper.setIsFromPending(true);

        if (pendingRequest != null && pendingRequest.size() > 0) {
            rvPendingJobs.setVisibility(View.VISIBLE);
            txtNoData.setVisibility(View.GONE);
            Collections.reverse(pendingRequest);
            rvPendingJobs.bindRecyclerView(new PendingServiceBinder(ItemClicklistener, false), pendingRequest, new LinearLayoutManager(getDockActivity(), LinearLayoutManager.VERTICAL, false), new DefaultItemAnimator());

        } else {
            rvPendingJobs.setVisibility(View.GONE);
            txtNoData.setVisibility(View.VISIBLE);
        }

        //  serviceHelper.enqueueCall(webService.getServicesStatus(prefHelper.getUser().getId() + "", AppConstants.SERVICE), SERVICESTATUS);

    }

   /* @Override
    public void ResponseSuccess(Object result, String Tag, String message) {
        super.ResponseSuccess(result, Tag, message);
        switch (Tag) {
            case SERVICESTATUS:
                ServicesStatus servicesStatus = (ServicesStatus) result;


                if (servicesStatus.getService().size() > 0) {
                    rvPendingJobs.setVisibility(View.VISIBLE);
                    txtNoData.setVisibility(View.GONE);
                    rvPendingJobs.bindRecyclerView(new PendingServiceBinder(ItemClicklistener), servicesStatus.getService(), new LinearLayoutManager(getDockActivity(), LinearLayoutManager.VERTICAL, false), new DefaultItemAnimator());

                } else {
                    rvPendingJobs.setVisibility(View.GONE);
                    txtNoData.setVisibility(View.VISIBLE);
                }


                break;
        }
    }*/


    private RecyclerItemListener ItemClicklistener = ((ent, position, id) -> {
        switch (id) {
            case R.id.btnCallRate:
                Service data = (Service) ent;
                Intent intent = new Intent(Intent.ACTION_DIAL);
                if (data.getAssignTechnician().getTechnicianDetails().getPhoneNo() != null && !data.getAssignTechnician().getTechnicianDetails().getPhoneNo().equals("")) {
                    if (data.getAssignTechnician().getTechnicianDetails().getCountryCode() != null && !data.getAssignTechnician().getTechnicianDetails().getCountryCode().equals("")) {
                        intent.setData(Uri.parse("tel:" + data.getAssignTechnician().getTechnicianDetails().getCountryCode() + data.getAssignTechnician().getTechnicianDetails().getPhoneNo()));
                    } else {
                        intent.setData(Uri.parse("tel:" + data.getAssignTechnician().getTechnicianDetails().getPhoneNo()));
                    }

                } else {
                    intent.setData(Uri.parse("tel:0123456789"));
                }
                startActivity(intent);
                getMainActivity().closeDrawer();
                break;
            case R.id.btnDetails:

                Service entity = (Service) ent;
                getDockActivity().replaceDockableFragment(ServiceDetailFragment.newInstance(false, entity), ServiceDetailFragment.TAG);
                break;
        }
    });


}
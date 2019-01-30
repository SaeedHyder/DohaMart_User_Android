package com.ingic.ezhalbatek.fragments;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ingic.ezhalbatek.R;
import com.ingic.ezhalbatek.entities.SubscriptionPackagesEnt;
import com.ingic.ezhalbatek.fragments.abstracts.BaseFragment;
import com.ingic.ezhalbatek.helpers.UIHelper;
import com.ingic.ezhalbatek.interfaces.RecyclerItemListener;
import com.ingic.ezhalbatek.ui.binders.SubscriptionPackageBinder;
import com.ingic.ezhalbatek.ui.views.AnyTextView;
import com.ingic.ezhalbatek.ui.views.CustomRecyclerView;
import com.ingic.ezhalbatek.ui.views.TitleBar;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.ingic.ezhalbatek.global.WebServiceConstants.AllPackages;

public class SubscriptionsPackagesFragment extends BaseFragment {
    @BindView(R.id.rv_subPackages)
    CustomRecyclerView rvSubPackages;
    Unbinder unbinder;
    @BindView(R.id.iv_image)
    ImageView ivImage;
    @BindView(R.id.txt_no_data)
    AnyTextView txtNoData;

    public static SubscriptionsPackagesFragment newInstance() {
        Bundle args = new Bundle();

        SubscriptionsPackagesFragment fragment = new SubscriptionsPackagesFragment();
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
        View view = inflater.inflate(R.layout.fragment_package_type, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvSubPackages.setNestedScrollingEnabled(false);
        serviceHelper.enqueueCall(webService.getAllPackages(), AllPackages);

    }

    @Override
    public void ResponseSuccess(Object result, String Tag, String message) {
        super.ResponseSuccess(result, Tag, message);
        switch (Tag){
            case AllPackages:
                bindViews((ArrayList<SubscriptionPackagesEnt>) result);
                break;
        }
    }

    private void bindViews(ArrayList<SubscriptionPackagesEnt> result) {

        if (result != null && result.size() > 0) {
            rvSubPackages.setVisibility(View.VISIBLE);
            txtNoData.setVisibility(View.GONE);
            rvSubPackages.bindRecyclerView(new SubscriptionPackageBinder(ItemClicklistener), result, new LinearLayoutManager(getDockActivity(), LinearLayoutManager.VERTICAL, false), new DefaultItemAnimator());

        } else {
            rvSubPackages.setVisibility(View.GONE);
            txtNoData.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
        titleBar.showBackButton();
        titleBar.setSubHeading(getResString(R.string.offer_packages));
    }

    private RecyclerItemListener ItemClicklistener = ((ent, position, id) -> {
        switch (id) {
            case R.id.btn_platinium:
                SubscriptionPackagesEnt data=(SubscriptionPackagesEnt)ent;
                getDockActivity().replaceDockableFragment(SubscriptionPackageDetail.newInstance(data),"SubscriptionPackageDetail");
                break;

        }
    });


}

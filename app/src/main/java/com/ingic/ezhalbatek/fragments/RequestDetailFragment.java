package com.ingic.ezhalbatek.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.ingic.ezhalbatek.R;
import com.ingic.ezhalbatek.entities.AllCategoriesEnt;
import com.ingic.ezhalbatek.fragments.abstracts.BaseFragment;
import com.ingic.ezhalbatek.ui.views.AnyTextView;
import com.ingic.ezhalbatek.ui.views.TitleBar;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created on 5/24/18.
 */
public class RequestDetailFragment extends BaseFragment {
    public static final String TAG = "RequestDetailFragment";
    @BindView(R.id.imgCategoryImage)
    ImageView imgCategoryImage;
    @BindView(R.id.txtDescriptionTitle)
    AnyTextView txtDescriptionTitle;
    @BindView(R.id.txtDescriptionDetail)
    AnyTextView txtDescriptionDetail;
    @BindView(R.id.btnRequest)
    Button btnRequest;
    Unbinder unbinder;
    private String titleHeading = "";
    private static String ALLSERVICESKEY = "ALLSERVICESKEY";
    private AllCategoriesEnt allCategoriesEnt;
    private ImageLoader imageLoader;

    public static RequestDetailFragment newInstance(String titleHeading, AllCategoriesEnt ent) {
        Bundle args = new Bundle();
        args.putString(ALLSERVICESKEY, new Gson().toJson(ent));
        RequestDetailFragment fragment = new RequestDetailFragment();
        fragment.setArguments(args);
        fragment.setTitleHeading(titleHeading);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        imageLoader = ImageLoader.getInstance();
        if (getArguments() != null) {
            String jsonString = getArguments().getString(ALLSERVICESKEY);

            if (jsonString != null) {
                allCategoriesEnt = new Gson().fromJson(jsonString, AllCategoriesEnt.class);
            }
        }

    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
        titleBar.showBackButton();
        titleBar.setSubHeading(titleHeading);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_request_detail, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setData();

    }

    private void setData() {
        if (allCategoriesEnt != null) {
        //    imageLoader.displayImage(allCategoriesEnt.getDetailImage(), imgCategoryImage);
            Picasso.with(getDockActivity()).load(allCategoriesEnt.getDetailImage()).placeholder(R.drawable.placeholder1).into(imgCategoryImage);
            txtDescriptionDetail.setText(allCategoriesEnt.getDescription() + "");
        }
    }


    @OnClick(R.id.btnRequest)
    public void onViewClicked() {
        getDockActivity().replaceDockableFragment(BookRequestFragment.newInstance(allCategoriesEnt.getId() + ""), BookRequestFragment.TAG);
    }

    public void setTitleHeading(String titleHeading) {
        this.titleHeading = titleHeading;
    }
}
package com.ingic.ezhalbatek.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.ingic.ezhalbatek.R;
import com.ingic.ezhalbatek.fragments.abstracts.BaseFragment;
import com.ingic.ezhalbatek.ui.views.AnyTextView;

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

    public static RequestDetailFragment newInstance() {
        Bundle args = new Bundle();

        RequestDetailFragment fragment = new RequestDetailFragment();
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
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_request_detail, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.btnRequest)
    public void onViewClicked() {
        getDockActivity().replaceDockableFragment(BookRequestFragment.newInstance(),BookRequestFragment.TAG);
    }
}
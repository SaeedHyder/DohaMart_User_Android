package com.ingic.ezhalbatek.fragments;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ingic.ezhalbatek.R;
import com.ingic.ezhalbatek.entities.CMSEnt;
import com.ingic.ezhalbatek.fragments.abstracts.BaseFragment;
import com.ingic.ezhalbatek.global.AppConstants;
import com.ingic.ezhalbatek.global.WebServiceConstants;
import com.ingic.ezhalbatek.retrofit.WebService;
import com.ingic.ezhalbatek.ui.views.TitleBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.ingic.ezhalbatek.global.WebServiceConstants.CMS;

/**
 * Created on 6/5/18.
 */
public class AboutAppFragment extends BaseFragment {
    public static final String TAG = "AboutAppFragment";
    @BindView(R.id.scroll_tv1)
    TextView scrollTv1;
    Unbinder unbinder;

    private static String Key;
    private static String TitleKey;

    public static AboutAppFragment newInstance(String dataKey,String title) {
        Bundle args = new Bundle();
        Key=dataKey;
        TitleKey=title;
        AboutAppFragment fragment = new AboutAppFragment();
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
        View view = inflater.inflate(R.layout.fragment_about_app, container, false);
        unbinder = ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        serviceHelper.enqueueCall(webService.CMS(Key), CMS);
    }

    @Override
    public void ResponseSuccess(Object result, String Tag, String message) {
        super.ResponseSuccess(result, Tag, message);
        switch (Tag){
            case CMS:
                CMSEnt ent=(CMSEnt)result;
                scrollTv1.setText(ent.getBody());
                scrollTv1.setMovementMethod(new ScrollingMovementMethod());

                break;

        }
    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
        titleBar.showBackButton();
        titleBar.setSubHeading(TitleKey);
    }

}
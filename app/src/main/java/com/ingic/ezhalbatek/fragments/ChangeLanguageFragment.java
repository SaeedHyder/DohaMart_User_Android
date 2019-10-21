package com.ingic.ezhalbatek.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ingic.ezhalbatek.R;
import com.ingic.ezhalbatek.fragments.abstracts.BaseFragment;
import com.ingic.ezhalbatek.ui.views.AnyTextView;
import com.ingic.ezhalbatek.ui.views.TitleBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created on 6/5/18.
 */
public class ChangeLanguageFragment extends BaseFragment {
    public static final String TAG = "ChangeLanguageFragment";
    @BindView(R.id.btnEnglist)
    AnyTextView btnEnglist;
    @BindView(R.id.btnArabic)
    AnyTextView btnArabic;

    public static ChangeLanguageFragment newInstance() {
        Bundle args = new Bundle();

        ChangeLanguageFragment fragment = new ChangeLanguageFragment();
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
        View view = inflater.inflate(R.layout.fragment_change_language, container, false);
      ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }
    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
        titleBar.showBackButton();
        titleBar.setSubHeading(getResString(R.string.change_language));
    }


    @OnClick({R.id.btnEnglist, R.id.btnArabic})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnEnglist:
                prefHelper.putLang(getDockActivity(), "en");
                break;
            case R.id.btnArabic:
                prefHelper.putLang(getDockActivity(), "ar");
                break;
        }
    }

}
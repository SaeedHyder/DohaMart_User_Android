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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.ingic.ezhalbatek.global.WebServiceConstants.CMS;

/**
 * Created on 6/5/18.
 */
public class TermsConditionFragment extends BaseFragment {
    public static final String TAG = "TermsConditionFragment";
    @BindView(R.id.txt_term_condition)
    TextView txtTermCondition;
    Unbinder unbinder;

    private static String Key;
    private static String TitleKey;

    public static TermsConditionFragment newInstance(String dataKey,String title) {
        Bundle args = new Bundle();
        Key=dataKey;
        TitleKey=title;
        TermsConditionFragment fragment = new TermsConditionFragment();
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
        View view = inflater.inflate(R.layout.fragment_term_condition, container, false);
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

        serviceHelper.enqueueCall(webService.CMS(Key), CMS);

    }

    @Override
    public void ResponseSuccess(Object result, String Tag, String message) {
        super.ResponseSuccess(result, Tag, message);
        switch (Tag){
            case CMS:
                CMSEnt ent=(CMSEnt)result;
                txtTermCondition.setText(ent.getBody());
                txtTermCondition.setMovementMethod(new ScrollingMovementMethod());

                break;

        }
    }
}
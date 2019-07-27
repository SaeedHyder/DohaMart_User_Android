package com.ingic.ezhalbatek.fragments;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.ingic.ezhalbatek.R;
import com.ingic.ezhalbatek.fragments.abstracts.BaseFragment;
import com.ingic.ezhalbatek.interfaces.TutorialInterface;
import com.ingic.ezhalbatek.ui.adapters.TutorialAdapter;
import com.ingic.ezhalbatek.ui.views.AnyTextView;
import com.ingic.ezhalbatek.ui.views.TitleBar;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import me.relex.circleindicator.CircleIndicator;

public class TutorialFragment extends BaseFragment implements TutorialInterface {
    @BindView(R.id.viewpager)
    ViewPager viewPager;
    @BindView(R.id.indicator)
    CircleIndicator indicator;
    Unbinder unbinder;
    @BindView(R.id.btnNext)
    Button btnNext;

    private ArrayList<String> imagesCollection;
    private TutorialAdapter tutorialAdapter;

    public static TutorialFragment newInstance() {
        Bundle args = new Bundle();

        TutorialFragment fragment = new TutorialFragment();
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
        View view = inflater.inflate(R.layout.fragment_tutorial, container, false);
        unbinder = ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setViewPager();

    }

    private void setViewPager() {

        imagesCollection = new ArrayList<>();
        imagesCollection.add("drawable://" + R.drawable.tutorial2);
        imagesCollection.add("drawable://" + R.drawable.tutorial3);
        imagesCollection.add("drawable://" + R.drawable.tutorial1);

        tutorialAdapter = new TutorialAdapter(getMainActivity(), imagesCollection,this);
        viewPager.setAdapter(tutorialAdapter);
        indicator.setViewPager(viewPager);
    }


    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideTitleBar();
    }


    @OnClick({ R.id.btnNext})
    public void onViewClicked(View view) {
        switch (view.getId()) {
           /* case R.id.btnSkip:
                getDockActivity().popBackStackTillEntry(0);
                getDockActivity().replaceDockableFragment(LoginFragment.newInstance(), "LoginFragment");
                break;*/
            case R.id.btnNext:
                if (viewPager.getCurrentItem() == tutorialAdapter.getCount()-1) {
                    getDockActivity().popBackStackTillEntry(0);
                    getDockActivity().replaceDockableFragment(LoginFragment.newInstance(), "LoginFragment");
                } else {
                    viewPager.setCurrentItem(viewPager.getCurrentItem() + 1, true);
                }
                break;
        }
    }

    @Override
    public void onClick() {

    }
}

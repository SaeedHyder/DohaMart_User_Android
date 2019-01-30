package com.ingic.ezhalbatek.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.ingic.ezhalbatek.R
import com.ingic.ezhalbatek.fragments.abstracts.BaseFragment
import com.ingic.ezhalbatek.ui.views.KotterKnife
import com.ingic.ezhalbatek.ui.views.TitleBar
import com.ingic.ezhalbatek.ui.views.bindView

/**
 * Created on 5/21/18.
 */
class UserTypeFragment : BaseFragment() {
    private val btnSubscribe: Button by bindView(R.id.btn_subscribe)
    private val btnTwentySeven: Button by bindView(R.id.btn_twenty_seven)

    companion object {
        val Tag: String = "UserTypeFragment"
        fun newInstance(): UserTypeFragment {
            val fragment = UserTypeFragment()
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun setTitleBar(titleBar: TitleBar) {
        super.setTitleBar(titleBar)
        titleBar.hideButtons()
        titleBar.setSubHeading(getResString(R.string.select_menu))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //  btnSubscribe.setOnClickListener { _ ->    dockActivity.replaceDockableFragment(SubscriptionTypesFragment.newInstance(), SubscriptionTypesFragment.Tag)}
        btnSubscribe.setOnClickListener { _ -> dockActivity.replaceDockableFragment(SubscriptionsPackagesFragment.newInstance(), "SubscriptionsPackagesFragment") }
        btnTwentySeven.setOnClickListener { _ ->
            dockActivity.popBackStackTillEntry(0)
            dockActivity.replaceDockableFragment(HomeFragment.newInstance(), "HomeFragment")
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        KotterKnife.reset(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_usertype, container, false)
    }
}
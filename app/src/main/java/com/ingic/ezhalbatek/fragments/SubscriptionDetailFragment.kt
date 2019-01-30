package com.ingic.ezhalbatek.fragments

import android.app.Activity.RESULT_CANCELED
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.support.v4.view.ViewPager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.google.android.gms.location.places.ui.PlaceAutocomplete
import com.google.android.gms.location.places.ui.PlacePicker
import com.ingic.ezhalbatek.R
import com.ingic.ezhalbatek.fragments.abstracts.BaseFragment
import com.ingic.ezhalbatek.ui.adapters.ViewPagerAdapter
import com.ingic.ezhalbatek.ui.views.KotterKnife
import com.ingic.ezhalbatek.ui.views.TitleBar
import com.ingic.ezhalbatek.ui.views.bindView


/**
 * Created on 5/21/18.
 */
class SubscriptionDetailFragment : BaseFragment() {
    private val pager by bindView<ViewPager>(R.id.pager)
    private val btnContinue by bindView<Button>(R.id.btn_continue)
    private var PLACE_AUTOCOMPLETE_REQUEST_CODE = 1
    private var address = ""
    private var addressLat = ""
    private var addressLng = ""
    private val pagerAdapter: ViewPagerAdapter  by lazy { ViewPagerAdapter(childFragmentManager) }

    companion object {
        val Tag: String = "SubscriptionFragment"
        fun newInstance(): SubscriptionDetailFragment {
            val fragment = SubscriptionDetailFragment()
            return fragment
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        KotterKnife.reset(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindViewPager()
        btnContinue.setOnClickListener { _ -> openLocationSelector() }
    }
    override fun setTitleBar(titleBar: TitleBar) {
        super.setTitleBar(titleBar)
        titleBar.hideButtons()
        titleBar.showBackButton()
        titleBar.setSubHeading(getResString(R.string.subscriptions))
    }

    private fun openLocationSelector() {

        try {
            /* Intent intent =
                    new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN)
                            .build(getDockActivity());*/
            val builder = PlacePicker.IntentBuilder()

            this.startActivityForResult(builder.build(mainActivity), PLACE_AUTOCOMPLETE_REQUEST_CODE)
            //this.startActivityForResult(intent, PLACE_AUTOCOMPLETE_REQUEST_CODE);
        } catch (e: Exception) {

            e.printStackTrace()
        }


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PLACE_AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {

                val place = PlaceAutocomplete.getPlace(dockActivity, data)
                if (place != null) {
                    address = place.address.toString()
                    addressLat = place.latLng.latitude.toString() + ""
                    addressLng = place.latLng.longitude.toString() + ""
                    dockActivity.replaceDockableFragment(PackageDetailFragment.newInstance(),PackageDetailFragment.TAG)
                }
                Log.i(Tag, "Place: " + place!!.name)

            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                val status = PlaceAutocomplete.getStatus(dockActivity, data)
                // TODO: Handle the error.
                Log.i(Tag, status.statusMessage)

            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }
        }

    }

    private fun bindViewPager() {
        for (i in 1..5) {
          //  pagerAdapter.addFragment(ItemPackageDetailFragment.newInstance())
        }
        setPagerSetting()
        pager.adapter = pagerAdapter
    }

    private fun setPagerSetting() {
        pager.clipToPadding = false
        pager.pageMargin = 10
        /* pager.setPadding(20, 8, 20, 8);
    pager.setOffscreenPageLimit(3);*/
        pager.setPageTransformer(false) { page, position ->
            val pageWidth = pager.measuredWidth - pager.paddingLeft - pager.paddingRight
            val pageHeight = pager.height
            val paddingLeft = pager.paddingLeft
            val transformPos = (page.left - (pager.scrollX + paddingLeft)).toFloat() / pageWidth
            val max = pageHeight / 10

            if (transformPos < -1) { // [-Infinity,-1)
                // This page is way off-screen to the left.
                page.alpha = 0.7f// to make left transparent
                page.scaleY = 0.9f
            } else if (transformPos <= 1) { // [-1,1]
                page.alpha = 1f
                page.scaleY = 1f
            } else { // (1,+Infinity]
                // This page is way off-screen to the right.
                page.alpha = 0.7f// to make right transparent
                page.scaleY = 0.9f
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_subscription_detail, container, false)
    }
}
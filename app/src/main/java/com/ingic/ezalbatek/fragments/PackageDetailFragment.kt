package com.ingic.ezalbatek.fragments

import com.ingic.ezalbatek.fragments.abstracts.BaseFragment;
import android.os.Bundle;
import android.support.v4.view.ViewPager
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.ingic.ezalbatek.R
import com.ingic.ezalbatek.ui.adapters.ViewPagerAdapter
import com.ingic.ezalbatek.ui.views.KotterKnife
import com.ingic.ezalbatek.ui.views.bindView
import com.nineoldandroids.view.ViewHelper.getScrollX
import android.opengl.ETC1.getHeight



/**
 * Created on 5/21/18.
 */
class PackageDetailFragment : BaseFragment() {
    private val pager: ViewPager by bindView(R.id.pager)

    private val pagerAdapter: ViewPagerAdapter = ViewPagerAdapter(childFragmentManager)

    companion object {
        val Tag: String = "PackageDetailFragment"
        fun newInstance(): PackageDetailFragment {
            val fragment = PackageDetailFragment()
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
    }

    private fun setPagerSetting() {
        pager.clipToPadding = false
        pager.pageMargin = 10
        /* pager.setPadding(20, 8, 20, 8);
    pager.setOffscreenPageLimit(3);*/
        pager.setPageTransformer(false) { page, position ->
            val pageWidth = pager.measuredWidth -
                    pager.paddingLeft - pager.paddingRight
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
        return inflater.inflate(R.layout.fragment_package_detail, container, false)
    }
}
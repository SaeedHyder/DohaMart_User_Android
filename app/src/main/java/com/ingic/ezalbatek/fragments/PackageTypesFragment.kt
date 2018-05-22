package com.ingic.ezalbatek.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.ingic.ezalbatek.R
import com.ingic.ezalbatek.fragments.abstracts.BaseFragment
import com.ingic.ezalbatek.ui.views.KotterKnife
import com.ingic.ezalbatek.ui.views.bindView

/**
 * Created on 5/21/18.
 */
class PackageTypesFragment : BaseFragment() {
    private val btnPlatinium: LinearLayout by bindView(R.id.btn_platinium)
    private val btnGold: LinearLayout by bindView(R.id.btn_gold)
    private val btnSilver: LinearLayout by bindView(R.id.btn_silver)
    private val clicklistener: View.OnClickListener = View.OnClickListener { v ->
        when (v.id) {
            R.id.btn_platinium -> {

            }
            R.id.btn_gold -> {

            }
            R.id.btn_silver -> {

            }
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        KotterKnife.reset(this)
    }
    companion object {
        val Tag: String = "PackageTypesFragment"
        fun newInstance(): PackageTypesFragment {
            val fragment = PackageTypesFragment()
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnPlatinium.setOnClickListener(clicklistener)
        btnGold.setOnClickListener(clicklistener)
        btnSilver.setOnClickListener(clicklistener)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_package_type, container, false)
    }
}
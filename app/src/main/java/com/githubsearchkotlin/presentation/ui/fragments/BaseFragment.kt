package com.githubsearchkotlin.presentation.ui.fragments

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import com.githubsearchkotlin.R
import com.githubsearchkotlin.base.viper.HideShowContentView
import dagger.android.support.DaggerFragment
import io.reactivex.annotations.Nullable

abstract class BaseFragment : DaggerFragment(), HideShowContentView {

    @BindView(R.id.loadingView)
    @Nullable
    lateinit var loadingView: View

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ButterKnife.bind(view)
    }

    override fun showLoading() {

            loadingView.visibility = View.VISIBLE

    }

    override fun hideLoading() {
            loadingView.visibility = View.GONE

    }

    override fun showMessageSnack(msg: String) {
        if (activity != null) {
            val snackbar = Snackbar.make(activity!!.findViewById(android.R.id.content), msg, 1500)
            val view = snackbar.view
            view.setBackgroundColor(ContextCompat.getColor(activity!!, R.color.colorPrimary))
            val text = view.findViewById<TextView>(android.support.design.R.id.snackbar_text)
            text.setTextColor(ContextCompat.getColor(activity!!, R.color.colorWhite))
            text.textAlignment = View.TEXT_ALIGNMENT_CENTER
            snackbar.show()
        }
    }

}
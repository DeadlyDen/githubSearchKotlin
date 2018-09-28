package com.githubsearchkotlin.presentation.ui.activities

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.view.View
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import com.githubsearchkotlin.R
import com.githubsearchkotlin.base.viper.HideShowContentView
import com.githubsearchkotlin.presentation.ui.utils.RxBus
import dagger.android.support.DaggerAppCompatActivity
import dagger.android.support.HasSupportFragmentInjector
import io.reactivex.annotations.Nullable

abstract class BaseActivity : DaggerAppCompatActivity(), HasSupportFragmentInjector, HideShowContentView {


    @BindView(R.id.loadingView)
    @Nullable
    lateinit var loadingView: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onDestroy() {
        super.onDestroy()
        RxBus.unregister(this)
    }

    override fun showLoading() {
        loadingView.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        loadingView.visibility = View.GONE
    }

    override fun showMessageSnack(msg: String) {
        val snackbar = Snackbar.make(findViewById(android.R.id.content), msg, 1500)
        val view = snackbar.view
        view.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary))
        val text = view.findViewById<TextView>(android.support.design.R.id.snackbar_text)
        text.setTextColor(ContextCompat.getColor(this, R.color.colorWhite))
        text.textAlignment = View.TEXT_ALIGNMENT_CENTER
        snackbar.show()
    }
}
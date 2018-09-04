package com.githubsearchkotlin.presentation.ui.activities.login

import android.util.Log
import com.githubsearchkotlin.base.viper.BasePresenter
import com.githubsearchkotlin.base.viper.InteractorCallback
import com.githubsearchkotlin.data.model.UserResponse
import com.githubsearchkotlin.domain.LoginInteractor
import com.githubsearchkotlin.presentation.ui.routing.LoginRouter
import com.githubsearchkotlin.presentation.ui.utils.UiUtils
import okhttp3.Credentials
import javax.inject.Inject

class LoginPresenter @Inject constructor(val loginInteractor: LoginInteractor,
                                         loginRouter: LoginRouter) : BasePresenter<LoginView, LoginRouter>(), InteractorCallback<UserResponse> {

    init {
        loginInteractor.callback = this
        router = loginRouter
    }

    fun login(email: String, password: String) {
        mvpView?.showLoading()
        hideKeyboart()
        loginInteractor.credential = Credentials.basic(email, password)
        loginInteractor.doLlogin()
    }

    override fun onSuccess(model: UserResponse?, onMore: Boolean) {
        mvpView?.showMessageSnack(model?.login!!)
        mvpView?.hideLoading()
        router?.let {
            UiUtils.closeKeyboard(it as LoginActivity)
            it.starSearchRepoActivity(model!!)
        }
    }

    override fun onFailure(throwable: Throwable?) {
        Log.v("User response", throwable?.toString())
        mvpView?.hideLoading()
        hideKeyboart()
        mvpView?.showMessageSnack(throwable.toString())
    }

    fun hideKeyboart() {
        router?.let {
            UiUtils.closeKeyboard(it as LoginActivity)
        }
    }


}
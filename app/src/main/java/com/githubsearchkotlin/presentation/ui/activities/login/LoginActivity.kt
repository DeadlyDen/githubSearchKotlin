package com.githubsearchkotlin.presentation.ui.activities.login

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.TextInputLayout
import android.widget.*
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.githubsearchkotlin.R
import com.githubsearchkotlin.base.viper.HideShowContentView
import com.githubsearchkotlin.base.viper.View
import com.githubsearchkotlin.data.model.UserResponse
import com.githubsearchkotlin.presentation.ui.activities.BaseActivity
import com.githubsearchkotlin.presentation.ui.activities.main.MainActivity
import com.githubsearchkotlin.presentation.ui.routing.LoginRouter
import com.githubsearchkotlin.presentation.ui.utils.TextInputValidator
import java.util.*
import javax.inject.Inject

interface LoginView : View, HideShowContentView

class LoginActivity : BaseActivity(), LoginView, LoginRouter {

    @BindView(R.id.login_form)
    lateinit var loginForm: ScrollView
    @BindView(R.id.email_login_form)
    lateinit var emailLoginForm: LinearLayout
    @BindView(R.id.email_in)
    lateinit var emailIn: TextInputLayout
    @BindView(R.id.email)
    lateinit var email: AutoCompleteTextView
    @BindView(R.id.password_in)
    lateinit var passwordIn: TextInputLayout
    @BindView(R.id.password)
    lateinit var password: EditText
    @BindView(R.id.email_sign_in_button)
    lateinit var emailSignInButton: Button

    @Inject
    lateinit var loginPresenter: LoginPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        ButterKnife.bind(this)

        loginPresenter.attachView(this)
    }

    @OnClick(R.id.email_sign_in_button)
    fun login() {
        if (TextInputValidator.validateFields(Arrays.asList<EditText>(email, password), Arrays.asList<TextInputLayout>(emailIn, passwordIn),
                        Arrays.asList(resources.getString(R.string.email_or_username_empty_en), resources.getString(R.string.password_empty_en)))) {
            showLoading()
            loginPresenter.login(email.text.toString(), password.text.toString())
        }
    }

    override fun starSearchRepoActivity(userResponse: UserResponse) {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }


}
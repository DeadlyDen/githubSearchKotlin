package com.githubsearchkotlin.presentation.ui.utils

import android.support.design.widget.TextInputLayout
import android.widget.EditText


object TextInputValidator {

    fun validateFields(fields: List<EditText>, textInputLayouts: List<TextInputLayout>, massages: List<String>): Boolean {
        var isValid = false
        for (editText in fields) {
            val cIndex = fields.indexOf(editText)
            if (editText.text.length != 0) {
                textInputLayouts[cIndex].error = null
                isValid = true
            } else {
                textInputLayouts[cIndex].error = massages[cIndex]
                isValid = false
                break
            }
        }
        return isValid
    }

    fun validateFields(editText: EditText, textInputLayout: TextInputLayout, errorMsg: String): Boolean {
        if (editText.text.length == 0) {
            textInputLayout.error = errorMsg
            return false
        } else {
            textInputLayout.error = null
            return true
        }
    }
}

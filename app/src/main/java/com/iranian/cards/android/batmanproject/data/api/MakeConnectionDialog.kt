package com.iranian.cards.android.batmanproject.data.api

import android.app.Activity
import com.iranian.cards.android.batmanproject.App
import com.iranian.cards.android.batmanproject.R
import com.iranian.cards.android.batmanproject.data.api.retrofit.DialogType
import com.iranian.cards.android.batmanproject.data.api.retrofit.ErrorInterceptor
import com.iranian.cards.android.batmanproject.utils.Constants
import com.iranian.cards.android.batmanproject.widgets.CustomDialog


class MakeConnectionDialog {
    private fun makeDialog(
        activity: Activity,
        title: String,
        message: String,
        setPositiveButton: Boolean,
        setNegativeButton: Boolean,
        connectionDialog: Boolean,
        endpoint: String
    ): CustomDialog {
        val dialog = CustomDialog.with(activity, Constants.DIALOG_STYLE)
        dialog.setCancelable(false)
        dialog.title(title)
        dialog.message(message)
        if (setPositiveButton) {
            if (connectionDialog) {
                dialog.positiveButton(
                    App.instance?.applicationContext?.getString(R.string.retry)!!
                ) { dialog1 ->
                    if (ErrorInterceptor.exceptionListener != null)
                        ErrorInterceptor.exceptionListener?.onRetrySelected(
                            dialog1, endpoint
                        )
                    dialog.dismiss()
                }
            } else {
                dialog.positiveButton(
                    App.instance?.applicationContext?.getString(R.string.retry)!!
                ) { dialog1 ->
                    if (ErrorInterceptor.exceptionListener != null)
                        ErrorInterceptor.exceptionListener?.onRetrySelected(
                            dialog1, endpoint
                        )
                    dialog.dismiss()

                }
            }
        }
        if (setNegativeButton) dialog.negativeButton(
            App.instance?.applicationContext?.getString(R.string.exit)!!
        ) { dialog12 ->
            if (ErrorInterceptor.exceptionListener != null)
                ErrorInterceptor.exceptionListener?.onExitSelected(
                    dialog12, endpoint
                )
            dialog.dismiss()
        }
        return dialog
    }

    fun createDialog(
        activity: Activity,
        type: DialogType,
        endpoint: String,
        errorMessage: String = ""
    ): CustomDialog {
        when (type) {
            DialogType.CONNECTION_DIALOG -> {
                return makeDialog(
                    activity,
                    App.instance?.applicationContext!!.getString(R.string.is_internet_on),
                    App.instance?.applicationContext?.getString(R.string.user_is_offline)!!,
                    setPositiveButton = true,
                    setNegativeButton = true,
                    connectionDialog = true,
                    endpoint = endpoint
                )
            }
            DialogType.SOCKET_TIMEOUT_DIALOG -> {
                return makeDialog(
                    activity,
                    App.instance?.applicationContext!!.getString(R.string.error_connection),
                    App.instance?.applicationContext?.getString(R.string.socket_timeout)!!,
                    setPositiveButton = true,
                    setNegativeButton = true,
                    connectionDialog = false,
                    endpoint = endpoint
                )
            }
            DialogType.USER_LIMITED_DIALOG -> {
                return makeDialog(
                    activity,
                    App.instance?.applicationContext!!.getString(R.string.error),
                    App.instance?.applicationContext?.getString(R.string.User_limited)!!,
                    setPositiveButton = false,
                    setNegativeButton = true,
                    connectionDialog = false,
                    endpoint = endpoint
                )
            }
            DialogType.CLIENT_PROBLEM_DIALOG -> {
                return makeDialog(
                    activity,
                    App.instance?.applicationContext!!.getString(R.string.problem),
                    App.instance?.applicationContext?.getString(R.string.client_problem)!!,
                    setPositiveButton = false,
                    setNegativeButton = true,
                    connectionDialog = false,
                    endpoint = endpoint
                )
            }
            DialogType.LOGIC_PROBLEM_DIALOG -> {
                return makeDialog(
                    activity,
                    App.instance?.applicationContext?.getString(R.string.client_problem)!!,
                    errorMessage,
                    setPositiveButton = false,
                    setNegativeButton = true,
                    connectionDialog = false,
                    endpoint = endpoint
                )
            }
            DialogType.SERVER_PROBLEM_DIALOG -> {
                return makeDialog(
                    activity,
                    App.instance?.applicationContext!!.getString(R.string.problem),
                    App.instance?.applicationContext?.getString(R.string.server_problem)!!,
                    setPositiveButton = true,
                    setNegativeButton = true,
                    connectionDialog = false,
                    endpoint = endpoint
                )
            }
            DialogType.UNKNOWN_PROBLEM_DIALOG -> {
                return makeDialog(
                    activity,
                    App.instance?.applicationContext!!.getString(R.string.problem),
                    App.instance?.applicationContext?.getString(R.string.unknown_problem)!!,
                    setPositiveButton = true,
                    setNegativeButton = true,
                    connectionDialog = false,
                    endpoint = endpoint
                )
            }
            DialogType.NONE -> {
                return makeDialog(
                    activity,
                    App.instance?.applicationContext!!.getString(R.string.problem),
                    App.instance?.applicationContext?.getString(R.string.unknown_problem)!!,
                    setPositiveButton = true,
                    setNegativeButton = true,
                    connectionDialog = false,
                    endpoint = endpoint
                )
            }
        }
    }
}
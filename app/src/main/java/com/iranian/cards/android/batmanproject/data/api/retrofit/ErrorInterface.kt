package com.iranian.cards.android.batmanproject.data.api.retrofit

import com.iranian.cards.android.batmanproject.widgets.CustomDialog


interface ErrorInterface {
    fun exception(
        exception: Exception,
        responseCode: Int,
        responseMessage: String,
        responseBody: String,
        dialogType: DialogType,
        endpoint: String
    )

    fun onRetrySelected(dialog: CustomDialog, endpoint: String)
    fun onExitSelected(dialog: CustomDialog, endpoint: String)

}
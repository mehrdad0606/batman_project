package com.iranian.cards.android.batmanproject.utils

import com.iranian.cards.android.batmanproject.data.api.retrofit.DialogType


data class Resource<out T>(
    val status: Status,
    val data: T?,
    val message: String?,
    val exception: Throwable?,
    val dialogType: DialogType
) {

    companion object {

        fun <T> success(data: T?, message: String): Resource<T> {
            return Resource(Status.SUCCESS, data, message, null, DialogType.NONE)
        }

        fun <T> error(data: T?, msg: String, exception: Throwable?): Resource<T> {
            return Resource(Status.ERROR, data, msg, exception, DialogType.NONE)
        }

        fun <T> loading(data: T?): Resource<T> {
            return Resource(Status.LOADING, data, null, null, DialogType.NONE)
        }

        fun <T> show(data: T?, endpoint: String, dialogType: DialogType): Resource<T> {
            return Resource(Status.SHOW, data, endpoint, null, dialogType)
        }

        fun <T> dismiss(data: T?, endpoint: String): Resource<T> {
            return Resource(Status.DISMISS, data, endpoint, null, DialogType.NONE)
        }

        fun <T> retry(data: T?, endpoint: String): Resource<T> {
            return Resource(Status.RETRY, data, endpoint, null, DialogType.NONE)
        }
    }
}
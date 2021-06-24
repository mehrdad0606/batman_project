package com.iranian.cards.android.batmanproject.data.api.retrofit

import android.os.Looper
import android.util.Log
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import java.net.SocketException
import java.net.SocketTimeoutException
import java.net.UnknownHostException


class ErrorInterceptor() : Interceptor {
    var token: String = ""

    companion object {
        var exceptionListener: ErrorInterface? = null
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        var responseCode: Int? = -1
        var responseMessage: String? = ""
        var responseBody: String? = ""
        var endpoint: String? = ""
        var response: Response? = null
        var newResponse: Response? = null
        var responseBodyString = ""
        try {
            val request: Request = chain.request()

            response = chain.proceed(request)

            val responseBody = response.body

            responseBodyString = response.body!!.string()

            newResponse = response.newBuilder()
                .body(responseBodyString.toResponseBody(responseBody!!.contentType())).build()

            responseCode = response.code
            endpoint = request.url.encodedPath

            if (responseCode != 200) {
                responseMessage = response.message

                throw java.lang.Exception()
            } else {
                return newResponse
            }
        } catch (exception: Exception) {
            endpoint = request.url.encodedPath
            if (Looper.myLooper() == null)
                Looper.prepare()
            if (exception is UnknownHostException || exception is SocketException) {
                exceptionListener?.exception(
                    exception,
                    responseCode!!,
                    responseMessage!!,
                    responseBody!!,
                    DialogType.CONNECTION_DIALOG,
                    endpoint
                )
                if (newResponse == null)
                    return chain.proceed(chain.request())
                return newResponse
            } else if (exception is SocketTimeoutException) {
                exceptionListener?.exception(
                    exception,
                    responseCode!!,
                    responseMessage!!,
                    responseBody!!,
                    DialogType.SOCKET_TIMEOUT_DIALOG, endpoint
                )
                if (newResponse == null)
                    return chain.proceed(chain.request())
                return newResponse
            } else {
                Log.e(
                    javaClass.simpleName,
                    "Error Code : " + responseCode + " | Error Message : " + responseMessage + " | Exception : " + exception.message
                )
                exceptionListener?.exception(
                    exception,
                    responseCode!!,
                    responseMessage!!,
                    responseBody!!,
                    DialogType.CLIENT_PROBLEM_DIALOG, endpoint
                )
                if (newResponse == null)
                    return chain.proceed(chain.request())
                return newResponse
            }
        }
    }

    fun setListener(errorInterface: ErrorInterface) {
        exceptionListener = errorInterface
    }
}
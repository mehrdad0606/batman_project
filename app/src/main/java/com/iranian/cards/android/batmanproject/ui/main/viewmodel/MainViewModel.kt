package com.iranian.cards.android.batmanproject.ui.main.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.iranian.cards.android.batmanproject.data.api.retrofit.DialogType
import com.iranian.cards.android.batmanproject.data.api.retrofit.ErrorInterceptor
import com.iranian.cards.android.batmanproject.data.api.retrofit.ErrorInterface
import com.iranian.cards.android.batmanproject.data.api.retrofit.model.MoviesResponse
import com.iranian.cards.android.batmanproject.data.repository.MainRepository
import com.iranian.cards.android.batmanproject.utils.CredentialManager
import com.iranian.cards.android.batmanproject.utils.Resource
import com.iranian.cards.android.batmanproject.utils.Status
import com.iranian.cards.android.batmanproject.widgets.CustomDialog
import kotlinx.coroutines.*

class MainViewModel(private val mainRepository: MainRepository) : ViewModel(),
    ErrorInterface {

    private var listener: ErrorInterceptor? = null
    private val _appStatus = MutableLiveData<Resource<Status>>()
    val appStatus: LiveData<Resource<Status>>
        get() = _appStatus
    private val _movies = MutableLiveData<Resource<MoviesResponse>>()
    val movies: LiveData<Resource<MoviesResponse>>
        get() = _movies

    //init listener for errorInterceptor
    fun initListener() {
        listener = ErrorInterceptor()
        listener?.setListener(this)
    }

    fun getMoviesTask() {
        _movies.postValue(Resource.loading(null))
        val mainActivityJob = Job()
        val errorHandler = CoroutineExceptionHandler { _, throwable ->
            throwable.message
        }
        val coroutineScope = CoroutineScope(Dispatchers.IO + mainActivityJob)
        coroutineScope.launch(errorHandler) {
            val response = mainRepository.getMoviesResponseFromServer("batman")
            CredentialManager.saveMoviesAsString(Gson().toJson(response))
            _movies.postValue(Resource.success(response, "get movies from api task"))
        }
    }

    //error handling if we have a problem in getting data from api
    override fun exception(
        exception: Exception,
        responseCode: Int,
        responseMessage: String,
        responseBody: String,
        dialogType: DialogType,
        endpoint: String
    ) {
        if (dialogType != DialogType.CONNECTION_DIALOG) {
            _appStatus.postValue(Resource.show(null, endpoint, dialogType))
        } else if (dialogType == DialogType.CONNECTION_DIALOG && CredentialManager.moviesAsString?.isNullOrEmpty()!!) {
            _appStatus.postValue(Resource.show(null, endpoint, dialogType))
        } else {
            _movies.postValue(
                Resource.success(
                    Gson().fromJson(
                        CredentialManager.moviesAsString,
                        MoviesResponse::class.java
                    ), "get movies from shared prefrences"
                )
            )
        }
    }

    override fun onRetrySelected(dialog: CustomDialog, endpoint: String) {
        _appStatus.postValue(Resource.retry(null, endpoint))
    }

    override fun onExitSelected(dialog: CustomDialog, endpoint: String) {
        _appStatus.postValue(Resource.dismiss(null, endpoint))
    }

}
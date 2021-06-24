package com.iranian.cards.android.batmanproject.ui.detail.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.iranian.cards.android.batmanproject.data.api.retrofit.DialogType
import com.iranian.cards.android.batmanproject.data.api.retrofit.ErrorInterceptor
import com.iranian.cards.android.batmanproject.data.api.retrofit.ErrorInterface
import com.iranian.cards.android.batmanproject.data.api.retrofit.model.MovieDetailsResponse
import com.iranian.cards.android.batmanproject.data.repository.MainRepository
import com.iranian.cards.android.batmanproject.utils.Resource
import com.iranian.cards.android.batmanproject.utils.Status
import com.iranian.cards.android.batmanproject.widgets.CustomDialog
import kotlinx.coroutines.*

class DetailViewModel(private val mainRepository: MainRepository) : ViewModel(),
    ErrorInterface {

    private var listener: ErrorInterceptor? = null
    private val _appStatus = MutableLiveData<Resource<Status>>()
    val appStatus: LiveData<Resource<Status>>
        get() = _appStatus
    private val _movieDetail = MutableLiveData<Resource<MovieDetailsResponse>>()
    val movieDetail: LiveData<Resource<MovieDetailsResponse>>
        get() = _movieDetail

    //init listener for errorInterceptor
    fun initListener() {
        listener = ErrorInterceptor()
        listener?.setListener(this)
    }

    fun getMovieDetailTask(imdbId: String) {
        _movieDetail.postValue(Resource.loading(null))
        val mainActivityJob = Job()
        val errorHandler = CoroutineExceptionHandler { _, throwable ->
            throwable.message
        }
        val coroutineScope = CoroutineScope(Dispatchers.IO + mainActivityJob)
        coroutineScope.launch(errorHandler) {
            val response = mainRepository.getMovieDetailResponseFromServer(imdbId)
            _movieDetail.postValue(Resource.success(response, "get movie detail task"))
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
        _appStatus.postValue(Resource.show(null, endpoint, dialogType))
    }

    override fun onRetrySelected(dialog: CustomDialog, endpoint: String) {
        _appStatus.postValue(Resource.retry(null, endpoint))
    }

    override fun onExitSelected(dialog: CustomDialog, endpoint: String) {
        _appStatus.postValue(Resource.dismiss(null, endpoint))
    }

}
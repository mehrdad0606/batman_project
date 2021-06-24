package com.iranian.cards.android.batmanproject.ui.detail.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.iranian.cards.android.batmanproject.data.api.MakeConnectionDialog
import com.iranian.cards.android.batmanproject.data.api.retrofit.model.MovieDetailsResponse
import com.iranian.cards.android.batmanproject.databinding.ActivityDetailBinding
import com.iranian.cards.android.batmanproject.ui.detail.viewmodel.DetailViewModel
import com.iranian.cards.android.batmanproject.utils.Constants
import com.iranian.cards.android.batmanproject.utils.Status
import com.iranian.cards.android.batmanproject.widgets.CustomDialog
import org.koin.android.viewmodel.ext.android.viewModel

class DetailActivity : AppCompatActivity() {

    private var mBind: ActivityDetailBinding? = null
    private val detailViewModel: DetailViewModel by viewModel()
    private var imdbId: String? = null
    private var customDialog: CustomDialog? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBind = ActivityDetailBinding.inflate(layoutInflater)
        detailViewModel.initListener()
        setContentView(mBind?.root)
        imdbId = intent.getStringExtra(Constants.IMDB_ID)
        getMovieDetailObserver()
        detailViewModel.getMovieDetailTask(imdbId!!)
        appStatusObserver()
    }

    private fun setupView(movieDetail: MovieDetailsResponse) {
        Glide.with(this).load(movieDetail.Poster).into(mBind?.detailsImg!!)
        mBind?.detailsTxtTitle?.text = movieDetail.Title
        mBind?.detailsTxtYear?.text = movieDetail.Year
        mBind?.detailsTxtReleased?.text = movieDetail.Released
        mBind?.detailsTxtTime?.text = movieDetail.Runtime
        mBind?.detailsTxtDirector?.text = movieDetail.Director
        mBind?.detailsTxtWriter?.text = movieDetail.Writer
        mBind?.detailsTxtActors?.text = movieDetail.Actors
        mBind?.detailsTxtStory?.text = movieDetail.Plot
        mBind?.detailsTxtRate?.text = movieDetail.imdbRating

    }

    private fun getMovieDetailObserver() {
        detailViewModel.movieDetail.observe(this, Observer {
            when (it.status) {
                Status.LOADING -> {
                    //create loading progress needed
                }
                Status.SUCCESS -> {
//                    hideProgressDialog()
//                    setupMoviesRcvRcv(it.data?.Search!!)
                    setupView(it.data!!)
                }
                else -> {
                }
            }
        })
    }

    private fun appStatusObserver() {
        detailViewModel.appStatus.observe(this, Observer {
            when (it.status) {
                Status.SHOW -> {
                    if (customDialog == null || !customDialog!!.isShowing) {
                        customDialog =
                            MakeConnectionDialog().createDialog(
                                this,
                                it.dialogType,
                                it.message!!
                            )
                        customDialog?.show()
                    }
                }
                Status.DISMISS -> {
                    customDialog?.dismiss()
                    finish()
                }
                Status.RETRY -> {
                    customDialog?.dismiss()
                    if (imdbId != null)
                        detailViewModel.getMovieDetailTask(imdbId!!)
                }
                else -> {
                }
            }
        })
    }


}
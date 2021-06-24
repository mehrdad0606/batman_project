package com.iranian.cards.android.batmanproject.ui.main.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.iranian.cards.android.batmanproject.adapter.MoviesAdapter
import com.iranian.cards.android.batmanproject.data.api.MakeConnectionDialog
import com.iranian.cards.android.batmanproject.data.api.retrofit.model.Movies
import com.iranian.cards.android.batmanproject.databinding.ActivityMainBinding
import com.iranian.cards.android.batmanproject.ui.detail.view.DetailActivity
import com.iranian.cards.android.batmanproject.ui.main.viewmodel.MainViewModel
import com.iranian.cards.android.batmanproject.utils.Constants
import com.iranian.cards.android.batmanproject.utils.Status
import com.iranian.cards.android.batmanproject.widgets.CustomDialog
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity(), MoviesAdapter.OnItemClickListener {

    private var mBind: ActivityMainBinding? = null
    private var moviesAdapter: MoviesAdapter? = null
    private val mainViewModel: MainViewModel by viewModel()
    private var customDialog: CustomDialog? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBind = ActivityMainBinding.inflate(layoutInflater)
        mainViewModel.initListener()
        setContentView(mBind?.root)
        getMoviesObserver()
        mainViewModel.getMoviesTask()
        appStatusObserver()
    }


    private fun getMoviesObserver() {
        mainViewModel.movies.observe(this, Observer {
            when (it.status) {
                Status.LOADING -> {
                    //create loading progress if needed
                }
                Status.SUCCESS -> {
//                    hideProgressDialog()
                    setupMoviesRcv(it.data?.Search!!)
                }
                else -> {
                }
            }
        })
    }

    private fun setupMoviesRcv(movieList: ArrayList<Movies>) {
        moviesAdapter = MoviesAdapter(this)
        moviesAdapter?.activity = this
        moviesAdapter?.list = movieList
        moviesAdapter?.setOnItemClickListener(this)
        val gLManager = GridLayoutManager(this, 1)
        mBind?.mainRcvMovies?.apply {
            layoutManager = gLManager
            adapter = moviesAdapter
        }
    }

    override fun onAchievementSelected(movie: Movies) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra(Constants.IMDB_ID, movie.imdbID)
        startActivity(intent)
    }

    private fun appStatusObserver() {
        mainViewModel.appStatus.observe(this, Observer {
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
                    mainViewModel.getMoviesTask()
                }
                else -> {
                }
            }
        })
    }
}
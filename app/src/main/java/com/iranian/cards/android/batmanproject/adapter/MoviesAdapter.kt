package com.iranian.cards.android.batmanproject.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.iranian.cards.android.batmanproject.data.api.retrofit.model.Movies
import com.iranian.cards.android.batmanproject.databinding.ItemRcvMainMoviesBinding


class MoviesAdapter(context: Context) :
    RecyclerView.Adapter<MoviesAdapter.ViewHolder>() {

    private var onItemClickListener: OnItemClickListener? =
        null
    var list: ArrayList<Movies> = ArrayList()
    var activity: Activity? = null

    private val mContext: Context = context
    private val requestOptions: RequestOptions = RequestOptions()
        .override(300, 300)
        .dontAnimate()

    inner class ViewHolder(val binding: ItemRcvMainMoviesBinding) :
        RecyclerView.ViewHolder(binding.root), View.OnClickListener {
        override fun onClick(v: View?) {
            if (v == binding.root) {
                onItemClickListener!!.onAchievementSelected(
                    list[layoutPosition]!!
                )
            }
        }

        init {
            binding.itemMainMoviesClRoot.setOnClickListener(this)
        }
    }

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener?) {
        this.onItemClickListener = onItemClickListener
    }

    interface OnItemClickListener {
        fun onAchievementSelected(movie: Movies)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MoviesAdapter.ViewHolder {
        return ViewHolder(
            ItemRcvMainMoviesBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    @SuppressLint("Range", "SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movieItem = list[position]
        Glide.with(mContext).load(movieItem.Poster).apply(requestOptions)
            .into(holder.binding.itemMainMoviesImgMovie)
        holder.binding.itemMainMoviesTxtTitle.text = movieItem.Title
        holder.binding.itemMainMoviesTxtSubtitle.text = movieItem.Year

    }

    override fun getItemCount(): Int {
        return list.size
    }
}
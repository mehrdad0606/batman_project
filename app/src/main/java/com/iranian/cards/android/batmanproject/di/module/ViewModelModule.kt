package com.iranian.cards.android.batmanproject.di.module

import com.iranian.cards.android.batmanproject.ui.detail.viewmodel.DetailViewModel
import com.iranian.cards.android.batmanproject.ui.main.viewmodel.MainViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel {
        MainViewModel(get())
    }
    viewModel {
        DetailViewModel(get())
    }
}
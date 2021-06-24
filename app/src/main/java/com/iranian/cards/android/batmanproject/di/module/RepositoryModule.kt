package com.iranian.cards.android.batmanproject.di.module

import com.iranian.cards.android.batmanproject.data.repository.MainRepository
import org.koin.dsl.module

val repoModule = module {
    single {
        MainRepository(get())
    }
}
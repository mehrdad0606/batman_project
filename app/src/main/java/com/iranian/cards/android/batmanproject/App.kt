package com.iranian.cards.android.batmanproject

import android.app.Application
import android.content.Context
import android.content.res.Configuration
import android.util.DisplayMetrics
import android.view.WindowManager
import androidx.appcompat.app.AppCompatDelegate
import com.iranian.cards.android.batmanproject.di.module.appModule
import com.iranian.cards.android.batmanproject.di.module.repoModule
import com.iranian.cards.android.batmanproject.di.module.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin


class App : Application() {
    companion object {
        var instance: App? = null
            private set

        init {
            AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
        }
    }

    override fun onCreate() {
        super.onCreate()
        instance = this

        startKoin {
            androidContext(this@App)
            modules(listOf(appModule, repoModule, viewModelModule))
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        val wm = getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val configuration = resources.configuration
        configuration.fontScale = 1.toFloat() //0.85 small size, 1 normal size, 1,15 big etc
        val metrics = DisplayMetrics()
        wm.defaultDisplay.getMetrics(metrics)
        metrics.scaledDensity = configuration.fontScale * metrics.density
        baseContext.resources.updateConfiguration(configuration, metrics)
    }

}
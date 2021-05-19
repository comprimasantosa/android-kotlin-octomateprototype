package com.primasantosa.android.octomateprototype

import android.app.Application
import androidx.camera.camera2.Camera2Config
import androidx.camera.core.CameraXConfig
import com.primasantosa.android.octomateprototype.di.networkModule
import com.primasantosa.android.octomateprototype.di.repositoryModule
import com.primasantosa.android.octomateprototype.di.viewModelModule
import org.koin.core.context.startKoin
import timber.log.Timber

class OctoApp: Application(), CameraXConfig.Provider {
    override fun onCreate() {
        super.onCreate()
        initTimber()
        initKoin()
    }

    override fun getCameraXConfig(): CameraXConfig {
        return Camera2Config.defaultConfig()
    }

    private fun initTimber() {
        Timber.plant(Timber.DebugTree())
    }

    private fun initKoin() {
        startKoin {
            modules(
                listOf(
                    networkModule,
                    repositoryModule,
                    viewModelModule
                )
            )
        }
    }
}
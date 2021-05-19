package com.primasantosa.android.octomateprototype.di

import com.primasantosa.android.octomateprototype.view.login.LoginViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val networkModule = module {
    single { Providers.provideWeatherService() }
}

val repositoryModule = module {
    single { Providers.provideWeatherRepository(get()) }
}

val viewModelModule = module {
    viewModel { LoginViewModel(get()) }
}
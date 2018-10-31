package my.cybulski.nazar.architecture_experemetal.di

import my.cybulski.nazar.architecture_experemetal.fragments.FirstViewModel
import my.cybulski.nazar.architecture_experemetal.fragments.SecondViewModel
import my.cybulski.nazar.architecture_experemetal.model.Store
import my.cybulski.nazar.architecture_experemetal.usecase.ChangedTaskStatusUseCase
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.Router

val appModule = module {

    single<Cicerone<Router>> { Cicerone.create() }

    single{ ChangedTaskStatusUseCase()}

    viewModel { FirstViewModel(get(),get()) }

    viewModel { SecondViewModel(get(),get()) }

    single{ Store()}

}
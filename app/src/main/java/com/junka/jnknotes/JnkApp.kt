package com.junka.jnknotes

import android.app.Application
import androidx.room.Room
import com.junka.jnknotes.data.NotesDataBase
import com.junka.jnknotes.data.dao.NoteDao
import com.junka.jnknotes.domain.Repository
import com.junka.jnknotes.domain.RepositoryImpl
import com.junka.jnknotes.presenter.ui.create.CreateNoteFragment
import com.junka.jnknotes.presenter.ui.create.CreateNoteViewModel
import com.junka.jnknotes.presenter.ui.dashboard.DashboardFragment
import com.junka.jnknotes.presenter.ui.dashboard.DashboardViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.core.qualifier.named
import org.koin.dsl.module

class JnkApp : Application() {

    val appModule = module {

        single(named("ioDispatcher")) { Dispatchers.IO }

    }

    val dbModule = module {
        single { NotesDataBase.buildDatabase(get()) }
        single { get<NotesDataBase>().noteDao }

        single<Repository> {  RepositoryImpl(get()) }
    }


    val scopeModule = module {
        scope<DashboardFragment> {
            viewModel { DashboardViewModel(get(), get(named("ioDispatcher"))) }
        }
        scope<CreateNoteFragment> {
            viewModel { CreateNoteViewModel(get(), get(named("ioDispatcher"))) }
        }

    }


    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@JnkApp)
            modules( appModule ,scopeModule,dbModule)
        }
    }
}
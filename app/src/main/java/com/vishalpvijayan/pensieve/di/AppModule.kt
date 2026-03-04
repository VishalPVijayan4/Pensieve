package com.vishalpvijayan.pensieve.di

import android.app.AlarmManager
import android.content.Context
import androidx.work.WorkManager
import com.vishalpvijayan.pensieve.domain.AppPreferences
import com.vishalpvijayan.pensieve.domain.InMemoryAppPreferences
import com.vishalpvijayan.pensieve.reminder.ReminderScheduler
import com.vishalpvijayan.pensieve.ui.PensieveViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single<AppPreferences> { InMemoryAppPreferences() }
    single { WorkManager.getInstance(get()) }
    single {
        get<Context>().getSystemService(Context.ALARM_SERVICE) as AlarmManager
    }
    single { ReminderScheduler(get(), get(), get()) }
    viewModel { PensieveViewModel(get(), get()) }
}

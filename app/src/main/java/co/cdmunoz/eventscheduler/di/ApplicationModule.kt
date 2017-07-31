package co.cdmunoz.eventscheduler.di

import android.app.Application
import android.content.Context
import co.cdmunoz.eventscheduler.SchedulerApplication
import dagger.Module
import dagger.Provides

@Module
class ApplicationModule(private val countdownApplication: SchedulerApplication) {

  @Provides
  fun applicationContext(): Context = countdownApplication

  @Provides
  fun application(): Application = countdownApplication
}
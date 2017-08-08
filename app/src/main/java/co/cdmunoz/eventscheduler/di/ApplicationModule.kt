package co.cdmunoz.eventscheduler.di

import android.app.Application
import android.content.Context
import co.cdmunoz.eventscheduler.SchedulerApplication
import dagger.Module
import dagger.Provides

@Module
class ApplicationModule(private val schedulerApplication: SchedulerApplication) {

  @Provides
  fun applicationContext(): Context = schedulerApplication

  @Provides
  fun application(): Application = schedulerApplication
}
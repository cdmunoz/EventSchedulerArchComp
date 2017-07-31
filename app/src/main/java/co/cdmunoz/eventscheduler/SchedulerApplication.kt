package co.cdmunoz.eventscheduler

import android.app.Application
import co.cdmunoz.eventscheduler.di.ApplicationModule
import co.cdmunoz.eventscheduler.di.DaggerSchedulerComponent
import co.cdmunoz.eventscheduler.di.SchedulerComponent
import co.cdmunoz.eventscheduler.di.SchedulerModule
import com.jakewharton.threetenabp.AndroidThreeTen
import timber.log.Timber

open class SchedulerApplication : Application() {

  open val countDownComponent: SchedulerComponent = DaggerSchedulerComponent.builder()
      .applicationModule(ApplicationModule(this))
      .schedulerModule(SchedulerModule())
      .build()

  override fun onCreate() {
    super.onCreate()
    AndroidThreeTen.init(this)
    if (BuildConfig.DEBUG) {
      Timber.plant(Timber.DebugTree())
    }
  }

}

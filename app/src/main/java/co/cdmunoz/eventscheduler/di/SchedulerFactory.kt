package co.cdmunoz.eventscheduler.di

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import co.cdmunoz.eventscheduler.SchedulerApplication

class SchedulerFactory(
    private val application: SchedulerApplication) : ViewModelProvider.NewInstanceFactory() {

  override fun <T : ViewModel> create(modelClass: Class<T>): T {
    val t = super.create(modelClass)
    if (t is SchedulerComponent.Injectable) {
      t.inject(application.countDownComponent)
    }
    return t
  }
}

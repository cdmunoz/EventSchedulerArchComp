package co.cdmunoz.eventscheduler.di

import dagger.Component
import co.cdmunoz.eventscheduler.ui.event.add.AddEventViewModel
import co.cdmunoz.eventscheduler.ui.event.list.EventListViewModel
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(SchedulerModule::class, ApplicationModule::class))
interface SchedulerComponent {

  fun inject(eventListViewModel: EventListViewModel)

  fun inject(addEventViewModel: AddEventViewModel)

  interface Injectable {
    fun inject(schedulerComponent: SchedulerComponent)
  }
}

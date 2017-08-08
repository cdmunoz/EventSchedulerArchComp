package co.cdmunoz.eventscheduler

import co.cdmunoz.eventscheduler.di.SchedulerComponent
import co.cdmunoz.eventscheduler.di.MockSchedulerModule

class MockSchedulerApplication : SchedulerApplication() {

  /*override val schedulerComponent: SchedulerComponent = DaggerEventDaoTest_MockSchedulerComponent.builder()
      .mockSchedulerModule(MockSchedulerModule(this))
      .build()*/
}

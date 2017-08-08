package co.cdmunoz.eventscheduler.dao

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Observer
import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import co.cdmunoz.eventscheduler.db.EventDatabase
import co.cdmunoz.eventscheduler.di.SchedulerComponent
import co.cdmunoz.eventscheduler.entity.Event
import dagger.Component
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.threeten.bp.LocalDateTime
import co.cdmunoz.eventscheduler.MockSchedulerApplication
import co.cdmunoz.eventscheduler.di.MockSchedulerModule
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton


@RunWith(AndroidJUnit4::class)
class EventDaoTest {

  lateinit var eventDao: EventDao

  @Inject
  lateinit var eventDatabase: EventDatabase

  @Singleton
  @Component(modules = arrayOf(MockSchedulerModule::class))
  interface MockSchedulerComponent : SchedulerComponent {
    fun inject(eventDaoTest: EventDaoTest)
  }

  @Before
  fun setup() {
    val instrumentation = InstrumentationRegistry.getInstrumentation()
    val app = instrumentation.targetContext.applicationContext as MockSchedulerApplication
    val component = app.schedulerComponent as MockSchedulerComponent
    component.inject(this)

    eventDao = eventDatabase.eventDao()
  }


  @Test
  @Throws(InterruptedException::class)
  fun addEvent_SuccessfullyAddsEvent() {
    val event = generateEventTestData(0, "Wedding")
    eventDao.addEvent(event)

    val eventRetrieved = getValue(eventDao.getEvents(LocalDateTime.now()))

    assertEquals(event.name, eventRetrieved[0].name)
    eventDao.deleteEvent(eventRetrieved[0])
  }

  @Test
  @Throws(InterruptedException::class)
  fun deleteEvent_SuccessfullyDeletesEvent() {
    val event = generateEventTestData(0, "Vacation")
    eventDao.addEvent(event)

    val eventRetrieved = getValue(eventDao.getEvents(LocalDateTime.now()))
    assertEquals(event.name, eventRetrieved[0].name)

    eventDao.deleteEvent(eventRetrieved[0])
    val eventRetrievedAfterUpdate = getValue(eventDao.getEvents(LocalDateTime.now()))

    assertEquals(0, eventRetrievedAfterUpdate.size)
  }


  @Test
  @Throws(InterruptedException::class)
  fun updateEvent_SuccessfullyUpdatesEvent() {
    val event = generateEventTestData(0, "First Name")
    eventDao.addEvent(event)

    val eventsRetrieved = getValue(eventDao.getEvents(LocalDateTime.now()))
    val (id, name) = eventsRetrieved[0]
    assertEquals(event.name, name)
    val newName = "New Name"
    val newEventUpdated = generateEventTestData(id, newName)

    eventDao.updateEvent(newEventUpdated)
    val eventRetrievedAfterUpdate = getValue(eventDao.getEvents(LocalDateTime.now()))

    assertEquals(newName, eventRetrievedAfterUpdate[0].name)
    eventDao.deleteEvent(eventRetrievedAfterUpdate[0])
  }


  internal fun generateEventTestData(id: Int, name: String): Event {
    return Event(id, name, "Test Description", LocalDateTime.now().plusDays(7))
  }

  companion object {

    /**
     * This is used to make sure the method waits till data is available from the observer.
     */
    @Throws(InterruptedException::class)
    fun <T> getValue(liveData: LiveData<T>): T {
      val data = arrayOfNulls<Any>(1)
      val latch = CountDownLatch(1)
      val observer = object : Observer<T> {
        override fun onChanged(o: T?) {
          data[0] = o
          latch.countDown()
          liveData.removeObserver(this)
        }
      }
      liveData.observeForever(observer)
      latch.await(2, TimeUnit.SECONDS)

      return data[0] as T
    }
  }
}

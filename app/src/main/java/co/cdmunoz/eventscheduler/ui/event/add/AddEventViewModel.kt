package co.cdmunoz.eventscheduler.ui.event.add

import android.arch.lifecycle.ViewModel
import co.cdmunoz.eventscheduler.di.SchedulerComponent
import co.cdmunoz.eventscheduler.entity.Event
import co.cdmunoz.eventscheduler.repository.EventRepository
import co.cdmunoz.eventscheduler.utils.MessageHelper
import co.cdmunoz.eventscheduler.utils.async
import io.reactivex.CompletableObserver
import io.reactivex.disposables.Disposable
import org.threeten.bp.LocalDateTime
import timber.log.Timber
import javax.inject.Inject


class AddEventViewModel : ViewModel(), SchedulerComponent.Injectable {

  @Inject
  lateinit var eventRepository: EventRepository
  lateinit var messageHelper: MessageHelper

  var eventName = String()
  var eventDescription = String()

  var eventDateTime = LocalDateTime.now()


  fun addEvent() {
    val event = Event(0, eventName, eventDescription, eventDateTime)
    eventRepository.addEvent(event).async()
        .subscribe(object : CompletableObserver {
          override fun onSubscribe(d: Disposable) {

          }

          override fun onComplete() {
            messageHelper.show("A new event was created -> " + event.name)
            Timber.d("onComplete - successfully added event -> " + event.name)
          }

          override fun onError(e: Throwable) {
            messageHelper.show("Error while creating a new event -> " + event.name)
            Timber.d("onError - error trying to add event: " + event.name, e)
          }
        })
  }

  override fun inject(schedulerComponent: SchedulerComponent) {
    schedulerComponent.inject(this)
  }
}

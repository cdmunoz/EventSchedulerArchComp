package co.cdmunoz.eventscheduler.ui.event.list


import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import co.cdmunoz.eventscheduler.di.SchedulerComponent
import co.cdmunoz.eventscheduler.entity.Event
import co.cdmunoz.eventscheduler.repository.EventRepository
import co.cdmunoz.eventscheduler.utils.async
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableCompletableObserver
import timber.log.Timber
import javax.inject.Inject

class EventListViewModel : ViewModel(), SchedulerComponent.Injectable {

  @Inject
  lateinit var eventRepository: EventRepository
  val disposables: CompositeDisposable = CompositeDisposable()

  var events: LiveData<List<Event>> = MutableLiveData()
    private set

  override fun inject(schedulerComponent: SchedulerComponent) {
    schedulerComponent.inject(this)
    events = eventRepository.events
  }

  fun deleteEvent(event: Event) {
    eventRepository.deleteEvent(event).async()
        .subscribe(object : DisposableCompletableObserver() {

          override fun onComplete() {
            Timber.d("onComplete - deleted event -> " + event.name)
          }

          override fun onError(e: Throwable) {
            Timber.e("OnError - Error trying to delete event: " + event.name, e)
          }
        })
  }

  fun destroy() {
    disposables.dispose()
  }

}

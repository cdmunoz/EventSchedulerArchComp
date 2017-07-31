package co.cdmunoz.eventscheduler.ui.event.list


import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import co.cdmunoz.eventscheduler.di.SchedulerComponent
import co.cdmunoz.eventscheduler.entity.Event
import co.cdmunoz.eventscheduler.repository.EventRepository
import io.reactivex.CompletableObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

class EventListViewModel : ViewModel(), SchedulerComponent.Injectable {

  @Inject
  lateinit var eventRepository: EventRepository

  var events: LiveData<List<Event>> = MutableLiveData()
    private set

  override fun inject(schedulerComponent: SchedulerComponent) {
    schedulerComponent.inject(this)
    events = eventRepository.events
  }

  fun deleteEvent(event: Event) {
    eventRepository.deleteEvent(event)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(object : CompletableObserver {
          override fun onSubscribe(d: Disposable) {

          }

          override fun onComplete() {
            Timber.d("onComplete - deleted event")
          }

          override fun onError(e: Throwable) {
            Timber.e("OnError - deleted event: ", e)
          }
        })
  }

}

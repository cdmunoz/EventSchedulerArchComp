package co.cdmunoz.eventscheduler.repository

import android.arch.lifecycle.LiveData
import co.cdmunoz.eventscheduler.db.EventDatabase
import co.cdmunoz.eventscheduler.entity.Event
import io.reactivex.Completable
import org.threeten.bp.LocalDateTime
import javax.inject.Inject

class EventRepositoryImpl @Inject constructor(var eventDatabase: EventDatabase) : EventRepository {

  override fun addEvent(event: Event): Completable {
    return Completable.fromAction { eventDatabase.eventDao().addEvent(event) }
  }

  override val events: LiveData<List<Event>>
    get() = eventDatabase.eventDao().getEvents(LocalDateTime.now())

  override fun deleteEvent(event: Event): Completable {
    return Completable.fromAction { eventDatabase.eventDao().deleteEvent(event) }
  }


}

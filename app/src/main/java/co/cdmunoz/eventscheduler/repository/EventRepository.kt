package co.cdmunoz.eventscheduler.repository


import android.arch.lifecycle.LiveData
import co.cdmunoz.eventscheduler.entity.Event
import io.reactivex.Completable

interface EventRepository {

    fun addEvent(event: Event): Completable
    val events: LiveData<List<Event>>
    fun deleteEvent(event: Event): Completable

}

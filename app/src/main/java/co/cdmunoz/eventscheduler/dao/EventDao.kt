package co.cdmunoz.eventscheduler.dao


import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import org.threeten.bp.LocalDateTime
import co.cdmunoz.eventscheduler.entity.Event

@Dao
interface EventDao {

    @Query("SELECT * FROM events WHERE date > :p0")
    fun getEvents(minDate: LocalDateTime): LiveData<List<Event>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addEvent(event: Event)

    @Delete
    fun deleteEvent(event: Event)

    @Update(onConflict = REPLACE)
    fun updateEvent(event: Event)

}

package co.cdmunoz.eventscheduler.entity

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import org.threeten.bp.LocalDateTime
import org.threeten.bp.temporal.ChronoUnit

@Entity(tableName = "events")
data class Event(
        @PrimaryKey(autoGenerate = true) val id: Int,
        val name: String = "",
        val description: String = "",
        val date: LocalDateTime = LocalDateTime.now()

) {
    fun getDaysBetween() = ChronoUnit.DAYS.between(LocalDateTime.now(), date)
}
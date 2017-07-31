package co.cdmunoz.eventscheduler.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters

import co.cdmunoz.eventscheduler.dao.EventDao
import co.cdmunoz.eventscheduler.entity.Event

@Database(entities = arrayOf(Event::class), version = 2)
@TypeConverters(DateTypeConverter::class)
abstract class EventDatabase : RoomDatabase() {

  abstract fun eventDao(): EventDao

}

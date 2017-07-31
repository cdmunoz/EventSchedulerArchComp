package co.cdmunoz.eventscheduler.di

import android.arch.persistence.room.Room
import android.content.Context
import co.cdmunoz.eventscheduler.db.EventDatabase
import dagger.Module
import dagger.Provides
import co.cdmunoz.eventscheduler.repository.EventRepository
import co.cdmunoz.eventscheduler.repository.EventRepositoryImpl
import javax.inject.Singleton

@Module
class SchedulerModule {

  @Provides
  @Singleton
  fun providesEventRepository(eventDatabase: EventDatabase): EventRepository = EventRepositoryImpl(
      eventDatabase)

  @Provides
  @Singleton
  fun providesEventDatabase(context: Context): EventDatabase = Room.databaseBuilder(
      context.applicationContext, EventDatabase::class.java, "event_db").build()

}

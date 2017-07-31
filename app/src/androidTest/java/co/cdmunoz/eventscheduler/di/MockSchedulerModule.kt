package co.cdmunoz.eventscheduler.di

import android.arch.persistence.room.Room
import android.content.Context
import co.cdmunoz.eventscheduler.SchedulerApplication
import co.cdmunoz.eventscheduler.db.EventDatabase
import co.cdmunoz.eventscheduler.repository.EventRepository
import co.cdmunoz.eventscheduler.repository.EventRepositoryImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class MockSchedulerModule(val application: SchedulerApplication) {

  val applicationContext: Context
    @Provides
    get() = application

  @Provides
  @Singleton
  fun provideEventDatabase(context: Context): EventDatabase {
    return Room.inMemoryDatabaseBuilder(context.applicationContext,
        EventDatabase::class.java).build()
  }

  @Provides
  @Singleton
  fun providesEventRepository(eventDatabase: EventDatabase): EventRepository {
    return EventRepositoryImpl(eventDatabase)
  }

}

package co.cdmunoz.eventscheduler.ui.event.add

import android.arch.core.executor.testing.InstantTaskExecutorRule
import co.cdmunoz.eventscheduler.repository.EventRepository
import io.reactivex.Completable
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.AfterClass
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

class AddEventViewModelTest {

  lateinit var addEventViewModel: AddEventViewModel

  @Mock
  lateinit var eventRepository: EventRepository

  @Rule @JvmField
  val instantExecutorRule = InstantTaskExecutorRule()

  @Before
  fun setup() {
    MockitoAnnotations.initMocks(this)
    addEventViewModel = AddEventViewModel()
    addEventViewModel.eventRepository = eventRepository
  }

  @Test
  fun addEvent() {
    `when`(eventRepository.addEvent(ArgumentMatchers.any())).thenReturn(Completable.complete())

    addEventViewModel.addEvent()

    verify<EventRepository>(eventRepository).addEvent(ArgumentMatchers.any())
  }

  companion object {
    @BeforeClass
    @JvmStatic
    fun setUpClass() {
      RxAndroidPlugins.setInitMainThreadSchedulerHandler { _ -> Schedulers.trampoline() }
    }

    @AfterClass
    @JvmStatic
    fun tearDownClass() {
      RxAndroidPlugins.reset()
    }
  }
}
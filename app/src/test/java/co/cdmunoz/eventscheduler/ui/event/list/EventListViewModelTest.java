package co.cdmunoz.eventscheduler.ui.event.list;

import android.arch.core.executor.testing.InstantTaskExecutorRule;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import co.cdmunoz.eventscheduler.di.SchedulerComponent;
import co.cdmunoz.eventscheduler.entity.Event;
import co.cdmunoz.eventscheduler.repository.EventRepository;
import co.cdmunoz.eventscheduler.ui.event.add.AddEventViewModel;
import io.reactivex.Completable;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.schedulers.Schedulers;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.threeten.bp.LocalDateTime;

import static junit.framework.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class EventListViewModelTest {

  EventListViewModel eventListViewModel;

  @Mock EventRepository eventRepository;

  @Rule public InstantTaskExecutorRule instantExecutorRule = new InstantTaskExecutorRule();

  @BeforeClass public static void setUpClass() {
    RxAndroidPlugins.setInitMainThreadSchedulerHandler(__ -> Schedulers.trampoline());
  }

  @Before public void setup() {
    MockitoAnnotations.initMocks(this);
    eventListViewModel = new EventListViewModel();
    eventListViewModel.setEventRepository(eventRepository);
  }

  @AfterClass public static void tearDownClass() {
    RxAndroidPlugins.reset();
  }

  @Test public void getEvents() throws InterruptedException {
    MutableLiveData<List<Event>> fakeEvents = getEventListMutableData();
    when(eventRepository.getEvents()).thenReturn(fakeEvents);

    eventListViewModel.inject(new SchedulerComponent() {
      @Override public void inject(EventListViewModel eventListViewModel) {
        eventListViewModel.setEventRepository(eventRepository);
      }

      @Override public void inject(AddEventViewModel addEventViewModel) {

      }
    });
    List<Event> eventsReturned = getValue(eventListViewModel.getEvents());

    verify(eventRepository).getEvents();
    assertEquals(1, eventsReturned.size());
    assertEquals("Name", eventsReturned.get(0).getName());
  }

  @NonNull private MutableLiveData<List<Event>> getEventListMutableData() {
    List<Event> events = new ArrayList<>();
    Event event = new Event(1, "Name", "Desc", LocalDateTime.now());
    events.add(event);
    MutableLiveData<List<Event>> fakeEvents = new MutableLiveData<>();
    fakeEvents.setValue(events);
    return fakeEvents;
  }

  @Test public void deleteEvent() {
    when(eventRepository.deleteEvent(any())).thenReturn(Completable.complete());

    eventListViewModel.deleteEvent(new Event(1, "Name", "Description", LocalDateTime.now()));

    verify(eventRepository).deleteEvent(any());
  }

  public static <T> T getValue(LiveData<T> liveData) throws InterruptedException {
    final Object[] data = new Object[1];
    CountDownLatch latch = new CountDownLatch(1);
    Observer<T> observer = new Observer<T>() {
      @Override public void onChanged(@Nullable T o) {
        data[0] = o;
        latch.countDown();
        liveData.removeObserver(this);
      }
    };
    liveData.observeForever(observer);
    latch.await(2, TimeUnit.SECONDS);
    //noinspection unchecked
    return (T) data[0];
  }
}

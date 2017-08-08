package co.cdmunoz.eventscheduler.ui.event.list

import android.arch.lifecycle.LifecycleActivity
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.Toast
import co.cdmunoz.eventscheduler.R
import co.cdmunoz.eventscheduler.SchedulerApplication
import co.cdmunoz.eventscheduler.di.SchedulerFactory
import co.cdmunoz.eventscheduler.entity.Event
import co.cdmunoz.eventscheduler.ui.event.add.AddEventActivity
import co.cdmunoz.eventscheduler.utils.toast
import kotlinx.android.synthetic.main.activity_list_event.fab_add
import kotlinx.android.synthetic.main.activity_list_event.recycler_view_list_events
import java.util.ArrayList

class EventListActivity : LifecycleActivity() {

  private lateinit var adapter: EventAdapter
  private lateinit var eventListViewModel: EventListViewModel

  private val deleteClickListener: View.OnClickListener = View.OnClickListener { v ->
    eventListViewModel.deleteEvent(v.tag as Event)
  }

  private val itemClickListener = View.OnClickListener { v ->
    val (_, name) = v.tag as Event
    toast("Clicked:" + name, Toast.LENGTH_LONG)
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_list_event)

    val recyclerView = recycler_view_list_events
    val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    recyclerView.layoutManager = layoutManager

    adapter = EventAdapter(ArrayList<Event>(), itemClickListener, deleteClickListener)
    recyclerView.adapter = adapter
    val dividerItemDecoration = DividerItemDecoration(recyclerView.context,
        layoutManager.orientation)
    recyclerView.addItemDecoration(dividerItemDecoration)

    val floatingActionButton = fab_add
    floatingActionButton.setOnClickListener { _ ->
      startActivity(Intent(applicationContext, AddEventActivity::class.java))
    }

    val application = application as SchedulerApplication
    eventListViewModel = ViewModelProviders.of(this, SchedulerFactory(application)).get(
        EventListViewModel::class.java)
    eventListViewModel.events.observe(this, Observer { events: List<Event>? ->
      adapter.items = events!!
    })

  }

  override fun onDestroy() {
    super.onDestroy()
    eventListViewModel.destroy()
  }
}

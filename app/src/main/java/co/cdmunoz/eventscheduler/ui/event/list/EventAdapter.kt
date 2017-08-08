package co.cdmunoz.eventscheduler.ui.event.list

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import co.cdmunoz.eventscheduler.R
import co.cdmunoz.eventscheduler.entity.Event
import co.cdmunoz.eventscheduler.ui.event.list.EventAdapter.EventViewHolder
import co.cdmunoz.eventscheduler.utils.inflate
import kotlinx.android.synthetic.main.list_item_event.view.button_delete
import kotlinx.android.synthetic.main.list_item_event.view.text_view_countdown
import kotlinx.android.synthetic.main.list_item_event.view.text_view_event_description
import kotlinx.android.synthetic.main.list_item_event.view.text_view_event_name

class EventAdapter internal constructor(items: List<Event>,
    private val viewClickListener: View.OnClickListener,
    private val deleteClickListener: View.OnClickListener) : RecyclerView.Adapter<EventViewHolder>() {

  var items: List<Event> = items
    set(newItems) {
      field = newItems
      notifyDataSetChanged()
    }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
    return EventViewHolder(parent.inflate(R.layout.list_item_event))
  }

  override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
    val item = items[position]
    with(holder.itemView) {
      text_view_event_name.text = item.name
      text_view_event_description.text = item.description
      text_view_countdown.text = context.getString(R.string.days_until, item.getDaysBetween())
      tag = item
      button_delete.tag = item
      button_delete.setOnClickListener(deleteClickListener)
      setOnClickListener(viewClickListener)
    }
  }

  override fun getItemCount() = items.size

  class EventViewHolder(v: View) : RecyclerView.ViewHolder(v)
}
package co.cdmunoz.eventscheduler.ui.event.add

import android.app.DatePickerDialog
import android.arch.lifecycle.LifecycleActivity
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.widget.DatePicker
import android.widget.Toast
import co.cdmunoz.eventscheduler.R
import co.cdmunoz.eventscheduler.SchedulerApplication
import co.cdmunoz.eventscheduler.di.SchedulerFactory
import co.cdmunoz.eventscheduler.utils.MessageHelper
import co.cdmunoz.eventscheduler.utils.toast
import com.jakewharton.rxbinding2.widget.RxTextView
import kotlinx.android.synthetic.main.activity_add_event.button_add
import kotlinx.android.synthetic.main.activity_add_event.button_set_date
import kotlinx.android.synthetic.main.activity_add_event.edit_text_description
import kotlinx.android.synthetic.main.activity_add_event.edit_text_title
import kotlinx.android.synthetic.main.activity_add_event.text_view_date_set
import org.threeten.bp.LocalDateTime

class AddEventActivity : LifecycleActivity(), DatePickerDialog.OnDateSetListener, MessageHelper {

  override fun show(message: String) {
    toast(message, Toast.LENGTH_SHORT)
  }

  private lateinit var addEventViewModel: AddEventViewModel

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_add_event)

    setupClickListeners()
    setupViewModelAndBindings()
  }

  private fun setupViewModelAndBindings() {
    val schedulerApplication = application as SchedulerApplication
    addEventViewModel = ViewModelProviders.of(this, SchedulerFactory(schedulerApplication))
        .get(AddEventViewModel::class.java)
    edit_text_title.setText(addEventViewModel.eventName)
    edit_text_description.setText(addEventViewModel.eventDescription)
    text_view_date_set.text = addEventViewModel.eventDateTime.toString()

    RxTextView.afterTextChangeEvents(
        edit_text_title).subscribe { s -> addEventViewModel.eventName = s.editable().toString() }

    RxTextView.afterTextChangeEvents(
        edit_text_description).subscribe { s -> addEventViewModel.eventDescription = s.editable().toString() }
  }

  private fun setupClickListeners() {
    button_add.setOnClickListener { _ ->
      addEventViewModel.messageHelper = this
      addEventViewModel.addEvent()
      finish()
    }

    button_set_date.setOnClickListener { _ ->
      val localDateTime = addEventViewModel.eventDateTime
      val datePickerDialog = DatePickerDialog(
          this, this, localDateTime!!.year, localDateTime.monthValue - 1, localDateTime.dayOfMonth)
      datePickerDialog.show()
    }
  }

  override fun onDateSet(view: DatePicker, year: Int, month: Int, dayOfMonth: Int) {
    addEventViewModel.eventDateTime = LocalDateTime.of(year, month + 1, dayOfMonth, 0, 0)
    text_view_date_set.text = addEventViewModel.eventDateTime.toString()
  }

  fun getMessageHelper(): MessageHelper {
    return object : MessageHelper {
      override fun show(message: String) {
        toast(message, Toast.LENGTH_SHORT)
      }
    }
  }
}

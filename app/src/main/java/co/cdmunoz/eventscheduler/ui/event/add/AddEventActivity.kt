package co.cdmunoz.eventscheduler.ui.add

import android.app.DatePickerDialog
import android.arch.lifecycle.LifecycleActivity
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.DatePicker
import co.cdmunoz.eventscheduler.R
import co.cdmunoz.eventscheduler.SchedulerApplication
import co.cdmunoz.eventscheduler.di.SchedulerFactory
import co.cdmunoz.eventscheduler.ui.event.add.AddEventViewModel
import kotlinx.android.synthetic.main.activity_add_event.button_add
import kotlinx.android.synthetic.main.activity_add_event.button_set_date
import kotlinx.android.synthetic.main.activity_add_event.edit_text_description
import kotlinx.android.synthetic.main.activity_add_event.edit_text_title
import kotlinx.android.synthetic.main.activity_add_event.text_view_date_set
import org.threeten.bp.LocalDateTime

class AddEventActivity : LifecycleActivity(), DatePickerDialog.OnDateSetListener {

  private lateinit var addEventViewModel: AddEventViewModel

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_add_event)

    setupClickListeners()
    setupViewModel()
  }

  private fun setupViewModel() {
    val countdownApplication = application as SchedulerApplication
    addEventViewModel = ViewModelProviders.of(this, SchedulerFactory(countdownApplication))
        .get(AddEventViewModel::class.java)
    edit_text_title.setText(addEventViewModel.eventName)
    edit_text_description.setText(addEventViewModel.eventDescription)
    text_view_date_set.text = addEventViewModel.eventDateTime.toString()
  }

  private fun setupClickListeners() {
    edit_text_title.addTextChangedListener(object : TextWatcher {
      override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

      }

      override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

      }

      override fun afterTextChanged(s: Editable) {
        addEventViewModel.eventName = s.toString()
      }
    })
    edit_text_description.addTextChangedListener(object : TextWatcher {
      override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

      }

      override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

      }

      override fun afterTextChanged(s: Editable) {
        addEventViewModel.eventDescription = s.toString()
      }
    })
    button_add.setOnClickListener { _ ->
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
}

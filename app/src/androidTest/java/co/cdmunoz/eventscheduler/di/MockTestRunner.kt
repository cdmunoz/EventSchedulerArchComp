package co.cdmunoz.eventscheduler.di

import android.app.Application
import android.content.Context
import android.support.test.runner.AndroidJUnitRunner

import co.cdmunoz.eventscheduler.MockSchedulerApplication

class MockTestRunner : AndroidJUnitRunner() {
  @Throws(InstantiationException::class, IllegalAccessException::class,
      ClassNotFoundException::class)
  override fun newApplication(cl: ClassLoader, className: String, context: Context): Application {
    return super.newApplication(cl, MockSchedulerApplication::class.java.name, context)
  }
}

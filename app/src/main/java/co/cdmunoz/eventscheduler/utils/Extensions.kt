package co.cdmunoz.eventscheduler.utils

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

fun Context.toast(text: CharSequence, length: Int = Toast.LENGTH_SHORT) {
  Toast.makeText(this, text, length).show()
}

fun ViewGroup.inflate(layoutRes: Int): View {
  return LayoutInflater.from(context).inflate(layoutRes, this, false)
}

fun Completable.async(): Completable = this.compose {
  this.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
}
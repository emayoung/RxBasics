package com.entraid.rxbasics

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class MainActivity : AppCompatActivity() {

    private val compostiteDisposable = CompositeDisposable()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        createObservable()
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(getObservable())

    }


    private fun createObservable():Observable<String> {
        //create a quick observable
        val observable  = Observable.just("Nsikak", "Emem", "Utibe", "Ekene", "Oscar",
            "Utibe 2", "Brownson", "Arab Money")
        return observable
    }

    private fun getObservable():Observer<String> {
        val observer  = object : Observer<String> {
            override fun onComplete() {
                Toast.makeText(this@MainActivity, "Emission has finished", Toast.LENGTH_LONG).show()
            }

            override fun onSubscribe(d: Disposable) {
                compostiteDisposable.add(d)

                Toast.makeText(this@MainActivity, "We have subscribed successfully",
                    Toast.LENGTH_LONG).show()
                Log.e("TAG", "Ive subscribed to the observable")
            }

            override fun onNext(data: String) {
                Toast.makeText(this@MainActivity, "Name $data", Toast.LENGTH_LONG).show()
            }

            override fun onError(e: Throwable) {
                Log.e("TAG", "An error occured")
            }
        }
        return  observer
    }


    override fun onDestroy() {
        compostiteDisposable.dispose()
        super.onDestroy()
    }
}

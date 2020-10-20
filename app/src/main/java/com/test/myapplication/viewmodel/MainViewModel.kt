package com.test.myapplication.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.test.myapplication.datamodel.MovieDetailsResponse
import com.tvsmotor.fivestvsapp.network.ServiceBuilder
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MainViewModel : ViewModel() {

    var movieDetailsList: MutableLiveData<MovieDetailsResponse> = MutableLiveData()



    fun fetchDealerDetails(api_key: String?,language: String?,page: Int) {
        val compositeDisposable = CompositeDisposable()
        compositeDisposable.add(
            ServiceBuilder.buildService().getMovieDetails(api_key,language,page)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({ response ->
                    movieDetailsList.value = response

                }, {it->
                    Log.e("error",it.toString())
                })
        )
    }
}
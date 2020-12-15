package com.test.myapplication.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.test.myapplication.datamodel.CategoryDetailsResponse
import com.tvsmotor.fivestvsapp.network.ServiceBuilder
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class CategoryViewModel : ViewModel() {

    var categoryDetailsList: MutableLiveData<CategoryDetailsResponse> = MutableLiveData()



    fun fetchCategoryDetails() {
        val compositeDisposable = CompositeDisposable()
        compositeDisposable.add(
            ServiceBuilder.buildService().getCategoryDetails()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({ response ->
                    categoryDetailsList.value = response

                }, {it->
                    Log.e("error",it.toString())
                })
        )
    }
}
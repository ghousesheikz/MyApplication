package com.test.myapplication.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.test.myapplication.datamodel.CategoryDetailsResponse
import com.test.myapplication.datamodel.ProductDetailsResponse
import com.tvsmotor.fivestvsapp.network.ServiceBuilder
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class ProductViewModel :ViewModel() {

    var productDetailsList: MutableLiveData<ProductDetailsResponse> = MutableLiveData()



    fun fetchProductDetails() {
        val compositeDisposable = CompositeDisposable()
        compositeDisposable.add(
            ServiceBuilder.buildService().getProductDetails()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({ response ->
                    productDetailsList.value = response

                }, {it->
                    Log.e("error",it.toString())
                })
        )
    }
}
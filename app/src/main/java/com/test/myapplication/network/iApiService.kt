package com.test.myapplication.network



import com.test.myapplication.datamodel.CategoryDetailsResponse
import com.test.myapplication.datamodel.ProductDetailsResponse
import io.reactivex.Observable
import retrofit2.http.GET

interface iApiService {


    @GET("5e16d5263000002a00d5616c")
    fun getCategoryDetails(): Observable<CategoryDetailsResponse>



    @GET("5e16d5443000004e00d5616d")
    fun getProductDetails(): Observable<ProductDetailsResponse>


}
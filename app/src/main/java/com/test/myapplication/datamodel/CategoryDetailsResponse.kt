package com.test.myapplication.datamodel

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class CategoryDetailsResponse {

    @SerializedName("arrayOfProducts")
    @Expose
    private var arrayOfProducts: List<ArrayOfProduct?>? = null

    fun getArrayOfProducts(): List<ArrayOfProduct?>? {
        return arrayOfProducts
    }

    fun setArrayOfProducts(arrayOfProducts: List<ArrayOfProduct?>?) {
        this.arrayOfProducts = arrayOfProducts
    }

    class ArrayOfProduct {
        @SerializedName("imgUrl")
        @Expose
        var imgUrl: String? = null

        @SerializedName("name")
        @Expose
        var name: String? = null

        @SerializedName("id")
        @Expose
        var id: String? = null
    }
}
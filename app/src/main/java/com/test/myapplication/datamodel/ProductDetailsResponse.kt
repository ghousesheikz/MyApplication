package com.test.myapplication.datamodel

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class ProductDetailsResponse {

    @SerializedName("arrayOfProducts")
    @Expose
    private var arrayOfProducts: List<ArrayOfProduct?> = emptyList()

    fun getArrayOfProducts(): List<ArrayOfProduct?> {
        return arrayOfProducts
    }

    fun setArrayOfProducts(arrayOfProducts: List<ArrayOfProduct?>) {
        this.arrayOfProducts = arrayOfProducts
    }

    class ArrayOfProduct {
        @SerializedName("imgUrl")
        @Expose
        var imgUrl: String? = null

        @SerializedName("name")
        @Expose
        var name: String? = null

        @SerializedName("category")
        @Expose
        var category: String? = null

        @SerializedName("quantity")
        @Expose
        var quantity: Int? = null

        @SerializedName("description")
        @Expose
        var description: String? = null

        @SerializedName("price")
        @Expose
        var price: String? = null
    }
}
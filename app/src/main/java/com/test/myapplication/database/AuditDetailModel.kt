package com.test.myapplication.database

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class AuditDetailModel {

    var photoLocation: String? = null

    @SerializedName("HeaderRequestNo")
    @Expose
    var headerRequestNo: String? = null

    @SerializedName("FixedPointNo")
    @Expose
    var fixedPointNo: String? = null

    @SerializedName("KeyVal")
    @Expose
    var keyVal: String? = null

    @SerializedName("ZoneNo")
    @Expose
    var zoneNo: String? = null

    @SerializedName("WeekNo")
    @Expose
    var weekNo: String? = null

    @SerializedName("Rating")
    @Expose
    var rating: Int? = null

    @SerializedName("Feedback")
    @Expose
    var feedback: String? = null
    @SerializedName("DetailRequestNo")
    @Expose
    var detailRequestNo: String? = null
    @SerializedName("COMPANY_CODE")
    @Expose
    var cOMPANYCODE: String? = null
}
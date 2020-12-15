package com.test.myapplication.view.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.SeekBar
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import com.crystal.crystalrangeseekbar.interfaces.OnRangeSeekbarChangeListener
import com.crystal.crystalrangeseekbar.interfaces.OnRangeSeekbarFinalValueListener
import com.crystal.crystalrangeseekbar.widgets.CrystalRangeSeekbar
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.test.myapplication.R
import com.test.myapplication.view.interfaces.FilterValues
import com.tvsmotor.fivestvsapp.utils.TinyDB
import kotlinx.android.synthetic.main.bottomshet_view.*


class BottomSheetDialog(productListActivity: Context) : BottomSheetDialogFragment() {
    lateinit var seekBar: SeekBar
    lateinit var seekbar1: CrystalRangeSeekbar
    lateinit var txt_minprice: TextView
    lateinit var txt_maxprice: TextView
    lateinit var stockChk: CheckBox

    lateinit var btn_apply: AppCompatButton

    var checkVal: Boolean? = true

    private val mCtx = productListActivity
    lateinit var tinyDB: TinyDB
    var priceminValue: Long? = 0
    var pricemaxValue: Long? = 0
    lateinit var filtervalues: FilterValues


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        tinyDB = TinyDB(mCtx)
        pricemaxValue = tinyDB.getInt("maxprice").toLong()

        try {
            filtervalues = mCtx as FilterValues
        } catch (e: ClassCastException) {
            throw ClassCastException(activity.toString() + " must implement MyInterface ")
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v: View = inflater.inflate(
            R.layout.bottomshet_view,
            container, false
        )
        seekbar1 = v.findViewById(R.id.rangeSeekbar1)
        txt_minprice = v.findViewById(R.id.txt_minpriceval)
        txt_maxprice = v.findViewById(R.id.txt_maxpriceval)
        stockChk = v.findViewById(R.id.stock_chk)
        btn_apply = v.findViewById(R.id.btn_apply)

        stockChk.setOnClickListener {
            checkVal = stockChk.isChecked
        }


        seekbar1.setOnRangeSeekbarChangeListener(OnRangeSeekbarChangeListener { minValue, maxValue ->

            txt_minprice.text = "min : ₹ " + minValue.toString()
            txt_maxprice.text = "max : ₹ " + maxValue.toString()
        })


        seekbar1.setOnRangeSeekbarFinalValueListener(OnRangeSeekbarFinalValueListener { minValue, maxValue ->
            priceminValue = minValue as Long?
            pricemaxValue = maxValue as Long?

        })
        btn_apply.setOnClickListener {

            filtervalues.FilterData(priceminValue,pricemaxValue, checkVal)
            dismiss()
        }

        return v
    }


}


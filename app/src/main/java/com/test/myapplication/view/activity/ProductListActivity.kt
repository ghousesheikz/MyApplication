package com.test.myapplication.view.activity

import android.content.Context
import android.os.AsyncTask
import android.os.Bundle
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.test.myapplication.R
import com.test.myapplication.database.DatabaseClient
import com.test.myapplication.database.ModelSaveData
import com.test.myapplication.utils.MyScrollController
import com.test.myapplication.view.adapter.ProductListAdapter
import com.test.myapplication.view.fragment.BottomSheetDialog
import com.test.myapplication.view.interfaces.FilterValues
import com.tvsmotor.fivestvsapp.utils.TinyDB
import kotlinx.android.synthetic.main.activity_product_list.*

class ProductListActivity : AppCompatActivity(), FilterValues {
    val int: Int? = 0
    var categoryId: String = ""
    lateinit var productAdapter: ProductListAdapter
    lateinit var productList: List<ModelSaveData>

    companion object {
        var recyclerview: RecyclerView? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_list)
        productList = ArrayList()
        categoryId = intent.getStringExtra("categoryid")!!

        recyclerview = findViewById(R.id.productlist)
        recyclerview?.layoutManager = LinearLayoutManager(this)
        recyclerview?.isNestedScrollingEnabled = false
        productAdapter = ProductListAdapter(productList, this)
        recyclerview?.adapter = productAdapter

        recyclerview?.addOnScrollListener(object : MyScrollController() {
            override fun show() {
                filter_data.setVisibility(View.VISIBLE)
                filter_data.animate().translationY(0f).setStartDelay(200).setInterpolator(
                    DecelerateInterpolator(2F)
                ).start()
            }

            override fun hide() {
                filter_data.animate().translationY(filter_data.getHeight().toFloat())
                    .setInterpolator(AccelerateInterpolator(2F)).start()
                filter_data.setVisibility(View.GONE)
            }

        })

        filter_data.setOnClickListener {

            val bottomSheet = BottomSheetDialog(this)
            bottomSheet.show(
                supportFragmentManager,
                "ModalBottomSheet"
            )

        }

        GetDataFromDB(this, productAdapter).execute(categoryId)
    }


    @Suppress("DEPRECATION")
    internal class GetDataFromDB(context: Context, productAdapter: ProductListAdapter) :
        AsyncTask<String?, Void?, List<ModelSaveData>>() {


        private val mCtx = context
        lateinit var tinyDb: TinyDB
        var mAdapter = productAdapter

        override fun doInBackground(vararg strings: String?): List<ModelSaveData> {
            tinyDb = TinyDB(mCtx)
            tinyDb.putInt(
                "maxprice",
                DatabaseClient(mCtx).getAppDatabase()!!.dataDao().getmaxPrice()!!
            )

            return DatabaseClient(mCtx).getAppDatabase()!!.dataDao()
                .getDatabyCategoryid(strings[0]) as List<ModelSaveData>
        }

        override fun onPostExecute(result: List<ModelSaveData>?) {
            super.onPostExecute(result)
            if (result!!.size > 0) {
                mAdapter.updateAdapter(result)
                //  recyclerview?.adapter = mAdapter
            }
        }

    }


    @Suppress("DEPRECATION")
    internal class GetDataByFilter(
        context: Context,
        productAdapter: ProductListAdapter,
        instock: Boolean
    ) :
        AsyncTask<String?, Void?, List<ModelSaveData>>() {
        private var chkinstock = instock
        private val mCtx = context
        lateinit var tinyDb: TinyDB
        var mAdapter = productAdapter

        override fun doInBackground(vararg strings: String?): List<ModelSaveData> {
            if (chkinstock) {
                return DatabaseClient(mCtx).getAppDatabase()!!.dataDao()
                    .getDatabypriceandstock(
                        strings[0],
                        strings[1],
                        strings[2]
                    ) as List<ModelSaveData>
            } else {
                return DatabaseClient(mCtx).getAppDatabase()!!.dataDao()
                    .getDatabypriceandoutofstock(
                        strings[0],
                        strings[1],
                        strings[2]
                    ) as List<ModelSaveData>
            }
        }

        override fun onPostExecute(result: List<ModelSaveData>?) {
            super.onPostExecute(result)

            mAdapter.updateAdapter(result)

        }

    }

    @Suppress("DEPRECATION")
    internal class GetDataByOutOfStockFilter(context: Context, productAdapter: ProductListAdapter) :
        AsyncTask<String?, Void?, List<ModelSaveData>>() {
        private val mCtx = context
        lateinit var tinyDb: TinyDB
        var mAdapter = productAdapter

        override fun doInBackground(vararg strings: String?): List<ModelSaveData> {

            return DatabaseClient(mCtx).getAppDatabase()!!.dataDao()
                .getDatabypriceandoutofstock(
                    strings[0],
                    strings[1],
                    strings[2]
                ) as List<ModelSaveData>
        }

        override fun onPostExecute(result: List<ModelSaveData>?) {
            super.onPostExecute(result)

            mAdapter.updateAdapter(result)

        }

    }

    override fun FilterData(minprice: Long?, maxprice: Long?, instock: Boolean?) {


        GetDataByFilter(this, productAdapter, instock!!).execute(
            minprice.toString(),
            maxprice.toString(),
            categoryId
        )

    }
}
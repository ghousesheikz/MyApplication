package com.test.myapplication.view.activity

import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.test.myapplication.R
import com.test.myapplication.database.DatabaseClient
import com.test.myapplication.database.ModelSaveData
import com.test.myapplication.datamodel.CategoryDetailsResponse
import com.test.myapplication.datamodel.ProductDetailsResponse
import com.test.myapplication.view.adapter.CategoryAdapter
import com.test.myapplication.viewmodel.CategoryViewModel
import com.test.myapplication.viewmodel.ProductViewModel
import kotlinx.android.synthetic.main.activity_category.*

class CategoryActivity : AppCompatActivity() {

    private lateinit var CategoryObserver: Observer<CategoryDetailsResponse>

    private lateinit var ProductObserver: Observer<ProductDetailsResponse>
    private lateinit var CategoryiewModel: CategoryViewModel
    private lateinit var ProductViewModel: ProductViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category)
        CategoryiewModel = CategoryViewModel()
        ProductViewModel = ProductViewModel()


        categorylist?.setLayoutManager(GridLayoutManager(this, 2));
        categorylist?.isNestedScrollingEnabled = false


        initialiseObservers()
        CategoryiewModel.categoryDetailsList.observe(this, CategoryObserver)
        ProductViewModel.productDetailsList.observe(this, ProductObserver)
        progress_bar?.visibility = View.VISIBLE
        fetchCall()
    }


    private fun fetchCall() {
        CategoryiewModel.fetchCategoryDetails()
        ProductViewModel.fetchProductDetails()
    }

    private fun initialiseObservers() {
        CategoryObserver = Observer {
            progress_bar?.visibility = View.GONE
            val categoryResultList = it.getArrayOfProducts()
            Log.e("data", "" + categoryResultList)
            categorylist?.adapter = CategoryAdapter(
                categoryResultList!!, this,
                object : CategoryAdapter.OnItemClickListener {
                    override fun onItemClick(
                        response: CategoryDetailsResponse.ArrayOfProduct,
                        position: Int
                    ) {
                        val intent = Intent(this@CategoryActivity, ProductListActivity::class.java)
                        intent.putExtra("categoryid", response.id)
                        startActivity(intent)
                    }

                })

        }

        ProductObserver = Observer {
            val productResultList = it.getArrayOfProducts()
            Log.e("productdata", "" + (productResultList?.get(0)?.imgUrl ?: String()))
            try {
                GetDataFromDb(this, productResultList).execute()
            } catch (e: Exception) {
                e.stackTrace
            }
        }
    }


    class GetDataFromDb(
        var context: CategoryActivity, var ResultList: List<ProductDetailsResponse.ArrayOfProduct?>
    ) : AsyncTask<Void, Void, List<ModelSaveData>>() {
        private lateinit var modelsaveData: ModelSaveData

        override fun doInBackground(vararg p0: Void?): List<ModelSaveData>? {
            if (DatabaseClient(context).getAppDatabase()!!.dataDao().getAll()!!.size > 0) {
                DatabaseClient(context).getAppDatabase()!!.dataDao().deleteData()
            }

            for (i in 0..ResultList.size) {
                try {
                    modelsaveData = ModelSaveData(
                        0,
                        ResultList[i]!!.name,
                        ResultList[i]!!.category,
                        ResultList[i]!!.imgUrl,
                        ResultList[i]!!.quantity,
                        ResultList[i]!!.description,
                        ResultList[i]!!.price?.toInt()
                    )
                    DatabaseClient(context).getAppDatabase()!!.dataDao().insert(modelsaveData)
                } catch (e: Exception) {
                    e.stackTrace
                }
            }
            return DatabaseClient(context).getAppDatabase()!!.dataDao()
                .getDatabyCategoryid("2") as List<ModelSaveData>
        }

        override fun onPostExecute(result: List<ModelSaveData>?) {
            Log.e("productdata", "" + (result?.get(0)?.imgUrl ?: String()))
        }
    }
}
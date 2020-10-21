package com.test.myapplication.view.activity

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.Gravity
import android.view.Window
import android.view.WindowManager
import androidx.core.util.rangeTo
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.test.myapplication.R
import com.test.myapplication.database.DatabaseClient
import com.test.myapplication.database.ModelSaveData
import com.test.myapplication.datamodel.MovieDetailsResponse
import com.test.myapplication.utils.Constants
import com.test.myapplication.view.adapter.MovieListAdapter
import com.test.myapplication.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.show_dialog.*

class MainActivity : AppCompatActivity() {

    private lateinit var successObserver: Observer<MovieDetailsResponse>
    private var movieAdapter: MovieListAdapter? = null
    private lateinit var viewModel: MainViewModel
    val page: Int = 1
    var mRatingFlag = false
    var mDatesFlag = false

    companion object {
        var recyclerView: RecyclerView? = null
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = MainViewModel()
        MainActivity.recyclerView = findViewById(R.id.movielist);


        movielist?.layoutManager = LinearLayoutManager(this)
        movielist?.isNestedScrollingEnabled = false
        initialiseObservers()
        fetchCall(Constants.KEY, Constants.LANG, page)
        viewModel.movieDetailsList.observe(this, successObserver)

        RatingSort.setOnClickListener {
            try {
                if (!mRatingFlag) {
                    GetRatingDataFromDb(this, "asc").execute()
                    mRatingFlag = true;
                    RatingSort.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_arrow_down, 0)
                } else {
                    GetRatingDataFromDb(this, "desc").execute()
                    mRatingFlag = false
                    RatingSort.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_arrow_up, 0)
                }
            } catch (e: java.lang.Exception) {
                e.stackTrace
            }
        }

        DateSort.setOnClickListener {
            try {
                if (!mDatesFlag) {
                    GetDateDataFromDb(this, "asc").execute()
                    mDatesFlag = true
                    DateSort.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_arrow_down, 0)
                } else {
                    GetDateDataFromDb(this, "desc").execute()
                    mDatesFlag = false
                    DateSort.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_arrow_up, 0)
                }
            } catch (e: java.lang.Exception) {
                e.stackTrace
            }
        }
    }

    private fun fetchCall(key: String, lang: String, moviepage: Int) {
        viewModel.fetchDealerDetails(key, lang, moviepage)
    }

    private fun initialiseObservers() {
        successObserver = Observer {
            val movieResultList = it.results
            Log.e("data", "" + movieResultList)
            if (movieResultList != null) {


                try {
                    GetDataFromDb(this, movieResultList).execute()
                } catch (e: Exception) {
                    e.stackTrace
                }


            }

        }
    }


    class GetDataFromDb(
        var context: MainActivity, var movieResultList: List<MovieDetailsResponse.Result>
    ) : AsyncTask<Void, Void, List<ModelSaveData>>() {
        private lateinit var modelsaveData: ModelSaveData

        override fun doInBackground(vararg p0: Void?): List<ModelSaveData>? {
            if (DatabaseClient(context).getAppDatabase()!!.dataDao().getAll()!!.size > 0) {
                DatabaseClient(context).getAppDatabase()!!.dataDao().deleteData()
            }

            for (i in 0..movieResultList.size) {
                try {
                    modelsaveData = ModelSaveData(
                        0,
                        movieResultList[i].popularity,
                        movieResultList[i].voteCount,
                        movieResultList[i].video,
                        movieResultList[i].posterPath,
                        movieResultList[i].adult,
                        movieResultList[i].backdropPath,
                        movieResultList[i].originalLanguage,
                        movieResultList[i].originalTitle,
                        movieResultList[i].title,
                        movieResultList[i].voteAverage,
                        movieResultList[i].overview,
                        movieResultList[i].releaseDate
                    )
                    DatabaseClient(context).getAppDatabase()!!.dataDao().insert(modelsaveData)
                } catch (e: Exception) {
                    e.stackTrace
                }
            }
            return DatabaseClient(context).getAppDatabase()!!.dataDao()
                .getAll() as List<ModelSaveData>
        }

        override fun onPostExecute(result: List<ModelSaveData>?) {
            MainActivity.recyclerView?.adapter = MovieListAdapter(
                result,
                context,
                object : MovieListAdapter.OnItemClickListener {
                    override fun onItemClick(
                        response: ModelSaveData?,
                        position: Int
                    ) {
                        val dialog = Dialog(context)
                        dialog.setCancelable(true)
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
                        dialog.setContentView(R.layout.show_dialog)
                        val lp = WindowManager.LayoutParams()
                        lp.copyFrom(dialog.window!!.attributes)
                        lp.width = WindowManager.LayoutParams.MATCH_PARENT
                        lp.height = WindowManager.LayoutParams.WRAP_CONTENT
                        lp.gravity = Gravity.CENTER
                        dialog.window!!.attributes = lp
                        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                        dialog.show()

                        Glide.with(context).load(Constants.IMAGE_URL + response!!.posterPath).into(dialog.movie_image)
                        dialog.original_name.text = Html.fromHtml("<font color='#F50057'>" + "<b>" + "Original Name : " + "</b>" + "</font>" + response.originalTitle)
                        dialog.ratings.text = Html.fromHtml("<font color='#F50057'>" + "<b>" + "Ratings : " + "</b>" + "</font>" + response.voteAverage)
                        dialog.release_date.text = Html.fromHtml("<font color='#F50057'>" + "<b>" + "Release Date : " + "</b>" + "</font>" + response.releaseDate)
                        dialog.original_language.text = Html.fromHtml("<font color='#F50057'>" + "<b>" + "Language : " + "</b>" + "</font>" + response.originalLanguage)

                    }
                })
        }
    }

    internal class GetRatingDataFromDb(
        var context: MainActivity,
        var ascdesc: String
    ) : AsyncTask<Void, Void, List<ModelSaveData>?>() {
        private lateinit var modelsaveData: ModelSaveData

        override fun doInBackground(vararg p0: Void?): List<ModelSaveData>? {
            if (ascdesc.equals("asc")) {
                return DatabaseClient(context).getAppDatabase()?.dataDao()
                    ?.getAscendingList() as List<ModelSaveData>
            } else {
                return DatabaseClient(context).getAppDatabase()?.dataDao()
                    ?.getDescendingList() as List<ModelSaveData>
            }
        }

        override fun onPostExecute(result: List<ModelSaveData>?) {
            MainActivity.recyclerView?.adapter = MovieListAdapter(
                result,
                context,
                object : MovieListAdapter.OnItemClickListener {
                    override fun onItemClick(
                        response: ModelSaveData?,
                        position: Int
                    ) {
                        val dialog = Dialog(context)
                        dialog.setCancelable(true)
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
                        dialog.setContentView(R.layout.show_dialog)
                        val lp = WindowManager.LayoutParams()
                        lp.copyFrom(dialog.window!!.attributes)
                        lp.width = WindowManager.LayoutParams.MATCH_PARENT
                        lp.height = WindowManager.LayoutParams.WRAP_CONTENT
                        lp.gravity = Gravity.CENTER
                        dialog.window!!.attributes = lp
                        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                        dialog.show()

                        Glide.with(context).load(Constants.IMAGE_URL + response!!.posterPath).into(dialog.movie_image)
                        dialog.original_name.text = Html.fromHtml("<font color='#F50057'>" + "<b>" + "Original Name : " + "</b>" + "</font>" + response.originalTitle)
                        dialog.ratings.text = Html.fromHtml("<font color='#F50057'>" + "<b>" + "Ratings : " + "</b>" + "</font>" + response.voteAverage)
                        dialog.release_date.text = Html.fromHtml("<font color='#F50057'>" + "<b>" + "Release Date : " + "</b>" + "</font>" + response.releaseDate)
                        dialog.original_language.text = Html.fromHtml("<font color='#F50057'>" + "<b>" + "Language : " + "</b>" + "</font>" + response.originalLanguage)

                    }
                })
        }
    }


    internal class GetDateDataFromDb(
        var context: MainActivity,
        var ascdesc: String
    ) : AsyncTask<Void, Void, List<ModelSaveData>?>() {
        private lateinit var modelsaveData: ModelSaveData

        override fun doInBackground(vararg p0: Void?): List<ModelSaveData>? {
            if (ascdesc.equals("asc")) {
                return DatabaseClient(context).getAppDatabase()?.dataDao()
                    ?.getAscReleaseDates() as List<ModelSaveData>
            } else {
                return DatabaseClient(context).getAppDatabase()?.dataDao()
                    ?.getDescReleaseDates() as List<ModelSaveData>
            }
        }

        override fun onPostExecute(result: List<ModelSaveData>?) {
            MainActivity.recyclerView?.adapter = MovieListAdapter(
                result,
                context,
                object : MovieListAdapter.OnItemClickListener {
                    override fun onItemClick(
                        response: ModelSaveData?,
                        position: Int
                    ) {
                        val dialog = Dialog(context)
                        dialog.setCancelable(true)
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
                        dialog.setContentView(R.layout.show_dialog)
                        val lp = WindowManager.LayoutParams()
                        lp.copyFrom(dialog.window!!.attributes)
                        lp.width = WindowManager.LayoutParams.MATCH_PARENT
                        lp.height = WindowManager.LayoutParams.WRAP_CONTENT
                        lp.gravity = Gravity.CENTER
                        dialog.window!!.attributes = lp
                        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                        dialog.show()

                        Glide.with(context).load(Constants.IMAGE_URL + response!!.posterPath).into(dialog.movie_image)
                        dialog.original_name.text = Html.fromHtml("<font color='#F50057'>" + "<b>" + "Original Name : " + "</b>" + "</font>" + response.originalTitle)
                        dialog.ratings.text = Html.fromHtml("<font color='#F50057'>" + "<b>" + "Ratings : " + "</b>" + "</font>" + response.voteAverage)
                        dialog.release_date.text = Html.fromHtml("<font color='#F50057'>" + "<b>" + "Release Date : " + "</b>" + "</font>" + response.releaseDate)
                        dialog.original_language.text = Html.fromHtml("<font color='#F50057'>" + "<b>" + "Language : " + "</b>" + "</font>" + response.originalLanguage)


                    }
                })
        }
    }


}


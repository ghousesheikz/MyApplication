package com.test.myapplication.network



import com.test.myapplication.datamodel.MovieDetailsResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface iApiService {

//    @GET("/3/movie/top_rated")
//    fun fetchMovieList(@Query(encoded=true,value = "api_key") api_key:String?,
//                    @Query(encoded=true,value = "language") language:String?,
//                    @Query(encoded=true,value = "page") page:Int?): Observable<DataResponse>

    @GET("top_rated?")
    fun getMovieDetails(
        @Query(encoded = true, value = "api_key") api_key: String?,
        @Query(encoded = true, value = "language") language: String?,
        @Query(encoded = true, value = "page") page: Int
    ): Observable<MovieDetailsResponse>


}
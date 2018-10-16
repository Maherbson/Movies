package br.com.filmes.model.interfaces.endpoints

import br.com.filmes.constants.ConstantsUrl
import br.com.filmes.model.entity.GenreResult
import br.com.filmes.model.entity.MovieResult
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieEndpoint {

    interface GetMovie {
        @GET("movie/upcoming")
        fun getMovie(@Query(ConstantsUrl.API_KEY) apiKey: String,
                             @Query(ConstantsUrl.LANGUAGE) language: String,
                             @Query(ConstantsUrl.PAGE) page: Int,
                             @Query(ConstantsUrl.REGION) region: String): Observable<MovieResult>

        @GET("search/movie")
        fun getMovie(@Query(ConstantsUrl.API_KEY) apiKey: String,
                           @Query(ConstantsUrl.LANGUAGE) language: String,
                           @Query(ConstantsUrl.QUERY) query: String,
                           @Query(ConstantsUrl.PAGE) page: Int,
                           @Query(ConstantsUrl.INCLUDE_ADULT) include_adult: Boolean): Observable<MovieResult>
    }

    interface GenreMovie {
        @GET("genre/movie/list")
        fun getGenreMovie(@Query(ConstantsUrl.API_KEY) apiKey: String,
                          @Query(ConstantsUrl.LANGUAGE) language: String): Observable<GenreResult>
    }

}
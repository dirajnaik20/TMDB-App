package com.example.androidlearning.data.repository.movie.datasource

import com.example.androidlearning.data.model.movie.Movie

interface MovieCacheDatasource {
    suspend fun getMoviesFromCache():List<Movie>
    suspend fun saveMoviesToCache(movies:List<Movie>)
}
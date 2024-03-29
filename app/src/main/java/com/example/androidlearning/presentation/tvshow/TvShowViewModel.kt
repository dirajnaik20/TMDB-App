package com.example.androidlearning.presentation.tvshow

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.androidlearning.domain.usecase.GetArtistsUseCase
import com.example.androidlearning.domain.usecase.GetMoviesUseCase
import com.example.androidlearning.domain.usecase.GetTvShowsUseCase
import com.example.androidlearning.domain.usecase.UpdateArtistsUseCase
import com.example.androidlearning.domain.usecase.UpdateTvShowsUseCase

class TvShowViewModel(
    private val getTvShowsUseCase: GetTvShowsUseCase,
    private val updateTvShowsUseCase: UpdateTvShowsUseCase
) : ViewModel() {

    fun getTvShows()= liveData {
        val tvShowList=getTvShowsUseCase.execute()
        emit(tvShowList)
    }

    fun updateTvShows()= liveData {
        val tvShowList=updateTvShowsUseCase.execute()
        emit(tvShowList)
    }
}
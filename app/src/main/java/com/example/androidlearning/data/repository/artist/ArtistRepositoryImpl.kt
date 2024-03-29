package com.example.androidlearning.data.repository.artist

import android.util.Log
import com.example.androidlearning.data.model.artist.Artist
import com.example.androidlearning.data.repository.artist.datasource.ArtistCacheDatasource
import com.example.androidlearning.data.repository.artist.datasource.ArtistLocalDatasource
import com.example.androidlearning.data.repository.artist.datasource.ArtistRemoteDatasource
import com.example.androidlearning.domain.repository.ArtistRepository

class ArtistRepositoryImpl(
    private val artistRemoteDatasource: ArtistRemoteDatasource,
    private val artistLocalDatasource: ArtistLocalDatasource,
    private val artistCacheDatasource: ArtistCacheDatasource
) : ArtistRepository {
    override suspend fun getArtists(): List<Artist>? {
        return getArtistsFromCache()
    }

    override suspend fun updateArtists(): List<Artist>? {
        val newListOfArtists=getArtistsFromAPI()
        artistLocalDatasource.clearAll()
        artistLocalDatasource.saveArtistToDB(newListOfArtists)
        artistCacheDatasource.saveArtistToCache(newListOfArtists)
        return newListOfArtists
    }

    suspend fun getArtistsFromAPI():List<Artist> {
        lateinit var artistList:List<Artist>
        try {
            val response=artistRemoteDatasource.getArtists()
            val body=response.body()
            if (body!=null){
                artistList=body.artists
            }
        }
        catch (exception:Exception){
            Log.i("MyTag",exception.message.toString())
        }

        return artistList
    }
    suspend fun getArtistsFromDB():List<Artist> {
        lateinit var artistList:List<Artist>
        try {
            artistList=artistLocalDatasource.getArtistFromDB()
        }
        catch (exception:Exception){
            Log.i("MyTag",exception.message.toString())
        }

        if (artistList.size>0){
            return artistList
        }
        else{
            artistList=getArtistsFromAPI()
            artistLocalDatasource.saveArtistToDB(artistList)

        }

        return artistList
    }
    suspend fun getArtistsFromCache():List<Artist> {
        lateinit var artistList:List<Artist>
        try {
            artistList=artistCacheDatasource.getArtistFromCache()
        }
        catch (exception:Exception){
            Log.i("MyTag",exception.message.toString())
        }

        if (artistList.size>0){
            return artistList
        }
        else{
            artistList=getArtistsFromDB()
            artistCacheDatasource.saveArtistToCache(artistList)

        }

        return artistList
    }
}
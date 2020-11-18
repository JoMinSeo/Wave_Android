package com.project.wave_v2.data.dao.playinglist

import androidx.room.*
import com.project.wave_v2.data.response.playlist.SongInfo
import com.project.wave_v2.data.response.search.Song

@Dao
    interface PlayingDAO {
        @Query("SELECT * FROM Song")
        @TypeConverter
        fun getAll(): List<Song>

        @Insert(onConflict = OnConflictStrategy.REPLACE)
        fun songInsert(song: Song)

        @Delete
        fun songDelete(song : Song)
    }
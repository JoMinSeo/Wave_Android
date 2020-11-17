package com.project.wave_v2.data.dao.playinglist

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.project.wave_v2.data.response.search.Song

@Database(entities = arrayOf(Song::class), version = 1)
abstract class PlayingRoomDatabase : RoomDatabase() {
    abstract fun playingList(): PlayingDAO

    companion object {

        private var INSTANCE: PlayingRoomDatabase? = null

        fun getInstance(context: Context): PlayingRoomDatabase? {
            if (INSTANCE == null) {
                synchronized(PlayingRoomDatabase::class) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        PlayingRoomDatabase::class.java,
                        "PlayListDB"
                    ).build()
                }
            }
            return INSTANCE
        }
    }
}
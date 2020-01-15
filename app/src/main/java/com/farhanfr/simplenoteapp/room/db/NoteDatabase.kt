package com.farhanfr.simplenoteapp.room.db

import android.content.Context
import android.os.AsyncTask
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.farhanfr.simplenoteapp.room.dao.NoteDao
import com.farhanfr.simplenoteapp.room.table.NoteTable

@Database(entities = [NoteTable::class],version = 2)
abstract class NoteDatabase: RoomDatabase() {

    abstract fun noteDao():NoteDao

    companion object{
        private var instance: NoteDatabase? = null
        fun getInstance(context: Context): NoteDatabase {
            if (instance == null) {
                synchronized(NoteDatabase::class) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        NoteDatabase::class.java, "notes_database"
                    )
                        .fallbackToDestructiveMigration()
                        .addCallback(roomCallback)
                        .build()
                }
            }
            return instance!!
        }

        fun destroyInstance() {
            instance = null
        }

        private val roomCallback = object : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                PopulateDbAsyncTask(instance)
                    .execute()
            }
        }
    }
    class PopulateDbAsyncTask(db: NoteDatabase?) : AsyncTask<Unit, Unit, Unit>() {
        private val noteDao = db?.noteDao()

        override fun doInBackground(vararg p0: Unit?) {
            noteDao?.insert(NoteTable(1, "description 1","qw"))
            noteDao?.insert(NoteTable(2, "description 2","qw"))
            noteDao?.insert(NoteTable(3, "description 3","qw"))
        }
    }

}



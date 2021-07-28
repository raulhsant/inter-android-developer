package me.estudos.applicationcontentprovider.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns._ID

class NotesDatabaseHelper(context: Context) : SQLiteOpenHelper(context, "databaseNotes", null, 1) {
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(
            "CREATE TABLE $NOTES_TABLE (" +
                    "$_ID INTEGER NOT NULL PRIMARY KEY," +
                    "$TITLE TEXT NOT NULL," +
                    "$DESCRIPTION TEXT NOT NULL"
        )
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
    }

    companion object {
        const val NOTES_TABLE: String = "Notes"
        const val TITLE: String = "title"
        const val DESCRIPTION: String = "description"
    }

}
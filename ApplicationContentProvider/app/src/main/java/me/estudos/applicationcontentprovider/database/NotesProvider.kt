package me.estudos.applicationcontentprovider.database

import android.content.ContentProvider
import android.content.ContentValues
import android.content.Context
import android.content.UriMatcher
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.media.UnsupportedSchemeException
import android.net.Uri
import android.provider.BaseColumns._ID
import me.estudos.applicationcontentprovider.database.NotesDatabaseHelper.Companion.NOTES_TABLE

class NotesProvider : ContentProvider() {

    private lateinit var mUriMatcher: UriMatcher
    private lateinit var dbHelper: NotesDatabaseHelper

    override fun onCreate(): Boolean {
        mUriMatcher = UriMatcher(UriMatcher.NO_MATCH)
        mUriMatcher.addURI(AUTHORITY, "notes", NOTES)
        mUriMatcher.addURI(AUTHORITY, "notes/#", NOTES_BY_ID)

        if (context != null) {
            dbHelper = NotesDatabaseHelper(context as Context)
        }

        return true
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        if (mUriMatcher.match(uri) == NOTES_BY_ID) {
            val db: SQLiteDatabase = dbHelper.writableDatabase
            val affectedLines = db.delete(NOTES_TABLE, "$_ID=?", arrayOf(uri.lastPathSegment))
            db.close()
            context?.contentResolver?.notifyChange(uri, null)
            return affectedLines
        } else {
            throw UnsupportedSchemeException("Uri inválida para exclusão")
        }
    }

    override fun getType(uri: Uri): String? =
        throw UnsupportedSchemeException("Uri não implementado")

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        if (mUriMatcher.match(uri) == NOTES) {
            val db: SQLiteDatabase = dbHelper.writableDatabase
            val id = db.insert(NOTES_TABLE, null, values)
            val insertUri = Uri.withAppendedPath(BASE_URI, id.toString())
            db.close()
            context?.contentResolver?.notifyChange(uri, null)
            return insertUri
        } else {
            throw UnsupportedSchemeException("Uri inválida para inserção")
        }
    }

    override fun query(
        uri: Uri, projection: Array<String>?, selection: String?,
        selectionArgs: Array<String>?, sortOrder: String?
    ): Cursor? {
        return when {
            mUriMatcher.match(uri) == NOTES -> {
                val db: SQLiteDatabase = dbHelper.readableDatabase
                val cursor = db.query(
                    NOTES_TABLE,
                    projection,
                    selection,
                    selectionArgs,
                    null,
                    null,
                    sortOrder
                )
                cursor.setNotificationUri(context?.contentResolver, uri)
                cursor
            }
            mUriMatcher.match(uri) == NOTES_BY_ID -> {
                val db: SQLiteDatabase = dbHelper.readableDatabase
                val cursor = db.query(
                    NOTES_TABLE,
                    projection,
                    "$_ID=?",
                    arrayOf(uri.lastPathSegment),
                    null,
                    null,
                    sortOrder
                )
                cursor.setNotificationUri(context?.contentResolver, uri)
                cursor
            }
            else -> {
                throw UnsupportedSchemeException("Uri não implementada")
            }
        }
    }

    override fun update(
        uri: Uri, values: ContentValues?, selection: String?,
        selectionArgs: Array<String>?
    ): Int {
        if (mUriMatcher.match(uri) == NOTES_BY_ID) {
            val db: SQLiteDatabase = dbHelper.writableDatabase
            val affectedLines =
                db.update(NOTES_TABLE, values, "$_ID=?", arrayOf(uri.lastPathSegment))
            db.close()
            context?.contentResolver?.notifyChange(uri, null)
            return affectedLines
        } else {
            throw UnsupportedSchemeException("Uri não implementada")
        }
    }

    companion object {
        const val AUTHORITY = "me.estudos.applicationcontentprovider.provider"
        val BASE_URI = Uri.parse("content://$AUTHORITY")
        val URI_NOTES = Uri.withAppendedPath(BASE_URI, "notes")

        const val NOTES = 1
        const val NOTES_BY_ID = 2
    }
}
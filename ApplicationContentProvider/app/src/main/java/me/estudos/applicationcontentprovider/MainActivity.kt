package me.estudos.applicationcontentprovider

import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.BaseColumns._ID
import androidx.appcompat.app.AppCompatActivity
import androidx.loader.app.LoaderManager
import androidx.loader.content.CursorLoader
import androidx.loader.content.Loader
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import me.estudos.applicationcontentprovider.database.NotesAdapter
import me.estudos.applicationcontentprovider.database.NotesDatabaseHelper.Companion.TITLE
import me.estudos.applicationcontentprovider.database.NotesProvider.Companion.URI_NOTES

class MainActivity : AppCompatActivity(), LoaderManager.LoaderCallbacks<Cursor> {

    private lateinit var noteRecyclerView: RecyclerView
    private lateinit var noteAddButton: FloatingActionButton

    lateinit var adapter: NotesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        noteAddButton = findViewById(R.id.btn_add_note)
        noteAddButton.setOnClickListener {
            NotesDetailFragment().show(
                supportFragmentManager,
                "dialog"
            )
        }

        adapter = NotesAdapter(object : NoteClickedListener {
            override fun noteClickedItem(cursor: Cursor?) {
                val id = cursor?.getLong(cursor.getColumnIndex(_ID))
                val fragment = id?.let { NotesDetailFragment.newInstance(it) }
                fragment?.show(supportFragmentManager, "dialog")
            }

            override fun noteRemoveItem(cursor: Cursor?) {
                val id = cursor?.getLong(cursor.getColumnIndex(_ID))
                contentResolver.delete(Uri.withAppendedPath(URI_NOTES, id.toString()), null, null)
            }

        })
        adapter.setHasStableIds(true)

        noteRecyclerView = findViewById(R.id.notes_recylcer)
        noteRecyclerView.layoutManager = LinearLayoutManager(this)
        noteRecyclerView.adapter = adapter

        LoaderManager.getInstance(this).initLoader(0, null, this)
    }

    override fun onCreateLoader(id: Int, args: Bundle?): Loader<Cursor> =
        CursorLoader(this, URI_NOTES, null, null, null, TITLE)

    override fun onLoadFinished(loader: Loader<Cursor>, data: Cursor?) {
        if (data != null) {
            adapter.setCursor(data)
        }
    }

    override fun onLoaderReset(loader: Loader<Cursor>) {
        adapter.setCursor(null)
    }
}
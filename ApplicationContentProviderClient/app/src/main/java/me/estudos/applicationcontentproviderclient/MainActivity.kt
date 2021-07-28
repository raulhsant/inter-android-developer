package me.estudos.applicationcontentproviderclient

import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.lang.Exception

class MainActivity : AppCompatActivity() {

    private lateinit var notesRecycler: RecyclerView
    private lateinit var notesRefreshButton: FloatingActionButton


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        notesRecycler = findViewById(R.id.client_list)
        notesRefreshButton = findViewById(R.id.btn_client_refresh)

        notesRefreshButton.setOnClickListener { getContentProvider() }
    }

    private fun getContentProvider(){
        try{
            val uri = Uri.parse("content://me.estudos.applicationcontentprovider.provider/notes")
            val cursor: Cursor? = contentResolver.query(uri, null, null, null, "title")
            notesRecycler.apply {
                layoutManager = LinearLayoutManager(this@MainActivity)
                adapter = ClientAdapter(cursor as Cursor)
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }
}
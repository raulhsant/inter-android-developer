package me.estudos.applicationcontentprovider.database

import android.database.Cursor
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import me.estudos.applicationcontentprovider.NoteClickedListener
import me.estudos.applicationcontentprovider.R
import me.estudos.applicationcontentprovider.database.NotesDatabaseHelper.Companion.DESCRIPTION
import me.estudos.applicationcontentprovider.database.NotesDatabaseHelper.Companion.TITLE

class NotesAdapter(private val listener: NoteClickedListener) :
    RecyclerView.Adapter<NotesViewHolder>() {

    private var mCursor: Cursor? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder =
        NotesViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.note_item, parent, false)
        )

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        mCursor?.moveToPosition(position)

        holder.noteTitle.text = mCursor?.getString(mCursor?.getColumnIndex(TITLE) as Int)
        holder.noteDescription.text =
            mCursor?.getString(mCursor?.getColumnIndex(DESCRIPTION) as Int)

        holder.removeNoteButton.setOnClickListener {
            mCursor?.moveToPosition(position)
            listener.noteRemoveItem(mCursor)
            notifyDataSetChanged()
        }

        holder.itemView.setOnClickListener { listener.noteClickedItem(mCursor) }
    }

    override fun getItemCount(): Int = if (mCursor != null) mCursor?.count as Int else 0

    fun setCursor(newCursor: Cursor?) {
        mCursor = newCursor
        notifyDataSetChanged()
    }
}

class NotesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val noteTitle = itemView.findViewById(R.id.note_title) as TextView
    val noteDescription = itemView.findViewById(R.id.note_description) as TextView
    val removeNoteButton = itemView.findViewById(R.id.btn_remove_note) as Button
}

package com.example.myapplication.ui.notes

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.entities.Note

class NotesAdapter(
    private var noteList: List<Note>,
    private val onDeleteClick: (Note) -> Unit // 用 Lambda 替代接口
) : RecyclerView.Adapter<NotesAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false)
        // Pass the onDeleteClickListener to the ViewHolder
        return ViewHolder(view, onDeleteClick) // 传递 Lambda
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val note = noteList[position]
        holder.bind(note)
    }

    override fun getItemCount(): Int {
        return noteList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateNotes(newNotes: List<Note>) {
        noteList = newNotes
        notifyDataSetChanged()
    }

    // ViewHolder class
    class ViewHolder(
        itemView: View,
        private val onDeleteClick: (Note) -> Unit
    ) : RecyclerView.ViewHolder(itemView) {
        private val title: TextView = itemView.findViewById(R.id.title)
        private val content: TextView = itemView.findViewById(R.id.content)
        private val deleteButton: ImageView = itemView.findViewById(R.id.delete_note)

        fun bind(note: Note) {
            title.text = note.title
            content.text = note.content
            deleteButton.setOnClickListener {
                Log.d("NotesAdapter", "Delete button clicked for note: ${note.title}")
                onDeleteClick(note)
            }
        }
    }
}
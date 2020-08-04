package com.junka.jnknotes.presenter.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.junka.jnknotes.data.entities.Note
import com.junka.jnknotes.databinding.ItemContainerNoteBinding
import kotlin.properties.Delegates

class NotesAdapter(notes: List<Note> = emptyList(), val onClickListener: (Note) -> Unit) :
    RecyclerView.Adapter<NotesAdapter.ViewHolder>() {

    var notes: List<Note> by Delegates.observable(notes) { _, _, _ ->
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: ItemContainerNoteBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Note) {

            with(binding) {
                noteTitle.text = item.title

                if(item.subtitle.trim().isEmpty()){
                    noteSubtitle.visibility = View.GONE
                }else{
                    noteSubtitle.visibility = View.VISIBLE
                    noteSubtitle.text = item.subtitle
                }

                noteDate.text = item.date.toString()

                if(item.image.isNotEmpty()){
                    Glide.with(noteImage).load(item.image).into(noteImage)
                    noteImage.visibility = View.VISIBLE
                }else{
                    noteImage.visibility = View.GONE
                }

                root.setBackgroundResource(item.color)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val binding: ItemContainerNoteBinding = ItemContainerNoteBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = notes.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val item = notes[position]
        holder.bind(item)
        holder.itemView.setOnClickListener {
            onClickListener(item)
        }
    }
}
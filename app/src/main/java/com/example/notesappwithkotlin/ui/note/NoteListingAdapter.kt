package com.example.notesappwithkotlin.ui.note

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.notesappwithkotlin.data.model.Note
import com.example.notesappwithkotlin.databinding.ItemNoteLayoutBinding
import com.example.notesappwithkotlin.util.addChip
import com.example.notesappwithkotlin.util.hide
import java.text.SimpleDateFormat

class NoteListingAdapter(
    //Lambda function: called when an item is clicked, passing the item's position and note data.
    val onItemClicked: (Int, Note) -> Unit
) : RecyclerView.Adapter<NoteListingAdapter.MyViewHolder>() {

    val sdf = SimpleDateFormat("dd MMM yyyy")
    private var list: MutableList<Note> = arrayListOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = ItemNoteLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MyViewHolder(itemView)
    }//Inflates the item view layout and returns a MyViewHolder instance.
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = list[position]
        holder.bind(item)
    }//Binds the note data to the item view at the specified position.
    fun updateList(list: MutableList<Note>){
        this.list = list
        notifyDataSetChanged()
    }//Updates the adapter's list with new data and notifies the change.
    fun removeItem(position: Int){
        list.removeAt(position)
        notifyItemChanged(position)
    }//Removes an item from the list at the specified position and notifies the change.
    override fun getItemCount(): Int {
        return list.size
    }//Returns the size of the list.
    inner class MyViewHolder(val binding: ItemNoteLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        //ViewHolder class that binds note data to the item view components.
        fun bind(item: Note){//Binds the note's title, date, tags, and description to the views and sets the click listener for the layout.
            binding.title.setText(item.title)
            binding.date.setText(sdf.format(item.date))
            binding.tags.apply {
                if (item.tags.isNullOrEmpty()){
                    hide()
                }else {
                    removeAllViews()
                    if (item.tags.size > 2) {
                        item.tags.subList(0, 2).forEach { tag -> addChip(tag) }
                        addChip("+${item.tags.size - 2}")
                    } else {
                        item.tags.forEach { tag -> addChip(tag) }
                    }
                }
            }
            binding.desc.apply {
                if (item.description.length > 120){
                    text = "${item.description.substring(0,120)}..."
                }else{
                    text = item.description
                }
            }
            binding.itemLayout.setOnClickListener { onItemClicked.invoke(adapterPosition,item) }
        }
    }
}


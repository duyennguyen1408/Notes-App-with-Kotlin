package com.example.notesappwithkotlin.ui.home

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.notesappwithkotlin.ui.note.NoteListingFragment
import com.example.notesappwithkotlin.ui.task.TaskListingFragment
import com.example.notesappwithkotlin.util.HomeTabs

class HomePagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = HomeTabs.values().size

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            HomeTabs.NOTES.index -> NoteListingFragment.newInstance(HomeTabs.NOTES.name)
            HomeTabs.TASKS.index -> TaskListingFragment.newInstance(HomeTabs.TASKS.name)
            else -> throw IllegalStateException("Fragment not found")
        }
    }
}
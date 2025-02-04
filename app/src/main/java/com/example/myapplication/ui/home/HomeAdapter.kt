package com.example.myapplication.ui.home

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.myapplication.ui.notes.NotesFragment

class HomeAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 3 // 三个 Tab

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> NotesFragment()
            1 -> NotesFragment()
            2 -> NotesFragment()
            else -> NotesFragment()
        }
    }
}
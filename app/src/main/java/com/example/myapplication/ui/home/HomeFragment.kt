package com.example.myapplication.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.databinding.FragmentHomeBinding
import com.example.myapplication.entities.Note

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val notes: MutableList<Note> = mutableListOf(
        Note(
            objectId = "1",
            content = "This is the first note",
            parentObjectId = "0",
            status = 1,
            tags = "work,important",
            createdAt = "2024-02-03T10:00:00Z",
            updatedAt = "2024-02-03T12:00:00Z"
        ),
        Note(
            objectId = "2",
            content = "This is the second note",
            parentObjectId = "0",
            status = 0,
            tags = "personal,todo",
            createdAt = "2024-02-03T11:00:00Z",
            updatedAt = "2024-02-03T13:00:00Z"
        )
    )


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

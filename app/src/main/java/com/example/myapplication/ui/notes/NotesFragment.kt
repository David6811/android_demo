package com.example.myapplication.ui.notes

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.MyApplication
import com.example.myapplication.dao.NoteDao
import com.example.myapplication.databinding.FragmentNotesBinding
import com.example.myapplication.entities.Note
import javax.inject.Inject

class NotesFragment : Fragment() {
    private var _binding: FragmentNotesBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var noteDao: NoteDao

    private val notes: MutableList<Note> = mutableListOf(
        Note(
            objectId = "1",
            title = "This is the first note",
            content = "Houses destroyed as lightning strikes spark new fires amid Victorian heatwave",
            parentObjectId = "0",
            status = 1,
            tags = "work,important",
            createdAt = "2024-02-03T10:00:00Z",
            updatedAt = "2024-02-03T12:00:00Z"
        ),
        Note(
            objectId = "2",
            title = "This is the second note",
            content = "There are fears of more property losses to come as heatwave conditions continue across Victoria.",
            parentObjectId = "0",
            status = 0,
            tags = "personal,todo",
            createdAt = "2024-02-03T11:00:00Z",
            updatedAt = "2024-02-03T13:00:00Z"
        )
    )

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as MyApplication).appComponent.inject(this) // Inject dependencies
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNotesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val notesAdapter = NotesAdapter(emptyList())

        //val notesAdapter = NotesAdapter(notes)

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
            adapter = notesAdapter
        }

        // Observe LiveData and update the adapter when data changes
            noteDao.getAll().observe(viewLifecycleOwner, Observer { notes ->
                notesAdapter.updateNotes(notes) // Update dataset instead of creating a new adapter
            })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

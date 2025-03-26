package com.example.myapplication.ui.notes

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.MyApplication
import com.example.myapplication.dao.NoteDao
import com.example.myapplication.databinding.FragmentNotesBinding
import com.example.myapplication.entities.Note
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class NotesFragment : Fragment()  {
    private var _binding: FragmentNotesBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var noteDao: NoteDao

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

        val notesAdapter = NotesAdapter(emptyList()) { note -> // 直接使用 Lambda
            AlertDialog.Builder(requireContext())
                .setTitle("确认删除")
                .setMessage("确定要删除笔记 '${note.title}' 吗？")
                .setPositiveButton("删除") { _, _ ->
                    viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
                        noteDao.delete(note)
                    }
                }
                .setNegativeButton("取消", null)
                .show()
        }

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
            adapter = notesAdapter
        }

        // Observe LiveData and update the adapter when data changes
        noteDao.getAll().observe(viewLifecycleOwner) { notes ->
            notesAdapter.updateNotes(notes) // Update dataset instead of creating a new adapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

package com.example.myapplication.ui.gallery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.myapplication.databinding.FragmentGalleryBinding
import com.example.myapplication.workers.UploadWorker

class GalleryFragment : Fragment() {

    private var _binding: FragmentGalleryBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val galleryViewModel =
            ViewModelProvider(this).get(GalleryViewModel::class.java)

        _binding = FragmentGalleryBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textGallery
        galleryViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }

        // Set up button click listener
        binding.uploadButton.setOnClickListener {
            scheduleUploadWork()
        }

        return root
    }

    private fun scheduleUploadWork() {
        // Create a WorkRequest for UploadWorker
        val uploadWorkRequest = OneTimeWorkRequestBuilder<UploadWorker>()
            .build()

        // Schedule the work with WorkManager
        WorkManager.getInstance(requireContext())
            .enqueue(uploadWorkRequest)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
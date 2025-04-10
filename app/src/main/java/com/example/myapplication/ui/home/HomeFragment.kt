package com.example.myapplication.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.myapplication.databinding.FragmentHomeBinding // Import binding class
import com.example.myapplication.entities.Note
import com.example.myapplication.usecase.CreateNoteUseCase
import com.example.myapplication.util.ObjectIdGenerator
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null // Nullable binding variable
    private val binding get() = _binding!! // Non-null accessor
    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager: ViewPager2
    private lateinit var adapter: HomeAdapter
    private val compositeDisposable = CompositeDisposable()

    @Inject
    lateinit var createNoteUseCase: CreateNoteUseCase

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout using View Binding
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize views using binding
        tabLayout = binding.tabLayout
        viewPager = binding.viewPager

        // Set up ViewPager2 adapter
        adapter = HomeAdapter(this)
        viewPager.adapter = adapter

        // Set up TabLayout with ViewPager2
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> "Notes"
                1 -> "Template"
                2 -> "Favorite"
                else -> "Notes"
            }
        }.attach()

        // Set FAB click listener
        binding.fab.setOnClickListener {
            val note = Note(
                objectId = ObjectIdGenerator.generateObjectId(),
                title = "Title of Note 1",
                content = "Content of Note 1",
                parentObjectId = "0",
                status = 1,
                tags = "tag1",
                createdAt = "2025-02-03T10:00:00Z",
                updatedAt = "2025-02-03T10:00:00Z"
            )

//            compositeDisposable.add(
//                createNoteUseCase.insertNotes(note)
//                    .subscribeOn(Schedulers.io()) // Run in background
//                    .observeOn(AndroidSchedulers.mainThread()) // Observe on main thread
//                    .subscribe(
//                        { /* Success case - optional */ },
//                        { error -> error.printStackTrace() } // Handle errors
//                    )
//            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        compositeDisposable.clear() // Clear disposables to prevent memory leaks
        _binding = null // Avoid memory leaks by nullifying binding
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose() // Dispose of all subscriptions
    }
}
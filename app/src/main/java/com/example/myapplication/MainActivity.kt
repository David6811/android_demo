package com.example.myapplication

import android.os.Bundle
import android.util.Log
import android.view.Menu
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.lifecycleScope
import com.example.myapplication.dao.NoteDao
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.entities.Note
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    @Inject lateinit var noteDao: NoteDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        println("Dagger: MainActivity onCreate")  // Debugging line
        try {
            (application as MyApplication).appComponent.inject(this)
        } catch (e: Exception) {
            println("Dagger1: Error during injection: ${e.message}")  // Print the exception message
            e.printStackTrace()  // Properly print the stack trace
        }



        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarMain.toolbar)

        binding.appBarMain.fab.setOnClickListener { view ->
            lifecycleScope.launch(Dispatchers.IO) {
                try {
                    val note = Note(
                        objectId = "1",
                        title = "Title of Note 1",
                        content = "Content of Note 1",
                        parentObjectId = "0",
                        status = 1,
                        tags = "tag1",
                        createdAt = "2025-02-03T10:00:00Z",
                        updatedAt = "2025-02-03T10:00:00Z"
                    )

                    noteDao.insertAll(note);

                    val notes = noteDao.getAll()
                    if (notes.isNotEmpty()) {
                        val firstNote = notes[0]
                        println("First note: ${firstNote.title}")
                    } else {
                        println("No notes found in database.")
                    }
                } catch (e: Exception) {
                    e.printStackTrace()  // ✅ Log errors to prevent crashes
                }
            }

//            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                .setAction("Action", null)
//                .setAnchorView(R.id.fab).show()
        }
        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        val searchItem = menu.findItem(R.id.action_search)

        // Set the SearchView explicitly if it's not set in XML
        val searchView = searchItem.actionView as? SearchView
        if (searchView != null) {
            // Expand the search view immediately when menu is created
            searchItem.expandActionView()

            // Optional: Set a hint for the search field
            searchView.queryHint = "Search here..."

            // Set up a listener for search input
            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    // Handle search query submission (e.g., filter data)
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    // Handle live search filtering
                    return false
                }
            })
        } else {
            Log.e("Menu", "SearchView is null!")
        }

        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}
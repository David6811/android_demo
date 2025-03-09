package com.example.myapplication

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Menu
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.example.myapplication.dao.NoteDao
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.entities.Note
import com.example.myapplication.usecase.CreateNoteUseCase
import com.example.myapplication.util.ObjectIdGenerator
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    @Inject
    lateinit var noteDao: NoteDao
    @Inject
    lateinit var createNoteUseCase: CreateNoteUseCase

    private val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create an explicit intent for an Activity in your app.
            val emptyIntent = PendingIntent.getActivity(this, 0, Intent(), PendingIntent.FLAG_IMMUTABLE)

            // Define intents for actions
            val openClipboardIntent = PendingIntent.getActivity(
                this, 0, Intent(this, MainActivity::class.java), PendingIntent.FLAG_IMMUTABLE
            )

            val persistNoteIntent = PendingIntent.getActivity(
                this, 1, Intent(this, MainActivity::class.java), PendingIntent.FLAG_IMMUTABLE
            )

            val captureScreenIntent = PendingIntent.getActivity(
                this, 2, Intent(this, MainActivity::class.java), PendingIntent.FLAG_IMMUTABLE
            )


            val channelId = "action"
            // Create the NotificationChannel.
            val name = getString(R.string.channel_name)
            val descriptionText = getString(R.string.channel_description)
            val importance = NotificationManager.IMPORTANCE_LOW
            val mChannel = NotificationChannel(channelId, name, importance).apply {
                description = descriptionText
                enableLights(true)
                lightColor = Color.BLUE
                enableVibration(true)
            }

            // Register the channel with the system. You can't change the importance
            // or other notification behaviors after this.
            val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(mChannel)

            val bigTextStyle = NotificationCompat.BigTextStyle()
                .bigText("Easily manage your clipboard, extract text from screenshots, and return to AnyCopy anytime.")

            var notification = NotificationCompat.Builder(this, channelId)
                .setSmallIcon(R.drawable.ic_content_copy_grey_24dp)
                .setContentTitle("Clipboard Assistant")
                .setContentText("Easily manage your clipboard, extract text from screenshots, and return to AnyCopy anytime.")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setColor(ContextCompat.getColor(this, R.color.color_primary))
                .setStyle(bigTextStyle)
                // Set the intent that fires when the user taps the notification.
                //.setAutoCancel(true)
                .addAction(
                    NotificationCompat.Action.Builder(
                        R.drawable.ic_menu_slideshow, "Persist", openClipboardIntent
                    ).build()
                )
                .addAction(
                    NotificationCompat.Action.Builder(
                        R.drawable.ic_menu_camera, "Capture", persistNoteIntent
                    ).build()
                )
                .addAction(
                    NotificationCompat.Action.Builder(
                        R.drawable.ic_menu_camera, "Launch", captureScreenIntent
                    ).build()
                )
                .build()

            notificationManager.notify(1, notification) // Display the notification with an ID (e.g., 1)

        }


        
        (application as MyApplication).appComponent.inject(this)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarMain.toolbar)

        binding.appBarMain.fab.setOnClickListener {

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

            compositeDisposable.add(
                createNoteUseCase.insertNotes(note)
                    .subscribeOn(Schedulers.io()) // Run in background
                    .observeOn(AndroidSchedulers.mainThread()) // Observe on main thread
                    .subscribe()
            )
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
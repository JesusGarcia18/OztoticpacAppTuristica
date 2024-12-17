package com.example.oztoticpacappturistica

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.example.oztoticpacappturistica.databinding.ActivityMainBinding
import databases.Sitios
import kotlinx.coroutines.launch
import databases.AppDatabase
import kotlinx.coroutines.withContext
import org.osmdroid.config.Configuration
import kotlinx.coroutines.Dispatchers

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private lateinit var sitios: Sitios
    private lateinit var db: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        val prefs = getSharedPreferences("app_settings", Context.MODE_PRIVATE)
        val themeMode = prefs.getInt("theme_mode", AppCompatDelegate.MODE_NIGHT_NO)
        AppCompatDelegate.setDefaultNightMode(themeMode)

        super.onCreate(savedInstanceState)

        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 1)
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 1
            )
        }

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarMain.toolbar)

        binding.appBarMain.fab.setOnClickListener {
            val dialog = BuscarSitiosFragment()
            dialog.show(supportFragmentManager, "BuscarSitiosDialogFragment")
        }
        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_naturales_culturales , R.id.nav_gallery, R.id.nav_slideshow, R.id.nav_recreativos, R.id.nav_hoteles, R.id.nav_festividades, R.id.nav_historia, R.id.nav_directorio
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "sitios-database"
        ).build()

        sitios = Sitios(db)

        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                val sitiosExistentes = db.sitioDao().getAllSitios()
                if (sitiosExistentes.isEmpty()){
                    sitios.insertarSitios()
                }
            }
        }
    }

    override fun onStart(){
        super.onStart()
        initOsmdroid()
    }

    private fun initOsmdroid(){
        val settings = Configuration.getInstance()
        val sharedPreferences = getSharedPreferences("osmdroid_prefs", MODE_PRIVATE)
        settings.load(applicationContext, sharedPreferences)
    }

    fun updateToolbar(title: String, logoResId: Int) {
        supportActionBar?.apply {
            setTitle(title)
            setLogo(logoResId)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        val currentDestination = navController.currentDestination

        val fragmentWithoutMenu = listOf(
            R.id.settingsFragment,
            R.id.nav_about
        )

        if (currentDestination != null && !fragmentWithoutMenu.contains(currentDestination.id)){
            menuInflater.inflate(R.menu.main, menu)
            return true
        }
        return false
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> {
                findNavController(R.id.nav_host_fragment_content_main).navigate(R.id.settingsFragment)
                true
            }
            R.id.action_about -> {
                findNavController(R.id.nav_host_fragment_content_main).navigate(R.id.nav_about)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

}
package com.vault.secure

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class AppPickerActivity : AppCompatActivity() {

    private lateinit var prefs: Prefs
    private lateinit var grid: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_picker)
        prefs = Prefs(this)
        grid = findViewById(R.id.pickerGrid)
        grid.layoutManager = GridLayoutManager(this, 4)
        findViewById<android.widget.TextView>(R.id.pickerBack).setOnClickListener { finish() }
        reload()
    }

    private fun reload() {
        val hidden = prefs.hiddenPackages()
        // Listing every installed launchable app here is the app reading the
        // device's package list — on Android this is granted automatically
        // via the QUERY_ALL_PACKAGES manifest permission at install time,
        // there is no extra runtime popup for it.
        val items = AppRepo.launchableApps(this).filter { it.packageName !in hidden }.toMutableList()
        grid.adapter = AppGridAdapter(items,
            onClick = { entry ->
                prefs.addHidden(entry.packageName)
                Toast.makeText(this, "${entry.label} Vault ichiga qo'shildi", Toast.LENGTH_SHORT).show()
                finish()
            },
            onLongClick = { _, _ -> })
    }
}

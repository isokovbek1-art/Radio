package com.vault.secure

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * This is the real Home screen once the app is set as default launcher.
 * Any package listed in Prefs.hiddenPackages() is simply left out of the
 * list below, so it never appears here, in the app drawer this launcher
 * provides, or in recents from this screen.
 */
class HomeActivity : AppCompatActivity() {

    private lateinit var prefs: Prefs
    private lateinit var grid: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        prefs = Prefs(this)
        grid = findViewById(R.id.homeGrid)
        grid.layoutManager = GridLayoutManager(this, 4)

        findViewById<android.widget.TextView>(R.id.vaultCorner).setOnClickListener {
            startActivity(Intent(this, VaultActivity::class.java))
        }
    }

    override fun onResume() {
        super.onResume()
        reload()
    }

    private fun reload() {
        val hidden = prefs.hiddenPackages()
        val visible = AppRepo.launchableApps(this).filter { it.packageName !in hidden }.toMutableList()
        grid.adapter = AppGridAdapter(visible,
            onClick = { AppRepo.launch(this, it.packageName) },
            onLongClick = { _, _ -> /* no-op on home; hiding happens via Vault picker */ })
    }

    // Block back button from exiting the launcher to a previous app's screen
    override fun onBackPressed() { /* intentionally does nothing: this is Home */ }
}

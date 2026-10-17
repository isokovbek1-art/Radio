package com.vault.secure

import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.view.animation.OvershootInterpolator
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class VaultActivity : AppCompatActivity() {

    private lateinit var prefs: Prefs
    private lateinit var grid: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vault)
        prefs = Prefs(this)
        grid = findViewById(R.id.vaultGrid)
        grid.layoutManager = GridLayoutManager(this, 3)

        findViewById<android.widget.TextView>(R.id.backBtn).setOnClickListener { finish() }

        val add = findViewById<android.widget.TextView>(R.id.addBtn)
        add.setOnClickListener {
            val sx = ObjectAnimator.ofFloat(add, "scaleX", 1f, 0.85f, 1f)
            val sy = ObjectAnimator.ofFloat(add, "scaleY", 1f, 0.85f, 1f)
            sx.interpolator = OvershootInterpolator(); sy.interpolator = OvershootInterpolator()
            sx.duration = 240; sy.duration = 240
            sx.start(); sy.start()
            startActivity(Intent(this, AppPickerActivity::class.java))
        }
    }

    override fun onResume() {
        super.onResume()
        reload()
    }

    private fun reload() {
        val hidden = prefs.hiddenPackages()
        val sub = findViewById<android.widget.TextView>(R.id.subText)
        sub.text = if (hidden.isEmpty()) "Hozircha bo'sh — pastdagi + tugmasini bosing"
                   else "${hidden.size} ta yashirin ilova · olib tashlash uchun bosib turing"

        val all = AppRepo.launchableApps(this)
        val items = all.filter { it.packageName in hidden }.toMutableList()
        grid.adapter = AppGridAdapter(items,
            onClick = { AppRepo.launch(this, it.packageName) },
            onLongClick = { entry, pos ->
                prefs.removeHidden(entry.packageName)
                (grid.adapter as AppGridAdapter).removeAt(pos)
                sub.text = "${prefs.hiddenPackages().size} ta yashirin ilova"
            })
    }
}

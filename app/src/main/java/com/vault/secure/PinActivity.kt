package com.vault.secure

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.OvershootInterpolator
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

/**
 * This activity is registered with CATEGORY_HOME, so once the user sets
 * this app as their default Launcher, this screen is what appears when
 * they press the Home button. Nothing is reachable without the PIN.
 */
class PinActivity : AppCompatActivity() {

    private lateinit var prefs: Prefs
    private val entered = StringBuilder()
    private lateinit var dots: List<View>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pin)
        prefs = Prefs(this)

        dots = listOf(
            findViewById(R.id.dot0), findViewById(R.id.dot1),
            findViewById(R.id.dot2), findViewById(R.id.dot3)
        )

        val title = findViewById<android.widget.TextView>(R.id.titleText)
        val hint = findViewById<android.widget.TextView>(R.id.hintText)
        if (!prefs.hasPin()) {
            title.text = "Yangi PIN o'rnating"
            hint.text = "4 xonali kodni tanlang"
        }

        val keyIds = intArrayOf(
            R.id.key0, R.id.key1, R.id.key2, R.id.key3, R.id.key4,
            R.id.key5, R.id.key6, R.id.key7, R.id.key8, R.id.key9
        )
        keyIds.forEachIndexed { digit, id ->
            findViewById<Button>(id).setOnClickListener { onDigit(digit.toString()) }
        }
        findViewById<Button>(R.id.keyDel).setOnClickListener {
            if (entered.isNotEmpty()) {
                entered.deleteCharAt(entered.length - 1)
                refreshDots()
            }
        }
    }

    private fun onDigit(d: String) {
        if (entered.length >= 4) return
        entered.append(d)
        bounce(dots[entered.length - 1])
        refreshDots()
        if (entered.length == 4) {
            if (!prefs.hasPin()) {
                prefs.setPin(entered.toString())
                goHome()
            } else if (prefs.checkPin(entered.toString())) {
                goHome()
            } else {
                shakeWrong()
            }
        }
    }

    private fun refreshDots() {
        dots.forEachIndexed { i, v ->
            v.setBackgroundResource(if (i < entered.length) R.drawable.dot_filled else R.drawable.dot_empty)
        }
    }

    private fun bounce(v: View) {
        val sx = ObjectAnimator.ofFloat(v, "scaleX", 1f, 1.6f, 1f)
        val sy = ObjectAnimator.ofFloat(v, "scaleY", 1f, 1.6f, 1f)
        AnimatorSet().apply { playTogether(sx, sy); duration = 260; interpolator = OvershootInterpolator(); start() }
    }

    private fun shakeWrong() {
        val row = findViewById<View>(R.id.dotsRow)
        val anim = ObjectAnimator.ofFloat(row, "translationX", 0f, -24f, 24f, -18f, 18f, -8f, 8f, 0f)
        anim.duration = 420
        anim.start()
        row.postDelayed({
            entered.clear()
            refreshDots()
        }, 420)
    }

    private fun goHome() {
        startActivity(Intent(this, HomeActivity::class.java))
        entered.clear()
    }
}

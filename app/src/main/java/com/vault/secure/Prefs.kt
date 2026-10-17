package com.vault.secure

import android.content.Context
import java.security.MessageDigest

class Prefs(ctx: Context) {
    private val sp = ctx.getSharedPreferences("vault_prefs", Context.MODE_PRIVATE)

    private fun hash(s: String): String {
        val d = MessageDigest.getInstance("SHA-256").digest(s.toByteArray())
        return d.joinToString("") { "%02x".format(it) }
    }

    fun hasPin(): Boolean = sp.contains("pin_hash")
    fun setPin(pin: String) = sp.edit().putString("pin_hash", hash(pin)).apply()
    fun checkPin(pin: String): Boolean = sp.getString("pin_hash", null) == hash(pin)

    fun hiddenPackages(): MutableSet<String> =
        HashSet(sp.getStringSet("hidden_packages", emptySet()) ?: emptySet())

    fun setHiddenPackages(set: Set<String>) =
        sp.edit().putStringSet("hidden_packages", set).apply()

    fun addHidden(pkg: String) {
        val s = hiddenPackages(); s.add(pkg); setHiddenPackages(s)
    }

    fun removeHidden(pkg: String) {
        val s = hiddenPackages(); s.remove(pkg); setHiddenPackages(s)
    }
}

package com.vault.secure

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager

object AppRepo {
    fun launchableApps(ctx: Context): List<AppEntry> {
        val pm = ctx.packageManager
        val intent = Intent(Intent.ACTION_MAIN, null).apply { addCategory(Intent.CATEGORY_LAUNCHER) }
        val resolved = pm.queryIntentActivities(intent, 0)
        return resolved
            .filter { it.activityInfo.packageName != ctx.packageName }
            .map {
                AppEntry(
                    label = it.loadLabel(pm).toString(),
                    packageName = it.activityInfo.packageName,
                    icon = it.loadIcon(pm)
                )
            }
            .distinctBy { it.packageName }
            .sortedBy { it.label.lowercase() }
    }

    fun launch(ctx: Context, pkg: String) {
        val launchIntent = ctx.packageManager.getLaunchIntentForPackage(pkg)
        if (launchIntent != null) ctx.startActivity(launchIntent)
    }
}

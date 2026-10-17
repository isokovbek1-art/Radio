package com.vault.secure

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView

class AppGridAdapter(
    private val items: MutableList<AppEntry>,
    private val onClick: (AppEntry) -> Unit,
    private val onLongClick: (AppEntry, Int) -> Unit
) : RecyclerView.Adapter<AppGridAdapter.VH>() {

    class VH(v: View) : RecyclerView.ViewHolder(v) {
        val icon: android.widget.ImageView = v.findViewById(R.id.appIcon)
        val label: android.widget.TextView = v.findViewById(R.id.appLabel)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_app, parent, false)
        return VH(v)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val item = items[position]
        holder.icon.setImageDrawable(item.icon)
        holder.label.text = item.label
        holder.itemView.setOnClickListener { onClick(item) }
        holder.itemView.setOnLongClickListener { onLongClick(item, position); true }

        // staggered entrance animation, capped delay so long lists don't feel slow
        holder.itemView.alpha = 0f
        holder.itemView.translationY = 40f
        holder.itemView.animate()
            .alpha(1f).translationY(0f)
            .setStartDelay((position % 12) * 35L)
            .setDuration(280)
            .setInterpolator(AnimationUtils.loadInterpolator(holder.itemView.context, android.R.interpolator.decelerate_cubic))
            .start()
    }

    override fun getItemCount() = items.size

    fun removeAt(pos: Int) {
        items.removeAt(pos)
        notifyItemRemoved(pos)
    }
}

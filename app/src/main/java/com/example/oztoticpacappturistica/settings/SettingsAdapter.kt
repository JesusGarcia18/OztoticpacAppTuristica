package com.example.oztoticpacappturistica.settings

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.oztoticpacappturistica.R

data class SettingOption( val iconResId: Int, val title: String, val description: String? = null, val action: () -> Unit)

class SettingsAdapter(private val options: List<SettingOption>) :
    RecyclerView.Adapter<SettingsAdapter.SettingsViewHolder>() {

        class SettingsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val iconImageView: ImageView = itemView.findViewById(R.id.ivSettingIcon)
        val titleTextView: TextView = itemView.findViewById(R.id.tvSettingTitle)
        val descriptionTextView: TextView = itemView.findViewById(R.id.tvSettingDescription)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SettingsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_setting, parent, false)
        return SettingsViewHolder(view)
    }

    override fun onBindViewHolder(holder: SettingsViewHolder, position: Int) {
        val option = options[position]
        holder.iconImageView.setImageResource(option.iconResId)
        holder.titleTextView.text = option.title
        option.description?.let {
            holder.descriptionTextView.visibility = View.VISIBLE
            holder.descriptionTextView.text = it
        }
        holder.itemView.setOnClickListener { option.action() }
    }

    override fun getItemCount(): Int = options.size
}
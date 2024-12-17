package com.example.oztoticpacappturistica.about

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.oztoticpacappturistica.R

data class TextoInfo(val texto: String? = null)

class AboutAdapter(private val contenido: List<TextoInfo>) : RecyclerView.Adapter<AboutAdapter.AboutViewHolder>() {
    class AboutViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textoTextView: TextView = itemView.findViewById(R.id.tvTextoInfo)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AboutViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_info, parent, false)
        return AboutViewHolder(view)
    }

    override fun onBindViewHolder(holder: AboutViewHolder, position: Int) {
        val item = contenido[position]

        if (!item.texto.isNullOrEmpty()) {
            holder.textoTextView.text = item.texto
            holder.textoTextView.visibility = View.VISIBLE
        } else {
            holder.textoTextView.visibility = View.GONE
        }
    }

    override fun getItemCount(): Int = contenido.size

}
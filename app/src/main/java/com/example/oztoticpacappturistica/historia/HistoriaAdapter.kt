package com.example.oztoticpacappturistica.historia

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.oztoticpacappturistica.R

data class ContenidoHistoria(val imagen: Int? = null, val texto: String? = null)

class HistoriaAdapter (private val contenido: List<ContenidoHistoria>) : RecyclerView.Adapter<HistoriaAdapter.HistoriaViewHolder>(){
    class HistoriaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textoTextView: TextView = itemView.findViewById(R.id.tvTextoHistoria)
        val imagenImageView: ImageView = itemView.findViewById(R.id.ivImagenHistoria)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoriaViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_historia, parent, false)
        return HistoriaViewHolder(view)
    }

    override fun onBindViewHolder(holder: HistoriaViewHolder, position: Int) {
        val item = contenido[position]

        if (!item.texto.isNullOrEmpty()) {
            holder.textoTextView.text = item.texto
            holder.textoTextView.visibility = View.VISIBLE
        } else {
            holder.textoTextView.visibility = View.GONE
        }

        if (item.imagen != null) {
            holder.imagenImageView.setImageResource(item.imagen)
            holder.imagenImageView.visibility = View.VISIBLE
        } else {
            holder.imagenImageView.visibility = View.GONE
        }
    }

    override fun getItemCount(): Int = contenido.size
}
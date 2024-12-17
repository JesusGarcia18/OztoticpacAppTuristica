package com.example.oztoticpacappturistica.festividades

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.oztoticpacappturistica.R

data class Festividad(val titulo: String, val descripcion: String, val imagenFestividad: Int)

class FestividadesAdapter(private val festividades: List<Festividad>) :
    RecyclerView.Adapter<FestividadesAdapter.FestividadViewHolder>() {

    class FestividadViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imagenFestividad: ImageView = itemView.findViewById(R.id.ivImagenFestividad)
        val tituloTextView: TextView = itemView.findViewById(R.id.tvTituloFestividad)
        val descripcionTextView: TextView = itemView.findViewById(R.id.tvDescripcionFestividad)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FestividadViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_festividad, parent, false)
        return FestividadViewHolder(view)
    }

    override fun onBindViewHolder(holder: FestividadViewHolder, position: Int) {
        val festividad = festividades[position]
        holder.tituloTextView.text = festividad.titulo
        holder.descripcionTextView.text = festividad.descripcion
        holder.imagenFestividad.setImageResource(festividad.imagenFestividad)
    }

    override fun getItemCount(): Int = festividades.size
}
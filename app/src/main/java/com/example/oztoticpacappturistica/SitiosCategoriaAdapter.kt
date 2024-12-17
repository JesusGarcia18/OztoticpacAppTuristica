package com.example.oztoticpacappturistica

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import databases.Sitio

class SitiosCategoriaAdapter(
    private val sitios: List<Sitio>, private val
    onClick: (Sitio) -> Unit, private val imagenesSitios: Map<String, Int>
    ) : RecyclerView.Adapter<SitiosCategoriaAdapter.SitioViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SitioViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_sitio_categoria, parent, false)
        return SitioViewHolder(view)
    }

    class SitioViewHolder(itemView: View) : RecyclerView.ViewHolder (itemView){
        val imagenSitio: ImageView = itemView.findViewById(R.id.imagenSitio)
        val nombreSitio: TextView = itemView.findViewById(R.id.nombreSitio)

        fun bind(sitio: Sitio, onClick: (Sitio) -> Unit){
            itemView.setOnClickListener { onClick(sitio) }
        }
    }

    override fun onBindViewHolder(holder: SitioViewHolder, position: Int) {
        val sitio = sitios[position]
        holder.bind(sitio, onClick)

        val imagenDrawableId = imagenesSitios[sitio.nombre]

        if (imagenDrawableId != null) {
            Glide.with(holder.itemView.context)
                .load(imagenDrawableId)
                .into(holder.imagenSitio)
        } else {
            holder.imagenSitio.setImageResource(R.drawable.ic_menu_gallery)
        }
        holder.nombreSitio.text = sitio.nombre
    }

        override fun getItemCount(): Int {
            return sitios.size
        }
}
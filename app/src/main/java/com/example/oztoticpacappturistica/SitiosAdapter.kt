package com.example.oztoticpacappturistica

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import databases.Sitio

class SitiosAdapter (private val onClick: (Sitio) -> Unit
    ) : ListAdapter<Sitio, SitiosAdapter.SitioViewHolder>(SitioDiffCallback()) {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SitioViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val view = inflater.inflate(R.layout.item_sitio, parent, false)
            return SitioViewHolder(view)
        }

        override fun onBindViewHolder(holder: SitioViewHolder, position: Int) {
            holder.bind(getItem(position), onClick)
        }

        fun updateList(newList: List<Sitio>) {
            submitList(newList)
        }

        class SitioViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            private val nombreSitio: TextView = itemView.findViewById(R.id.nombreSitio)
            private val categoriaSitio: TextView = itemView.findViewById(R.id.categoriaSitio)

            fun bind(sitio: Sitio, onClick: (Sitio) -> Unit) {
                nombreSitio.text = sitio.nombre
                categoriaSitio.text = sitio.categoria
                itemView.setOnClickListener { onClick(sitio) }
            }
        }
    class SitioDiffCallback : DiffUtil.ItemCallback<Sitio>(){
        override fun areItemsTheSame(oldItem: Sitio, newItem: Sitio): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Sitio, newItem: Sitio): Boolean {
            return oldItem == newItem
        }
    }
}
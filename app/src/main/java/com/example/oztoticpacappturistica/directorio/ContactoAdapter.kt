package com.example.oztoticpacappturistica.directorio

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.oztoticpacappturistica.R

data class ContactoSitio(val nombreContacto: String?, val contactos: String?)

class ContactoAdapter(private var contactos: List<ContactoSitio>, private val onContactCopied: (String) -> Unit) : RecyclerView.Adapter<ContactoAdapter.ContactoViewHolder>(){

    class ContactoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nombreTextView: TextView = itemView.findViewById(R.id.tvNombreContacto)
        val numeroTextView: TextView = itemView.findViewById(R.id.tvNumeroContacto)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_contacto, parent, false)
        return ContactoViewHolder(view)
    }

    override fun onBindViewHolder(holder: ContactoViewHolder, position: Int) {
        val contacto = contactos[position]
        holder.nombreTextView.text = contacto.nombreContacto
        holder.numeroTextView.text = contacto.contactos

        // Copiar al portapapeles al hacer clic en el número
        holder.numeroTextView.setOnClickListener {
            onContactCopied(contacto.contactos!!)
        }
    }

    override fun getItemCount(): Int = contactos.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(newContactos: List<ContactoSitio>){
        contactos = newContactos
        notifyDataSetChanged()
    }
}
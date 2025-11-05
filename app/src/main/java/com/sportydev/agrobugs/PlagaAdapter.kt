package com.sportydev.agrobugs

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class PlagaAdapter(
    private val context: Context,
    private val pestList: List<Plaga>,
    private val listener: OnItemClickListener
) : RecyclerView.Adapter<PlagaAdapter.PestViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(pest: Plaga)
    }

    // El ViewHolder ahora maneja el clic y llama a la interfaz.
    inner class PestViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        val commonName: TextView = itemView.findViewById(R.id.tvCommonName)
        val scientificName: TextView = itemView.findViewById(R.id.tvScientificName)
        val description: TextView = itemView.findViewById(R.id.tvDescription)
        val pestImage: ImageView = itemView.findViewById(R.id.ivPestImage)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                val clickedPest = pestList[position]
                // Llama al método de la interfaz, que la Activity implementará.
                listener.onItemClick(clickedPest)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PestViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_plaga, parent, false)
        return PestViewHolder(view)
    }

    override fun getItemCount(): Int {
        return pestList.size
    }

    override fun onBindViewHolder(holder: PestViewHolder, position: Int) {
        val currentPest = pestList[position]
        //claseplaga
        holder.commonName.text = currentPest.nomPlaga
        holder.scientificName.text = currentPest.nomcientifico
        holder.description.text = currentPest.descPlaga

        // lógica para cargar la imagen
        val firstImageName = currentPest.imageName.firstOrNull()

        if (firstImageName != null) {
            val imageId = context.resources.getIdentifier(
                firstImageName.trim(), // .trim() por si hay espacios
                "drawable",
                context.packageName
            )

            if (imageId != 0) {
                holder.pestImage.setImageResource(imageId)
            } else {
                // imagen por defecto
                holder.pestImage.setImageResource(R.drawable.img_question)
            }
        } else {
            // Si la lista está vacía, también ponemos la imagen por defecto
            holder.pestImage.setImageResource(R.drawable.img_question)
        }
    }
}
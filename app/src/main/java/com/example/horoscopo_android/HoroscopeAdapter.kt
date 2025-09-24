package com.example.horoscopo_android

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class HoroscopeAdapter(val items: List<Horoscope>) : RecyclerView.Adapter<HoroscopeViewHolder>()
{
    //CUAL ES LA LISTA PARA LOS ELEMENTOS
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HoroscopeViewHolder {
        //
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_horoscope,parent,false)
        return HoroscopeViewHolder(view)
    }

    //CUALES SON LOS DATOS PARA EL ELEMENTO QUE ESTA EN LA POSICION
    override fun onBindViewHolder(
        holder: HoroscopeViewHolder,
        position: Int
    ) {
        val item = items[position]
        holder.render(item)
    }

    //ELEMENTOS QUE SE QUIEREN LISTAR
    override fun getItemCount(): Int {
        return items.size
    }
}

class HoroscopeViewHolder(view: View) : RecyclerView.ViewHolder(view)
{
    //DECLARAMOS LAS VARIABLES Y ENLAZAMOS CON LOS VIEW
    val ivItemImage: ImageView = view.findViewById(R.id.idIvItemImage)
    val tvItemName: TextView = view.findViewById(R.id.idTvItemName)
    val tvItemDates: TextView = view.findViewById(R.id.idTvItemDates)

    //
    fun render(horoscope: Horoscope)
    {
        ivItemImage.setImageResource(horoscope.icon)
        tvItemName.setText(horoscope.name)
        tvItemDates.setText(horoscope.dates)
    }
}
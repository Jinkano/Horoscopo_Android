package com.example.horoscopo_android.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.horoscopo_android.data.Horoscope
import com.example.horoscopo_android.R

class HoroscopeAdapter(val items: List<Horoscope>, val onClickListener: (Int)-> Unit) : RecyclerView.Adapter<HoroscopeViewHolder>()
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

        //LLAMAMOS A LA FUNCION LAMDA DECLARADA EN EL COMNSTRUCTOR
        holder.itemView.setOnClickListener { onClickListener(position) }
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

    //
    init {
        view.setOnClickListener { v: View->
            val position: Int = layoutPosition
            Toast.makeText(view.context,"${position} probando", Toast.LENGTH_SHORT).show()


        }
    }
}
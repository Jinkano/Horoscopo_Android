package com.example.horoscopo_android.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.horoscopo_android.data.Horoscope
import com.example.horoscopo_android.R
import com.example.horoscopo_android.utils.SessionManager

class HoroscopeAdapter(
    var items: List<Horoscope>,
    val onClickListener: (Int)-> Unit,
    val layout: Int
) : RecyclerView.Adapter<HoroscopeViewHolder>()
{
    /*
    * CUAL ES LA LISTA PARA LOS ELEMENTOS
    * */
    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int
                                ): HoroscopeViewHolder
    {
        //
        //val view = LayoutInflater.from(parent.context).inflate(R.layout.item_horoscope_list,parent,false)
        //val view = LayoutInflater.from(parent.context).inflate(R.layout.item_horoscope_grid,parent,false)
        val view = LayoutInflater.from(parent.context).inflate(layout,parent,false)//En esta opción se agrega el parametro layout
        return HoroscopeViewHolder(view)
    }

    /*
    * CUALES SON LOS DATOS PARA EL ELEMENTO QUE ESTA EN LA POSICION
    * */
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

    /*
    * CREAMOS LA FUNCION updateItems QUE ACTUALIZARA LA LISTA CON LA BUSQUEDA
    * */
    fun updateItems(items: List<Horoscope>){
        this.items = items
        notifyDataSetChanged()
    }
}

class HoroscopeViewHolder(view: View) : RecyclerView.ViewHolder(view)
{
    //DECLARAMOS LAS VARIABLES Y ENLAZAMOS CON LOS VIEW
    val ivItemImage: ImageView = view.findViewById(R.id.idIvItemImage)
    val tvItemName: TextView = view.findViewById(R.id.idTvItemName)
    val tvItemDates: TextView = view.findViewById(R.id.idTvItemDates)
    val ivItemFavorite: ImageView = view.findViewById(R.id.idIvItemFavorite)

    //
    fun render(horoscope: Horoscope)
    {
        ivItemImage.setImageResource(horoscope.icon)
        tvItemName.setText(horoscope.name)
        tvItemDates.setText(horoscope.dates)
        //ivItemFavorite.setImageResource()

        val session = SessionManager(itemView.context)
        if (session.isFavorite(horoscope.id))
        {
            ivItemFavorite.visibility = View.VISIBLE
        } else {
            ivItemFavorite.visibility = View.GONE
        }

    }
}
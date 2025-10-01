package com.example.horoscopo_android.activities

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.horoscopo_android.R
import com.example.horoscopo_android.adapters.HoroscopeAdapter
import com.example.horoscopo_android.data.Horoscope

class MainActivity : AppCompatActivity()
{
    //VARIABLE PARA EL RECYCLERVIEW
    lateinit var rvListHoroscope: RecyclerView

    //VARIABLE PARA EL ADAPTADOR DEL RECYCLERVIEW
    lateinit var adapter: HoroscopeAdapter

    //VARIABLE QUE RECIBE LA LISTA CON LOS HOROSCOPOS
    var horoscopeList: List<Horoscope> = Horoscope.getAll()

    /*
    * INICIO DEL ONCREATE
    * */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }//INICIO ONCREATE

        //CAMBIAR EL TITULO DE LA APLICACION
        supportActionBar?.setTitle(R.string.txt_title_activity_main)
        rvListHoroscope = findViewById(R.id.idRvListHoroscope)

        adapter = HoroscopeAdapter(horoscopeList,::onItemClickListener)
        /*
        * SE PUEDE HACER USO DE LA FUNCION LAMDA DE ESTAS DOS MANERAS
        *
        * LA PRIMERA : val adapter = HoroscopeAdapter(horoscopeList,::onClickListener)
        * EN EL PRIMER CASO SE USAN DOS FUNCIONES, LLAMA A LA FUNCIÓN onClickListener QUE A SU VEZ
        * LLAMA A LA FUNCIÓN goToDetail
        *
        * LA SEGUNDA : val adapter = HoroscopeAdapter(horoscopeList,{ it ->
            val horoscope = horoscopeList[it]
            goToDetail(horoscope)
        })//IT ES POR DEFECTO DE LA FUNCION LAMDA, SE PUEDE */

        rvListHoroscope.adapter = adapter
        //rvListHoroscope.layoutManager  = LinearLayoutManager(this)//, LinearLayoutManager.VERTICAL,false)
        rvListHoroscope.layoutManager  = GridLayoutManager(this,2)

    }
    /*
    * FIN ONCREATE
    * */

    /*
    * actualizar los items de la lista
    * */
    override fun onResume() {
        super.onResume()
        adapter.updateItems(horoscopeList)
    }

    //
    override fun onCreateOptionsMenu(menu: Menu?): Boolean
    {
        menuInflater.inflate(R.menu.activity_main_menu,menu)

        val searchView = menu!!.findItem(R.id.idMnSearch).actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{

            //consejo : cuando esta en internet
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            /*
            * quitamos el ? de (newText: String?) para indicarle que
            * */
            override fun onQueryTextChange(newText: String): Boolean {
                /*
                * consejo : cuando estan en el telefono usar este metodo
                * para buscar en la lista
                * */
                horoscopeList = Horoscope.getAll().filter {
                    getString(it.name).contains(newText,true) ||
                    getString(it.dates).contains(newText,true) }

                //LLAMAMOS A LA FUNCION updateItems DEL ADAPTADOR HOROSCOPE ADAPTER
                adapter.updateItems(horoscopeList)
                return true
            }

        })
        //
        return true//super.onCreateOptionsMenu(menu)
    }

    //
    override fun onOptionsItemSelected(item: MenuItem): Boolean
    {
        return when (item.itemId) {
            R.id.idMnSearch -> {
                //

                Toast.makeText(this,"BUSCAR", Toast.LENGTH_SHORT).show()
                true
            }

            else -> {super.onOptionsItemSelected(item)}
        }
    }


    //
    fun goToDetail(horoscope: Horoscope)
    {
        val intent = Intent(this, DailyHoroscope::class.java)
        intent.putExtra("HOROSCOPE_ID",horoscope.id)
        startActivity(intent)
    }

    //
    fun onItemClickListener(position: Int)
    {
        val horoscope = horoscopeList[position]
        goToDetail(horoscope)
    }

}
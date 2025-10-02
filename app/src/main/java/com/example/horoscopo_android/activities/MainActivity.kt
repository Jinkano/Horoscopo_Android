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
import com.example.horoscopo_android.utils.search

class MainActivity : AppCompatActivity()
{
    //VARIABLE PARA EL RECYCLERVIEW
    lateinit var rvListHoroscope: RecyclerView

    //VARIABLE PARA EL ADAPTADOR DEL RECYCLERVIEW
    lateinit var adapter: HoroscopeAdapter

    //VARIABLE QUE RECIBE LA LISTA CON LOS HOROSCOPOS
    var horoscopeList: List<Horoscope> = Horoscope.getAll()

    //
    var isGridViewEnabled = false
    lateinit var viewModeMenu: MenuItem

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

        //ENLAZAMOS LA VARIABLE CON EL CONTROL
        rvListHoroscope = findViewById(R.id.idRvListHoroscope)

        //CAMBIAR EL TITULO DE LA APLICACION
        supportActionBar?.setTitle(R.string.txt_title_activity_main)

        setupViewMode()
        //adapter = HoroscopeAdapter(horoscopeList,::onItemClickListener)
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

        /*
        * SE COMENTA ESTAS LINEAS PORQUE SE VAN A LA FUNCION SETUPVIEWMODE
        * */
        //rvListHoroscope.adapter = adapter
        //rvListHoroscope.layoutManager  = LinearLayoutManager(this)//, LinearLayoutManager.VERTICAL,false)
        //rvListHoroscope.layoutManager  = GridLayoutManager(this,2)

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

    //override fun onCreateOptionsMenu(menu: Menu?): Boolean
    //QUITAMOS EL SIGNO DE ? PARA INDICAR QUE SI EXISTE EL MENU
    override fun onCreateOptionsMenu(menu: Menu): Boolean
    {
        menuInflater.inflate(R.menu.activity_main_menu,menu)

        viewModeMenu = menu.findItem(R.id.idMnViewMode)
        setViewModeMenu()

        //val searchView = menu!!.findItem(R.id.idMnSearch).actionView as SearchView
        val searchView = menu.findItem(R.id.idMnSearch).actionView as SearchView

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

                    /*getString(it.name).contains(newText,true) ||
                    getString(it.dates).contains(newText,true)*/
                    /*
                    * search SE IMPORTA DE LA CLASE StringExtensions QUE ESTA EN EL PAQUETE utils
                    * */
                    getString(it.name).search(newText) ||
                    getString(it.dates).search(newText) }

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
            R.id.idMnViewMode -> {
                //
                isGridViewEnabled = !isGridViewEnabled
                setupViewMode()
                setViewModeMenu()
                true
            }
            else -> {super.onOptionsItemSelected(item)}
        }
    }

    //
    private fun setupViewMode()
    {
        if (isGridViewEnabled)
        {
            adapter = HoroscopeAdapter(horoscopeList,::onItemClickListener, R.layout.item_horoscope_grid)
            rvListHoroscope.layoutManager = GridLayoutManager(this, 2)
        }
        else
        {
            adapter = HoroscopeAdapter(horoscopeList,::onItemClickListener, R.layout.item_horoscope_list)
            rvListHoroscope.layoutManager = LinearLayoutManager(this)
        }
        rvListHoroscope.adapter = adapter
    }

    //
    private fun setViewModeMenu()
    {
        if (isGridViewEnabled)
        {
            viewModeMenu.setIcon(R.drawable.ic_mn_list_view_24)
        }
        else
        {
            viewModeMenu.setIcon(R.drawable.ic_mn_grid_view_24)
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
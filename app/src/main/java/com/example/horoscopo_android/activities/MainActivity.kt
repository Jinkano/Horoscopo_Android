package com.example.horoscopo_android.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.horoscopo_android.R
import com.example.horoscopo_android.adapters.HoroscopeAdapter
import com.example.horoscopo_android.data.Horoscope

class MainActivity : AppCompatActivity()
{
    //
    lateinit var rvListHoroscope: RecyclerView

    //lateinit var botoncito : Button

    //
    val horoscopeList: List<Horoscope> = Horoscope.Companion.getAll()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        //INICIO ONCREATE

        rvListHoroscope = findViewById(R.id.idRvListHoroscope)

        val adapter = HoroscopeAdapter(horoscopeList,{ it ->
            val horoscope = horoscopeList[it]
            goToDetail(horoscope)
        })//IT ES POR DEFECTO DE LA FUNCION LAMDA se puede renombrar por una variable

        rvListHoroscope.adapter = adapter
        rvListHoroscope.layoutManager  = LinearLayoutManager(this)//, LinearLayoutManager.VERTICAL,false)

        /*
        *
        val botoncito = findViewById<Button>(R.id.button)
        botoncito.setOnClickListener { goToDetail() }
        * */
        //botoncito.setOnClickListener { goToDetail() }

    //FIN ONCREATE
    }

    //
    fun goToDetail(horoscope: Horoscope)
    {
        val intent = Intent(this, DailyHoroscope::class.java)
        intent.putExtra("HOROSCOPE_ID",horoscope.id)
        startActivity(intent)
    }

}
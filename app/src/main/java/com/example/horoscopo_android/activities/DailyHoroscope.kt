package com.example.horoscopo_android.activities

import android.os.Bundle
import android.se.omapi.Session
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.horoscopo_android.R
import com.example.horoscopo_android.data.Horoscope
import com.example.horoscopo_android.utils.SessionManager
import kotlin.time.Duration

class DailyHoroscope : AppCompatActivity()
{
    //
    lateinit var ivImage: ImageView
    lateinit var tvDates: TextView
    lateinit var tvDailyHoroscope: TextView
    lateinit var horoscope: Horoscope
    lateinit var session: SessionManager
    lateinit var favoriteMenu: MenuItem
    var isFavorite = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_daily_horoscope)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }//INICIO DEL ONCREATE

        //PERSONALIZAR EL ACTIONBAR
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_launcher_foreground)
        supportActionBar?.setTitle("JWIR")
        supportActionBar?.setSubtitle("InRi")

        //ENLAZAMOS LOS ELEMENTOS CON LAS VARIABLES
        ivImage = findViewById(R.id.idIvImage)
        tvDates = findViewById(R.id.idTvDates)
        tvDailyHoroscope = findViewById(R.id.idTvDailyHoroscope)

        //
        session = SessionManager(this)

        //RECIBIMOS EL INTENT DEL OTRO ACTIVITY
        val id = intent.getStringExtra("HOROSCOPE_ID")!!//CON ESTOS SIGNOS !! LE DECIMOS QUE NO HABRAN NULOS

        //
        isFavorite = session.isFavorite(id)
        //VARIABLE PARA
        horoscope = Horoscope.getById(id)

        //PASAMOS LOS VALORES RECIBIDOS
        ivImage.setImageResource(horoscope.icon)
        tvDates.setText(horoscope.dates)
        tvDailyHoroscope.setText(horoscope.name)

    }//fin oncreate

    //
    override fun onCreateOptionsMenu(menu: Menu): Boolean
    {
        menuInflater.inflate(R.menu.activity_daily_horoscope_menu,menu)

        //
        favoriteMenu = menu.findItem(R.id.idMnFavorite)
        setFavoriteMenu()


        return true//super.onCreateOptionsMenu(menu)
    }


    //
    override fun onOptionsItemSelected(item: MenuItem): Boolean
    {
        return when (item.itemId) {
            R.id.idMnFavorite -> {
                //
                isFavorite = !isFavorite
                if (isFavorite)
                {
                    session.setFavorite(horoscope.id)
                }
                else {
                    session.setFavorite("")
                }
                setFavoriteMenu()
                //Toast.makeText(this,"MARCADO COMO FAVORITO", Toast.LENGTH_SHORT).show()
                true
            }

            R.id.idMnShare -> {
                Toast.makeText(this,"COMPARTIR", Toast.LENGTH_SHORT).show()
                true
            }

            android.R.id.home -> {
                    finish()
                    true
                }

            else -> {super.onOptionsItemSelected(item)}
        }
    }

    //

    fun setFavoriteMenu() {
        if (isFavorite) {
            favoriteMenu.setIcon(R.drawable.ic_mn_favorite_select_24)
        } else {
            favoriteMenu.setIcon(R.drawable.ic_mn_favorite_24)
        }
    }
}
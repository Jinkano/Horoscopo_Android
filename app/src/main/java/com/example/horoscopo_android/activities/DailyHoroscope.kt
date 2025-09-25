package com.example.horoscopo_android.activities

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.horoscopo_android.R
import com.example.horoscopo_android.data.Horoscope

class DailyHoroscope : AppCompatActivity()
{
    //
    lateinit var ivImage: ImageView
    lateinit var tvDates: TextView
    lateinit var tvDailyHoroscope: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_daily_horoscope)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        //INICIO DEL ONCREATE

        //ENLAZAMOS LOS ELEMENTOS CON LAS VARIABLES
        ivImage = findViewById(R.id.idIvImage)
        tvDates = findViewById(R.id.idTvDates)
        tvDailyHoroscope = findViewById(R.id.idTvDailyHoroscope)

        //RECIBIMOS EL INTENT DEL OTRO ACTIVITY
        val id = intent.getStringExtra("HOROSCOPE_ID")!!//CON ESTOS SIGNOS !! LE DECIMOS QUE NO HABRAN NULOS

        //VARIABLE PARA
        val horoscope = Horoscope.getById(id)

        //PASAMOS LOS VALORES RECIBIDOS
        ivImage.setImageResource(horoscope.icon)
        tvDates.setText(horoscope.dates)
        tvDailyHoroscope.setText(horoscope.name)


        //fin oncreate
    }
}
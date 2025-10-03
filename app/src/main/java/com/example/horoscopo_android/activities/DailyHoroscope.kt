package com.example.horoscopo_android.activities

import android.os.Bundle
import android.se.omapi.Session
import android.util.Log
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
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.progressindicator.LinearProgressIndicator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import javax.net.ssl.HttpsURLConnection
import kotlin.time.Duration

class DailyHoroscope : AppCompatActivity()
{
    //
    lateinit var ivImage: ImageView
    lateinit var tvDates: TextView
    lateinit var tvDailyHoroscope: TextView
    lateinit var lpiDailyHoroscope: LinearProgressIndicator
    lateinit var bnvDailyHoroscope: BottomNavigationView

    //
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
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0)//systemBars.bottom
            insets
        }//INICIO DEL ONCREATE

        //PERSONALIZAR EL ACTIONBAR
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_mn_back_24)
        supportActionBar?.setTitle(R.string.txt_title_daily_horoscope)
        //supportActionBar?.setSubtitle("InRi")

        //ENLAZAMOS LOS ELEMENTOS CON LAS VARIABLES
        ivImage = findViewById(R.id.idIvImage)
        tvDates = findViewById(R.id.idTvDates)
        tvDailyHoroscope = findViewById(R.id.idTvDailyHoroscope)
        lpiDailyHoroscope = findViewById(R.id.idLpiDailyHoroscope)
        bnvDailyHoroscope = findViewById(R.id.idBnvDailyHoroscope)

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
        //tvDailyHoroscope.setText(horoscope.name)
        supportActionBar?.setSubtitle(horoscope.name)

        //
        bnvDailyHoroscope.setOnItemSelectedListener { item ->
            when (item.itemId){
                R.id.idMnToday ->{
                    getHoroscopeLuck("daily")
                    true
                }
                R.id.idMnWeek ->{
                    getHoroscopeLuck("weekly")
                    true
                }
                R.id.idMnMonth ->{
                    getHoroscopeLuck("monthly")
                    true
                }
                else -> false
            }
        }

        //
        getHoroscopeLuck()

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

    //
    fun getHoroscopeLuck(period: String = "daily")
    {
        //
        lpiDailyHoroscope.show()

        //
        tvDailyHoroscope.setText(R.string.txt_consulting)

        //
        CoroutineScope(Dispatchers.IO).launch {
            val url = URL("https://horoscope-app-api.vercel.app/api/v1/get-horoscope/$period?sign=${horoscope.id}&day=TODAY")
            // HTTP Connexion
            val connection = url.openConnection() as HttpsURLConnection
            // Method
            connection.setRequestMethod("GET")

            try {
                // Response code
                val responseCode = connection.getResponseCode()

                if (responseCode == HttpURLConnection.HTTP_OK) {
                    // Read the response
                    val response = readStream(connection.inputStream)

                    val jsonResponse = JSONObject(response)
                    val result = jsonResponse.getJSONObject("data").getString("horoscope_data")

                    CoroutineScope(Dispatchers.Main).launch {
                        tvDailyHoroscope.text = result
                        lpiDailyHoroscope.hide()
                    }

                    Log.i("API", result)
                } else {
                    Log.e("API", "Server response: $responseCode")
                }
            } catch (e: Exception) {
                Log.e("API", "Error", e)
            } finally {
                connection.disconnect()
            }
        }
    }

    //

    fun readStream (input: InputStream) : String {
        val reader = BufferedReader(InputStreamReader(input))
        val response = StringBuffer()
        var inputLine: String? = null

        while ((reader.readLine().also { inputLine = it }) != null) {
            response.append(inputLine)
        }
        reader.close()
        return response.toString()
    }

    /*FIN DEL CÓDIGO*/
}
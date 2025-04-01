package com.example.zodiacapp.activities

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.zodiacapp.R
import com.example.zodiacapp.data.Horoscope
import com.example.zodiacapp.data.HoroscopeProvider
import com.example.zodiacapp.utils.SessionManager


class DetailActivity : AppCompatActivity() {


    lateinit var nameTextView: TextView
    lateinit var datesTextView: TextView
    lateinit var iconImageView: ImageView
    lateinit var  horoscope : Horoscope
    lateinit var session:SessionManager
    var isfavorite = false
    lateinit var  favoritemenuitem : MenuItem

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_detail)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
         session = SessionManager(this)
        nameTextView = findViewById(R.id.nameTextView)
        datesTextView = findViewById(R.id.datesTextView)
        iconImageView =findViewById(R.id.iconImageView)

         val id = intent.getStringExtra("HOROSCOPE_ID")!!


        horoscope = HoroscopeProvider.getById(id)!!

        isfavorite = session.getFavoriteHoroscope() == horoscope.id

        nameTextView.setText(horoscope.name)
        datesTextView.setText(horoscope.dates)
        iconImageView.setImageResource(horoscope.icon)

        Toast.makeText(this, getString(horoscope.name),Toast.LENGTH_SHORT).show()

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {

        menuInflater.inflate(R.menu.activity_detail_menu, menu)
         favoritemenuitem=menu.findItem(R.id.menu_favorite)
        setFavoriteIcon()
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.menu_favorite->{

                if(isfavorite){
                    session.setFavoriteHoroscope("")
                }else{
                    session.setFavoriteHoroscope(horoscope.id)
                }
                isfavorite = !isfavorite
                setFavoriteIcon()

                return true
            }


            R.id.menu_share->{

                val sendIntent = Intent()
                sendIntent.setAction(Intent.ACTION_SEND)
                sendIntent.putExtra(Intent.EXTRA_TEXT, "This is my text to send.")
                sendIntent.setType("text/plain")

                val shareIntent = Intent.createChooser(sendIntent, null)
                startActivity(shareIntent)

                return true
            }

            else->{
                return super.onOptionsItemSelected(item)
            }

        }


    }
    fun setFavoriteIcon(){
       if(isfavorite){
           favoritemenuitem.setIcon(R.drawable.ic_favorite_check)
       }else{
           favoritemenuitem.setIcon(R.drawable.ic_favorite)
       }
    }

}
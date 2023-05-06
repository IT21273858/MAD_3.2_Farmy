package com.example.farmy


import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView


class Activityprofile : AppCompatActivity() {
    private lateinit var scnbutton: ImageButton
    private lateinit var prdbutton: ImageButton
    private lateinit var prtbutton: Button
    private lateinit var weatherbutton: ImageButton
    private lateinit var postbutton1: Button
    private lateinit var postbutton: ImageButton

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        scnbutton = findViewById<ImageButton>(R.id.heal_btn)
        prdbutton = findViewById<ImageButton>(R.id.prd_btn)
        prtbutton = findViewById<Button>(R.id.view_price)
        weatherbutton = findViewById<ImageButton>(R.id.img_weather)
        postbutton = findViewById<ImageButton>(R.id.img_chat)
        postbutton1 = findViewById<Button>(R.id.view_post)
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigation)
        bottomNavigationView.selectedItemId = R.id.bottom_home
        bottomNavigationView.setOnItemSelectedListener { item: MenuItem ->
            when (item.itemId) {
                R.id.bottom_home -> {
                    startActivity(Intent(applicationContext, Activityprofile::class.java))
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                    finish()
                    return@setOnItemSelectedListener true
                }
                R.id.bottom_scan -> {
                    startActivity(Intent(applicationContext, Scan::class.java))
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                    finish()
                    return@setOnItemSelectedListener true
                }

                R.id.bottom_chat -> {
                    startActivity(Intent(applicationContext, MainActivity::class.java))
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                    finish()
                    return@setOnItemSelectedListener true
                }
                R.id.bottom_product -> {
                    startActivity(Intent(applicationContext, Grocery::class.java)
                    )
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                    finish()
                    return@setOnItemSelectedListener true
                }
            }
            false
        }
        scnbutton.setOnClickListener {
            val intent = Intent(this, Scan::class.java)
            startActivity(intent)
        }
        prdbutton.setOnClickListener {
            val intent = Intent(this, ProductInfoActivity::class.java)
            startActivity(intent)
        }
        prtbutton.setOnClickListener {
            val intent = Intent(this, ProductInfoActivity::class.java)
            startActivity(intent)
        }
        weatherbutton.setOnClickListener {
            val intent = Intent(this, Weather::class.java)
            startActivity(intent)
        }
        postbutton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        postbutton1.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}
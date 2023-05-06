package com.example.farmy

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*
class ProductInfoActivity : AppCompatActivity() {

    private lateinit var listView: ListView
    private lateinit var button: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_info)
        button = findViewById<Button>(R.id.editItem)
        listView = findViewById(R.id.productsListView)
        val adapter = ArrayAdapter<Grocery.Product>(
            this,
            R.layout.activity_product_info,
            R.id.product_name,
            mutableListOf()
        )
        listView.adapter = adapter

        // Get a reference to the Firebase Realtime Database
        val database = FirebaseDatabase.getInstance()
        // Get a reference to the "products" node in the database
        val productsRef = database.getReference("products")
        // Attach a listener to the "products" node to fetch the products and display them in the list view
        productsRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val products = mutableListOf<Grocery.Product>()
                for (productSnapshot in snapshot.children) {
                    val product = productSnapshot.getValue(Grocery.Product::class.java)
                    if (product != null) {
                        products.add(product)
                    }
                }
                adapter.clear()
                adapter.addAll(products)
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle the error
            }
        })
        button.setOnClickListener {
            val intent = Intent(this, Grocery::class.java)
            startActivity(intent)
        }
    }
}


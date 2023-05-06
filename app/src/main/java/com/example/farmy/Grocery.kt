package com.example.farmy
import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.util.Log
import android.view.MenuItem
import android.widget.*
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.database.*

class Grocery : AppCompatActivity() {
    data class Product(val id:Int=0, val name: String? = "", val price: Double = 0.0, val quantity: Int = 0) :
        Parcelable {

        constructor(parcel: Parcel) : this(
            parcel.readInt(),
            parcel.readString(),
            parcel.readDouble(),
            parcel.readInt()
        ) {
        }

        @Exclude
        fun toMap(): Map<String, Any?> {
            return mapOf(
                "id" to id,
                "name" to name,
                "price" to price,
                "quantity" to quantity
            )
        }
        override fun toString(): String {
            return "$id - $name - $price - $quantity"
        }

        override fun writeToParcel(parcel: Parcel, flags: Int) {
            parcel.writeInt(id)
            parcel.writeString(name)
            parcel.writeDouble(price)
            parcel.writeInt(quantity)
        }

        override fun describeContents(): Int {
            return 0
        }

        companion object CREATOR : Parcelable.Creator<Product> {
            override fun createFromParcel(parcel: Parcel): Product {
                return Product(parcel)
            }

            override fun newArray(size: Int): Array<Product?> {
                return arrayOfNulls(size)
            }
        }

    }
    private lateinit var database: DatabaseReference
    private lateinit var productsListView: ListView
    private lateinit var updateButton: Button
    private lateinit var deleteButton: Button
    private lateinit var selectedProduct: Product
    private lateinit var backbutton: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_grocery)

        database = FirebaseDatabase.getInstance().reference
        productsListView = findViewById(R.id.productsListView)
        updateButton = findViewById(R.id.updateButton)
        deleteButton = findViewById(R.id.deleteButton)
        backbutton = findViewById<ImageButton>(R.id.logo_login)
        val nameEditText = findViewById<EditText>(R.id.nameEditText)
        val priceEditText = findViewById<EditText>(R.id.priceEditText)
        val quantityEditText = findViewById<EditText>(R.id.quantityEditText)
        val idEditText = findViewById<EditText>(R.id.idEditText)

        // Populate the ListView with products
        database.child("products").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val products = mutableListOf<Product>()
                for (productSnapshot in snapshot.children) {
                    val product = productSnapshot.getValue(Product::class.java)
                    if (product != null) {
                        products.add(product)
                    }
                }
                val adapter = ArrayAdapter(this@Grocery, android.R.layout.simple_list_item_1, products)
                productsListView.adapter = adapter

                // Handle clicks on products in the ListView
                productsListView.setOnItemClickListener { parent, view, position, id ->
                    selectedProduct = products[position]
                    updateButton.isEnabled = true
                    deleteButton.isEnabled = true
                    nameEditText.setText(selectedProduct.name)
                    priceEditText.setText(selectedProduct.price.toString())
                    quantityEditText.setText(selectedProduct.quantity.toString())
                    idEditText.setText(selectedProduct.id.toString())
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e(TAG, "Database error", error.toException())
                Toast.makeText(this@Grocery, "Database error: ${error.message}", Toast.LENGTH_SHORT).show()
            }



        })


        val addButton = findViewById<Button>(R.id.addButton)
        addButton.setOnClickListener {
            val name = nameEditText.text.toString()
            val price = priceEditText.text.toString().toDouble()
            val quantity = quantityEditText.text.toString().toInt()
            val id = idEditText.text.toString().toInt()
            addProduct(id,name, price, quantity)
        }

        updateButton.setOnClickListener {
            val name = nameEditText.text.toString()
            val price = priceEditText.text.toString().toDoubleOrNull()
            val quantity = quantityEditText.text.toString().toIntOrNull()
            val id =idEditText.text.toString().toIntOrNull()

            if (name.isNotEmpty() && price != null && quantity != null && id != null) {
                val selectedProductId = selectedProduct.id // This assumes you have an "id" property in your Product class
                if (selectedProductId != null) {
                    val updates = mapOf<String, Any>(
                        "/products/$selectedProductId/id" to id, // Add id property to updates map
                        "/products/$selectedProductId/name" to name,
                        "/products/$selectedProductId/price" to price,
                        "/products/$selectedProductId/quantity" to quantity
                    )
                    database.updateChildren(updates)
                    nameEditText.setText("")
                    priceEditText.setText("")
                    quantityEditText.setText("")
                    updateButton.isEnabled = false
                    deleteButton.isEnabled = false
                } else {
                    Toast.makeText(this, "Please select a product to update", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Please fill all the fields", Toast.LENGTH_SHORT).show()
            }
        }


        deleteButton.setOnClickListener {
            // Check if a product is selected
            if (selectedProduct != null) {
                // Delete the selected product
                val productRef = database.child("products").child(selectedProduct.id.toString())
                productRef.removeValue()
                    .addOnSuccessListener {
                        Toast.makeText(this, "Product deleted successfully", Toast.LENGTH_SHORT).show()
                        nameEditText.setText("")
                        priceEditText.setText("")
                        quantityEditText.setText("")
                        updateButton.isEnabled = false
                        deleteButton.isEnabled = false
                    }
                    .addOnFailureListener {
                        Toast.makeText(this, "Failed to delete product", Toast.LENGTH_SHORT).show()
                    }
            } else {
                Toast.makeText(this, "Please select a product to delete", Toast.LENGTH_SHORT).show()
            }
        }
        backbutton.setOnClickListener {
            val intent = Intent(this, Activityprofile::class.java)
            startActivity(intent)
        }

    }
    private fun addProduct(id: Int, name: String, price: Double, quantity: Int) {
        val productId = database.child("products").push().key
        if (productId != null) {
            val product = Product(id,name, price, quantity)
            val productValues = product.toMap()
            val childUpdates = hashMapOf<String, Any>(
                "/products/$productId" to productValues
            )
            database.updateChildren(childUpdates)
        }
    }


}

package com.example.farmy

import android.Manifest
import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.os.Bundle
import android.os.Handler
import android.provider.MediaStore
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.*
import androidx.core.app.ActivityCompat
import kotlinx.android.synthetic.main.activity_scan.*
import com.example.farmy.databinding.ActivityMainBinding
import com.example.farmy.databinding.ActivityScanBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*

class Scan : AppCompatActivity() {

    private lateinit var mBitmap: Bitmap
    private val mCameraRequest = 1
    private val mGalleryRequest = 2
    private lateinit var mCategorization: Categorization

    private val mInputSize = 224
    private val mModelPath = "plant_disease_model.tfLite"
    private val mLabelPath = "plant_labels.txt"

    private val mSamplePath = "automn.jpg"

    private lateinit var binding: ActivityScanBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mCategorization = Categorization(assets,mModelPath,mLabelPath,mInputSize)

        resources.assets.open(mSamplePath).use {
            mBitmap = BitmapFactory.decodeStream(it)
            mBitmap = Bitmap.createScaledBitmap(mBitmap,mInputSize,mInputSize,true)
            imageView.setImageBitmap(mBitmap)
        }

        binding.cameraBtn.setOnClickListener{
            if(ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA),11)
            }
            else{
                try {
                    val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                    startActivityForResult(intent,mCameraRequest)
                }catch (e: Exception){
                    Toast.makeText(this,e.message.toString(), Toast.LENGTH_SHORT).show()
                }
            }
        }

        binding.galleryBtn.setOnClickListener{
            if(ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),21)
            }
            else{
                try{
                    val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                    intent.type = "image/*"
                    startActivityForResult(intent,mGalleryRequest)
                }catch (e: Exception){
                    Toast.makeText(this,e.message.toString(), Toast.LENGTH_SHORT).show()
                }
            }
        }



        binding.resultBtn.setOnClickListener{
            val progressDialog = ProgressDialog(this@Scan)
            progressDialog.setTitle("Please Wait")
            progressDialog.setMessage("Wait there I do Something....")
            progressDialog.show()
            val handler = Handler()
            handler.postDelayed(Runnable { progressDialog.dismiss()
                val results = mCategorization.recognizeImage(mBitmap).firstOrNull()
                resultTextView.text = "Disease: "+results?.title +"\nConfidence: "+results?.confidence
            },2000)
        }

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
                R.id.bottom_chat-> {
                    startActivity(Intent(applicationContext, MainActivity::class.java))
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                    finish()
                    return@setOnItemSelectedListener true
                }

                R.id.bottom_product -> {
                    startActivity(Intent(applicationContext, ProductInfoActivity::class.java)
                    )
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                    finish()
                    return@setOnItemSelectedListener true
                }
            }
            false
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == mCameraRequest && resultCode == Activity.RESULT_OK && data != null){
            mBitmap = data.extras!!.get("data") as Bitmap
            try {
//            mBitmap = scaleImage(mBitmap)
            }catch (e: Exception){
                Toast.makeText(this,e.message.toString(), Toast.LENGTH_LONG).show()
            }
            binding.imageView.setImageBitmap(mBitmap)
            resultTextView.text = "Farmy Set Your Photo Now"
        }
        else if(requestCode == mGalleryRequest && resultCode == Activity.RESULT_OK && data != null){
            val uri = data.data
            try{
                mBitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, uri)
//            mBitmap = scaleImage(mBitmap)
            }catch (e: Exception){
                Toast.makeText(this,e.message.toString(), Toast.LENGTH_SHORT).show()
            }
            binding.imageView.setImageBitmap(mBitmap)
            resultTextView.text = "Farmy Set Your Photo Now"
        }
    }


    private fun scaleImage(mBitmap: Bitmap): Bitmap {
        val originalWidth = mBitmap.width
        val originalHeight = mBitmap.height
        val scaleWidth = mInputSize.toFloat()
        val scaleHeight = mInputSize.toFloat()
        val matrix = Matrix()
        matrix.postScale(scaleWidth,scaleHeight)
        return Bitmap.createBitmap(mBitmap,0,0,originalWidth,originalHeight,matrix,true)
    }
}

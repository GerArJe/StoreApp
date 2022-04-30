package com.example.storeapp.view

import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.storeapp.R
import com.example.storeapp.databinding.ActivityProductAddBinding
import com.example.storeapp.model.entity.Product
import com.example.storeapp.viewModel.ProductAddActivityViewModel
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class ProductAddActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProductAddBinding
    private lateinit var viewModel: ProductAddActivityViewModel
    private lateinit var resultGallery: ActivityResultLauncher<Intent>
    private lateinit var resultCamera: ActivityResultLauncher<Intent>
    private var photoUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val product: Product? = intent.getSerializableExtra("product") as Product?

        binding = DataBindingUtil.setContentView(this, R.layout.activity_product_add)


        viewModel = ViewModelProvider(this)[ProductAddActivityViewModel::class.java]

        binding.viewModel = viewModel

        binding.ibCameraProductAdd.setOnClickListener {
            val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            takePictureIntent.resolveActivity(packageManager)?.let {
                var photoFile: File? = null
                try {
                    photoFile = createImage()
                } catch (e: IOException) {

                }
                photoFile?.let {
                    photoUri = FileProvider.getUriForFile(
                        applicationContext,
                        "com.example.storeapp.fileprovider",
                        photoFile
                    )
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri)
                    resultCamera.launch(takePictureIntent)
                }
            } ?: run {
                println("error takePictureIntent")
            }
        }

        resultCamera = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == RESULT_OK) {
                Glide.with(applicationContext).load(photoUri).into(binding.ivProductForm)
            }
        }

        binding.ibGalleryProductAdd.setOnClickListener {
            val galleryIntent =
                Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            galleryIntent.resolveActivity(packageManager)?.let {
                resultGallery.launch(galleryIntent)
            } ?: run {
                println("error galleryIntent")
            }
        }

        resultGallery =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                if (it.resultCode == RESULT_OK) run {
                    photoUri = it.data!!.data!!
                    Glide.with(applicationContext).load(photoUri).into(binding.ivProductForm)
                }
            }

        product?.let {
            binding.tvTitleProductForm.text = "${getString(R.string.editar)} ${it.name}"
            viewModel.product = it
            binding.btAddProductForm.text = getString(R.string.editar_producto)
            binding.btAddProductForm.setOnClickListener {
                viewModel.edit().observe(this) { state ->
                    if (state) {
                        finish()
                    } else {
                        Toast.makeText(
                            applicationContext,
                            "Error al actualizar producto...",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        } ?: run {
            binding.btAddProductForm.setOnClickListener {
                binding.btAddProductForm.isEnabled = false
                viewModel.add(photoUri).observe(this) { id ->
                    if (id != "") {
                        binding.btAddProductForm.isEnabled = true
                        finish()
                    } else {
                        binding.btAddProductForm.isEnabled = true
                        Toast.makeText(
                            applicationContext,
                            "Error al agregar producto...",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }


        binding.btReturnProductForm.setOnClickListener {
            finish()
        }


    }

    private fun createImage(): File? {
        val timeStamp = SimpleDateFormat("yyyMMdd_HHmmss", Locale.US).format(Date())
        val storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(timeStamp, ",jpg", storageDir)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.mi_logout -> {
                logout()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun logout() {
        val preferences: SharedPreferences = getSharedPreferences("store_app.pref", MODE_PRIVATE)
        val editor = preferences.edit()
        editor.clear()
        editor.apply()

        val intent = Intent(applicationContext, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }
}
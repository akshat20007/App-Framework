package com.example.trello.activities


import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.example.trello.R
import com.example.trello.activities.firebase.FirestoreClass
import com.example.trello.activities.models.Tourni
import com.example.trello.activities.utils.Constants
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.activity_create_board.*
import java.io.IOException

class CreateBoardActivity : BaseActivity() {


    private var mSelectedImageFileUri: Uri? = null

    private lateinit var mUserName: String

    private var mBoardImageURL: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_board)

        setupActionBar()

        if(intent.hasExtra(Constants.NAME)){
            mUserName = intent.getStringExtra(Constants.NAME).toString()
        }

        iv_board_image.setOnClickListener { view ->

            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED
            ) {
                Constants.showImageChooser(this@CreateBoardActivity)
            } else {

                ActivityCompat.requestPermissions(
                        this,
                        arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                        Constants.READ_STORAGE_PERMISSION_CODE
                )
            }

            btn_create.setOnClickListener{
                if(mSelectedImageFileUri != null){
                    uploadBoardImage()
                }else{
                    showProgressDialog(resources.getString(R.string.please_wait))
                    createBoard()
                }
            }
        }
    }

    override fun onRequestPermissionsResult(
            requestCode: Int,
            permissions: Array<out String>,
            grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == Constants.READ_STORAGE_PERMISSION_CODE) {
            //If permission is granted
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Constants.showImageChooser(this@CreateBoardActivity)
            } else {

                Toast.makeText(
                        this,
                        "Oops, you just denied the permission for storage. You can also allow it from settings.",
                        Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK
                && requestCode == Constants.PICK_IMAGE_REQUEST_CODE
                && data!!.data != null
        ) {
            mSelectedImageFileUri = data.data

            try {

                Glide
                        .with(this@CreateBoardActivity)
                        .load(Uri.parse(mSelectedImageFileUri.toString())) // URI of the image
                        .centerCrop() // Scale type of the image.
                        .placeholder(R.drawable.ic_user_place_holder) // A default place holder
                        .into(iv_board_image) // the view in which the image will be loaded.
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    private fun createBoard(){
        val assignedUserArrayList: ArrayList<String> = ArrayList()
        assignedUserArrayList.add(getCurrentUserId())
        var board = Tourni(
            et_board_name.text.toString(),
            mBoardImageURL,
            mUserName,
//            assignedUserArrayList
        )

        FirestoreClass().createBoard(this, board)

    }

    private fun uploadBoardImage(){
        showProgressDialog(resources.getString(R.string.please_wait))

        val sRef: StorageReference = FirebaseStorage.getInstance().reference.child(
            "BOARD_IMAGE" + System.currentTimeMillis() + "."
                    + Constants.getFileExtension(this, mSelectedImageFileUri)
        )

        //adding the file to reference
        sRef.putFile(mSelectedImageFileUri!!)
            .addOnSuccessListener { taskSnapshot ->
                // The image upload is success
                Log.e(
                    "Firebase Image URL",
                    taskSnapshot.metadata!!.reference!!.downloadUrl.toString()
                )

                // Get the downloadable url from the task snapshot
                taskSnapshot.metadata!!.reference!!.downloadUrl
                    .addOnSuccessListener { uri ->
                        Log.i("Downloadable Image URL", uri.toString())

                        // assign the image url to the variable.
                        mBoardImageURL = uri.toString()

                        // Call a function to update user details in the database.
                        createBoard()
                    }
            }
            .addOnFailureListener { exception ->
                Toast.makeText(
                    this,
                    exception.message,
                    Toast.LENGTH_LONG
                ).show()

                hideProgressDialog()
            }
    }

    fun boardCreatedSuccessfully(){
        hideProgressDialog()
        finish()
    }

    private fun setupActionBar() {

        setSupportActionBar(toolbar_create_board_activity)

        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_white_color_back_24dp)
        }

        toolbar_create_board_activity.setNavigationOnClickListener { onBackPressed() }
    }
}
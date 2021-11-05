package com.example.antiwaste3_0

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.webkit.MimeTypeMap
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.activity_upload_photo.*
import kotlinx.android.synthetic.main.fragment_notifications.*
import java.io.IOException
import java.lang.reflect.TypeVariable


class UploadPhotoActivity : AppCompatActivity() {

    private var mSelectedImageFileUri: Uri? = null      //directory of images set to null; later at ln 70+ assign value

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload_photo)

        btn_select_image.setOnClickListener{
            if(ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE
                )== PackageManager.PERMISSION_GRANTED){
                //Select Image
                val galleryIntent = Intent(
                    Intent.ACTION_PICK,
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                )
                startActivityForResult(galleryIntent, 222)
            }else{
                // Request Permission
                ActivityCompat.requestPermissions(this,
                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                    121)
            }
        }

        btn_upload_image.setOnClickListener{
            if(mSelectedImageFileUri != null){      // check if uri not equals to null
                val imageExtension = MimeTypeMap    // get image extension
                    .getSingleton().getExtensionFromMimeType(contentResolver.getType(mSelectedImageFileUri!!))

                val StringRef : String = "Image" + System.currentTimeMillis() + "." + imageExtension

                val sRef : StorageReference = FirebaseStorage.getInstance().reference.child(
                    StringRef
                )
                sRef.putFile(mSelectedImageFileUri!!)
                    .addOnSuccessListener { taskSnapshot->
                        taskSnapshot.metadata!!.reference!!.downloadUrl
                            .addOnSuccessListener {url->
                                tv_image_upload_success.text = "Image uploaded successfully :: $url"

                                //update points here
                                updatePoints(StringRef)

                            }.addOnFailureListener{exception->
                                Toast.makeText(
                                    this,
                                    exception.message,
                                    Toast.LENGTH_LONG
                                ).show()
                                //Log.e(java.class.simpleName, exception.message, exception)
                            }
                    }


            }else{
                Toast.makeText(
                    this,
                    "Please select the image to upload",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }



    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == 121){
            val galleryIntent = Intent(
                Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            )

            startActivityForResult(galleryIntent, 222)

        }else{
            Toast.makeText(
                this,
                "Permission denied for storage",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK){
            if(data != null){
                try{
                    mSelectedImageFileUri = data.data!!

                    image_view.setImageURI(mSelectedImageFileUri)
                }catch(e: IOException){     // catch error if user uploads smth wrong cos input output error
                    e.printStackTrace()
                    Toast.makeText(
                        this@UploadPhotoActivity,
                        "Image Selection Failed",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
}

fun updatePoints(url: String) {
    val database = FirebaseFirestore.getInstance()
    val user = Firebase.auth.currentUser
    lateinit var points : Number
    if (user != null) {
        database.collection("users").document(user.email.toString()).get()
            .addOnSuccessListener() { task ->
                points = task.data?.getValue("rewardPts") as Number         //getPoints
                database.collection("users")                        //updatePoints+1
                    .document(user.email.toString())
                    .update("rewardPts", points.toInt()+1,
                        "photoId", FieldValue.arrayUnion(url)
                        )
            }

    }
    else{
        points = 500
    }

}

/*
fun updatePoints() {
    val database = FirebaseFirestore.getInstance()
    val user = Firebase.auth.currentUser
    var points2 = getPoints().toInt()

    if (user != null) {
        database.collection("users").document(user.email.toString()).update("rewardPts", points2)
        }
}*/



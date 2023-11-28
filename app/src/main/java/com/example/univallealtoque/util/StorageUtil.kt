package com.example.univallealtoque.util

import android.content.Context
import android.net.Uri
import android.util.Log
import android.widget.Toast
import com.example.univallealtoque.sign_in_express.LoginViewModelExpress
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import java.util.UUID


class StorageUtil{


    companion object {

        fun uploadToStorage(viewModelExpress: LoginViewModelExpress, uri: Uri, context: Context, type: String) {
            var httpsReference = "";

            val storage = Firebase.storage


            // Create a storage reference from our app
            var storageRef = storage.reference

            val unique_image_name = UUID.randomUUID()
            var spaceRef: StorageReference

            if (type == "image"){
                spaceRef = storageRef.child("images/$unique_image_name.jpg")
            }else{
                spaceRef = storageRef.child("videos/$unique_image_name.mp4")
            }

            val byteArray: ByteArray? = context.contentResolver
                .openInputStream(uri)
                ?.use { it.readBytes() }

            byteArray?.let{

                var uploadTask = spaceRef.putBytes(byteArray)
                uploadTask.addOnFailureListener {
                    Toast.makeText(
                        context,
                        "upload failed",
                        Toast.LENGTH_SHORT
                    ).show()
                    // Handle unsuccessful uploads
                }.addOnSuccessListener { taskSnapshot ->
                    // taskSnapshot.metadata contains file metadata such as size, content-type, etc.
                    // ...
                    println("unique_image_name: ${unique_image_name}")
                    val storageN = Firebase.storage
                    val listRef = storageN.reference.child("images")

                    val dispatchGroup = DispatchGroup()

                    var tmpUris: MutableList<Uri> = mutableListOf()
                    listRef.listAll()
                        .addOnSuccessListener{results ->
                            results.items.forEach { res ->
                                dispatchGroup.enter()
                                res.downloadUrl
                                    .addOnSuccessListener { uri ->
                                        tmpUris.add(uri)
                                        println("uri: "+ uri)
                                        if (unique_image_name.toString() in uri.toString()){
                                            httpsReference = uri.toString()
                                            println("si encontrado ${httpsReference}")
                                            viewModelExpress.updateBasicData(newProfilePhoto =  httpsReference)
                                        }else {
                                            println("no encontrado")
                                        }

                                        dispatchGroup.leave()
                                    }
                                    .addOnFailureListener {

                                    }

                                dispatchGroup.notify {


                                }
                            }
                        }
                        .addOnFailureListener {
                            // Uh-oh, an error occurred!
                            Log.d("DEBUG" , "Error occured")
                        }
                    Toast.makeText(
                        context,
                        "upload successed",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
}
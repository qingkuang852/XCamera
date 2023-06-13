package com.wenm.xcamera

import android.Manifest
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat

class XCameraActivity : AppCompatActivity() {

    private lateinit var xCameraManager: XCameraManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val view = XCameraView(this)
        setContentView(view)
        xCameraManager = XCameraManager(view)

        val cameraPermission = ActivityCompat.checkSelfPermission(
            this,
            Manifest.permission.CAMERA
        ) == android.content.pm.PackageManager.PERMISSION_GRANTED
        if(cameraPermission) {
            xCameraManager.openCamera(this)
        }else{
            Toast.makeText(this, "Camera permission not granted", Toast.LENGTH_SHORT).show()
        }
    }

//    fun cropBitmap(){
//        var hRatio = binding.centerView.height.toFloat() / (binding.ivPhoto.height.toFloat())
//        var wRatio = binding.centerView.width.toFloat() / (binding.ivPhoto.width.toFloat())
//
//        val file = getFileFromMediaUri(this, imageUri!!)
//        val photoBmp = getBitmapFormUri(this, Uri.fromFile(file))
//
//        val degree = getBitmapDegree(file!!.absolutePath)
//        val newbitmap = rotateBitmapByDegree(photoBmp!!, degree)
//
//        val w = (newbitmap!!.width * wRatio).toInt()
//        val h = (newbitmap!!.height * hRatio).toInt()
//        val l = (newbitmap!!.width - w)/2
//        val t = (newbitmap!!.height - h)/2
//
//        val mCropBitmap = Bitmap.createBitmap(
//            newbitmap!!,
//            l, t, w, h
//        )
//
//        val imagePath = BitmapHelper.saveImageToGallery(mCropBitmap!!, this)
//    }
}
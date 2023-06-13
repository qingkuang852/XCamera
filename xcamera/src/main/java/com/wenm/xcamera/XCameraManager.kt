package com.wenm.xcamera

import android.app.Instrumentation
import android.util.Log
import android.util.Size
import androidx.activity.result.ActivityResult
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import java.io.File

class XCameraManager(val cameraView: XCameraView) {
    private lateinit var imageCapture: ImageCapture

    var filePath: String? = null
    var takePhotoCallback: ImageCapture.OnImageSavedCallback
    var result: ImageCapture.OutputFileResults? = null
    var size: Size? = null

    init {
        filePath = cameraView.context.cacheDir.path
        cameraView.clickTakePhoto { takePhoto() }
        takePhotoCallback = object : ImageCapture.OnImageSavedCallback {
            override fun onError(error: ImageCaptureException) {
            }

            override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                cameraView.showPhoto(output.savedUri!!)
                result = output
            }
        }
    }

    fun openCamera(lifecycleOwner: LifecycleOwner, size: Size) {
        this.size = size
        openCamera(lifecycleOwner)
    }

    fun openCamera(lifecycleOwner: LifecycleOwner) {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(cameraView.context)
        cameraProviderFuture.addListener({
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()
            val preview = Preview.Builder()
                .build()
                .also {
                    it.setSurfaceProvider(cameraView.texture.surfaceProvider)
                }

            cameraView.postDelayed({
                imageCapture = ImageCapture.Builder().setTargetResolution(
                    size ?: Size(
                        cameraView.texture.measuredWidth,
                        cameraView.texture.measuredHeight
                    )
                ).build()

                val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
                try {
                    cameraProvider.unbindAll()
                    cameraProvider.bindToLifecycle(
                        lifecycleOwner, cameraSelector, preview, imageCapture
                    )
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }, 300)

        }, ContextCompat.getMainExecutor(cameraView.context))
    }

    fun takePhoto() {
        val name = System.currentTimeMillis().toString() + ".jpg"
        takePhoto(name)
    }

    fun takePhoto(fileName: String) {
        val file = File(filePath, fileName)
        val outputFileOptions = ImageCapture.OutputFileOptions.Builder(file).build()
        imageCapture.takePicture(
            outputFileOptions,
            ContextCompat.getMainExecutor(cameraView.context),
            takePhotoCallback
        )
    }

    companion object {
        fun parseResult(result: ActivityResult): String {
            return result.data?.let {
                it.getBundleExtra("result")?.getString("imageUri")
            } ?: ""
        }
    }
}
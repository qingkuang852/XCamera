package com.wenm.xcamera

import android.app.Activity
import android.app.Instrumentation
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.view.PreviewView
import androidx.constraintlayout.widget.ConstraintLayout

class XCameraView(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : ConstraintLayout(
    context,
    attrs, defStyleAttr
) {
    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    val texture: PreviewView
    private val imageButton: ImageButton
    private val cancelImageButton: ImageButton
    private val confirmImageButton: ImageButton
    private val imageView: ImageView
    private var photo: Uri? = null

    private var customize = false
       set(value) {
            field = value
            if(field){
                imageButton.visibility = View.GONE
            }
        }

    init {
        val inflate = inflate(context, R.layout.view_camera_preview, this)
        texture = inflate.findViewById(R.id.preview) as PreviewView
        imageButton = inflate.findViewById(R.id.imageButton)
        cancelImageButton = inflate.findViewById(R.id.cancelImageButton)
        confirmImageButton = inflate.findViewById(R.id.confirmImageButton)
        imageView = inflate.findViewById(R.id.imageView)

        cancelImageButton.setOnClickListener {
            hidePhoto()
        }

        confirmImageButton.setOnClickListener {
            photo?.let {
                val intent = Intent()
                val bundle = Bundle()
                bundle.putString("imageUri", photo.toString())
                intent.putExtra("result", bundle)
                if (context is Activity) {
                    context.setResult(AppCompatActivity.RESULT_OK, intent)
                    context.finish()
                }
            }
        }

        val ta = context.obtainStyledAttributes(attrs, R.styleable.XCameraView)
        customize = ta.getBoolean(R.styleable.XCameraView_customize, false)
        ta.recycle()
    }

    fun clickTakePhoto(onClickListener: OnClickListener) {
        imageButton.setOnClickListener(onClickListener)
    }

    fun showPhoto(photo: Uri) {
        if (customize) return
        imageView.visibility = View.VISIBLE
        imageView.setImageURI(photo)
        cancelImageButton.visibility = View.VISIBLE
        confirmImageButton.visibility = View.VISIBLE
        imageButton.visibility = View.GONE
        this.photo = photo
    }

    private fun hidePhoto() {
        imageButton.visibility = View.VISIBLE
        cancelImageButton.visibility = View.GONE
        confirmImageButton.visibility = View.GONE
        imageView.visibility = View.GONE
        imageView.setImageURI(null)
    }
}
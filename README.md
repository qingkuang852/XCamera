# XCamera
[![](https://jitpack.io/v/qingkuang852/XCamera.svg)](https://jitpack.io/#qingkuang852/XCamera)

# How to use?

## Easiest Way
  1. AndroidManifest.xml
  
    add 
    <activity android:name="com.wenm.xcamera.XCameraActivity"/>
  
  2. Apply permission Camera (can refer to my https://github.com/qingkuang852/Permissioner)
  
  3. Start XCameraActivity
  
    launcher.launch(Intent(this@MainActivity, XCameraActivity::class.java))
  
  4. Get result uri
  
    val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult(), {
        val uri = XCameraManager.parseResult(it)
        //imageview.setImageURI(Uri.parse(uri))
    })
  
  That's it!

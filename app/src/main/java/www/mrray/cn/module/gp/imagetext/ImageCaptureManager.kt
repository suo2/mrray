package www.mrray.cn.module.gp.imagetext

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

/**
 * @author suo
 * @date 2018/10/22
 * @desc: image管理器
 */
class ImageCaptureManager(private val mContext: Context) {

    var currentPhotoPath: String? = null
        private set

    @Throws(IOException::class)
    private fun createImageFile(): File {

        // Create an image file name
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val imageFileName = "JPEG_" + timeStamp + "_"
        val storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
        if (!storageDir.exists()) {
            if (!storageDir.mkdir()) {
                throw IOException()
            }
        }
        val image = File(storageDir, "$imageFileName.jpg")
        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.absolutePath
        return image
    }


    @Throws(IOException::class)
    fun dispatchTakePictureIntent(): Intent {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(mContext.packageManager) != null) {
            // Create the File where the photo should go
            val photoFile = createImageFile()
            // Continue only if the File was successfully created
            if (photoFile != null) {
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                        Uri.fromFile(photoFile))
            }
        }
        return takePictureIntent
    }


    fun galleryAddPic() {
        val mediaScanIntent = Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE)
        val f = File(currentPhotoPath!!)
        val contentUri = Uri.fromFile(f)
        mediaScanIntent.data = contentUri
        mContext.sendBroadcast(mediaScanIntent)
    }

    fun onSaveInstanceState(savedInstanceState: Bundle?) {
        if (savedInstanceState != null && currentPhotoPath != null) {
            savedInstanceState.putString(CAPTURED_PHOTO_PATH_KEY, currentPhotoPath)
        }
    }

    fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        if (savedInstanceState != null && savedInstanceState.containsKey(CAPTURED_PHOTO_PATH_KEY)) {
            currentPhotoPath = savedInstanceState.getString(CAPTURED_PHOTO_PATH_KEY)
        }
    }

    companion object {

        private val CAPTURED_PHOTO_PATH_KEY = "mCurrentPhotoPath"
        val REQUEST_TAKE_PHOTO = 1
    }

}

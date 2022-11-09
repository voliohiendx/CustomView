package com.example.custtomview

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import android.util.Log
import android.webkit.MimeTypeMap
import android.widget.Button
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.core.net.toFile
import androidx.core.net.toUri
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.Main
import java.io.File
import java.io.InputStream
import java.io.OutputStream
import java.security.MessageDigest


class TestDoc : AppCompatActivity() {
    var uri: Uri? = null

    val fileResultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            //viewModel.uploadFile(result, false)
            if (result.resultCode == RESULT_OK) {
                result.data?.data?.let {
                    Log.d("Na00007", ":$it ")
//                    copyFileToIn(it)
                    //  uri= it

                }
            }
        }

    var resultManagerEx =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->

        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_doc)
        //  pickFile()

        findViewById<Button>(R.id.btnCheck).setOnClickListener {
            CopyFile.cancelCopy()
            //  openFile()
        }

        findViewById<Button>(R.id.btnCheck2).setOnClickListener {
            CopyFile.apply {
                init(application)
                val listFile = mutableListOf<File>()

                listFile.add(File("sdcard/Download/Test/sample_3840x2160.mov"))
                listFile.add(File("sdcard/Download/Test/mmmm.zip"))
                listFile.add(File("sdcard/Download/Test/Sample-Video-File-For-Testing.mp4"))

                this.addCallback(object : CopyFileCallback {
                    override fun startCopyFile(file: File) {
                        Log.d("Na00000x77", "startCopyFile: ")
                    }

                    override fun cancelCopyFile(
                        listFileSuccess: List<File>, listFileNonSuccess: List<File>
                    ) {
                        Log.d("Na00000x77", "cancelCopyFile: ")
                    }

                    override fun updateProcessFileCopyCurrent(bytesCopied: Long, bytesFile: Long) {
                        Log.d("Na00000x77", "updateProcessFileCopyCurrent: ")
                        findViewById<TextView>(R.id.tvTest).text = bytesCopied.toString()
                    }


                })
                val folder = File(this@TestDoc?.filesDir, "Hienoc")
                if (!folder.exists()) {
                    folder.mkdirs()
                }

                CoroutineScope(Dispatchers.Default).launch {
                    copyListFile(listFile, folder)
                }
            }
        }
        CoroutineScope(Main).launch {
            getDocument()
        }
        //  Log.d("Na00007Ng", "Before: ${File("sdcard/DCIM/Camera/JUL00106.jpg").md5()}")
        //   copyFileToIn("sdcard/DCIM/Camera/JUL00106.jpg")

        //  requestManagerPermission()
    }

    fun requestManagerPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val intent = Intent(
                Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION,
                Uri.parse("package:" + BuildConfig.APPLICATION_ID)
            )
            try {
                resultManagerEx.launch(intent)
            } catch (e: Exception) {
            }
        } else {
            val intent = Intent(
                Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                Uri.parse("package:" + BuildConfig.APPLICATION_ID)
            )
            try {
                startActivity(intent)
            } catch (e: Exception) {
            }
        }
    }

    fun openFile() {
        val file = File("/data/data/com.example.custtomview/file/sample.pdf")
        if (file.exists()) {
            Log.d("Na00007", "openFile")
            Log.d("Na00007", "openFile ${file.toUri()}")
        }

        try {
            val newIntent = Intent(Intent.ACTION_VIEW)
            newIntent.data = FileProvider.getUriForFile(
                getApplicationContext(), getPackageName() + ".provider", file
            );
            startActivity(newIntent)
        } catch (e: java.lang.Exception) {
            //  Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show()
            Log.d("Na00007", "openFile: $e")
        }
    }

    fun pickFile() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("application/pdf");

        //  intent.type = "*/*"
        fileResultLauncher.launch(intent)
        // startActivityForResult(intent, YOUR_RESULT_CODE)
    }

    fun copyFileToIn(string: String) {

        try {
            val uri = Uri.fromFile(File(string))
            val input = uri.toFile()
            val output = File(this.getFilesDir(), "/mm.jpg")

            input.copyTo(output)

            Log.d("Na00007Ng", "After: ${output.path}")
            Log.d("Na00007Ng", "After: ${output.md5()}")

        } catch (e: java.lang.Exception) {
            Log.d("Na00007", "openFile: $e")
        }
    }

    fun File.copyTo(file: File) {
        inputStream().use { input ->
            file.outputStream().use { output ->
                input.copyTo(output)
            }
        }
    }

    fun File.md5(): String {
        val md = MessageDigest.getInstance("MD5")
        return this.inputStream().use { fis ->
            val buffer = ByteArray(8192)
            generateSequence {
                when (val bytesRead = fis.read(buffer)) {
                    -1 -> null
                    else -> bytesRead
                }
            }.forEach { bytesRead -> md.update(buffer, 0, bytesRead) }
            md.digest().joinToString("") { "%02x".format(it) }
        }
    }

    fun InputStream.copyFileNew(out: OutputStream, bufferSize: Int = DEFAULT_BUFFER_SIZE): Long {
        var bytesCopied: Long = 0
        val buffer = ByteArray(bufferSize)
        var bytes = read(buffer)
        while (bytes >= 0) {
            out.write(buffer, 0, bytes)
            bytesCopied += bytes
            bytes = read(buffer)
        }
        return bytesCopied
    }

    private suspend fun getDocument(
    ) = withContext(Dispatchers.IO) {
        val folders = HashMap<Long, String>()
        val dateMap = HashMap<String, String>()

        val promise = async {
            launch {

                val projection = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) arrayOf(
                    MediaStore.Files.FileColumns.MEDIA_TYPE,
                    MediaStore.Files.FileColumns.RELATIVE_PATH,
                    MediaStore.Files.FileColumns.DISPLAY_NAME,
                    MediaStore.Files.FileColumns._ID,
                    MediaStore.Files.FileColumns.BUCKET_DISPLAY_NAME,
                    MediaStore.Files.FileColumns.BUCKET_ID,
                )
                else arrayOf(
                    MediaStore.Files.FileColumns.MEDIA_TYPE,
                    MediaStore.Files.FileColumns.DATA,
                    MediaStore.Files.FileColumns.TITLE,
                    MediaStore.Files.FileColumns._ID,
                    MediaStore.Files.FileColumns.BUCKET_DISPLAY_NAME,
                    MediaStore.Files.FileColumns.BUCKET_ID,
                )

                val mimeTypes = mutableListOf<String>()
                val extenstions = mutableListOf(
                    "pdf", "doc", "docx", "ppt", "pptx", "xls", "xlsx"
                )
                extenstions.forEach { mimeType ->
                    MimeTypeMap.getSingleton().getMimeTypeFromExtension(mimeType)?.let {
                        mimeTypes.add("'$it'")
                    }
                }

                val selectionMimeType =
                    MediaStore.Files.FileColumns.MIME_TYPE + " IN (${mimeTypes.joinToString()})"

                val uri = MediaStore.Files.getContentUri("external")

                application.contentResolver.query(
                    uri, projection, selectionMimeType, null, null
                )?.use { cursor ->
                    while (cursor.moveToNext()) {
                        try {
                            val idMedia: Long =
                                cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns._ID))
                            val nameMedia: String =
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                                    val name = cursor.getString(
                                        cursor.getColumnIndexOrThrow(
                                            MediaStore.Files.FileColumns.DISPLAY_NAME
                                        )
                                    )
                                    val index = name.lastIndexOf(".")
                                    if (index != -1) {
                                        val nameCut = name.substring(0, index)
                                        nameCut
                                    } else {
                                        name
                                    }
                                } else cursor.getString(
                                    cursor.getColumnIndexOrThrow(
                                        MediaStore.Files.FileColumns.TITLE
                                    )
                                )

                            val path =
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) "sdcard/" + cursor.getString(
                                    cursor.getColumnIndexOrThrow(
                                        MediaStore.Files.FileColumns.RELATIVE_PATH
                                    )
                                ) + cursor.getString(
                                    cursor.getColumnIndexOrThrow(
                                        MediaStore.Files.FileColumns.DISPLAY_NAME
                                    )
                                )
                                else cursor.getString(
                                    cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.DATA)
                                )

                            val pathFolder =
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) "sdcard/" + cursor.getString(
                                    cursor.getColumnIndexOrThrow(
                                        MediaStore.Files.FileColumns.RELATIVE_PATH
                                    )
                                )
                                else cursor.getString(
                                    cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.DATA)
                                )

                            val contentUri = Uri.withAppendedPath(uri, "" + idMedia)
                            val folderIdIndex: Int =
                                cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.BUCKET_ID)
                            val folderNameIndex: Int = cursor.getColumnIndexOrThrow(
                                MediaStore.Files.FileColumns.BUCKET_DISPLAY_NAME
                            )
                            val folderId: Long = cursor.getLong(folderIdIndex)
                            val timeModified = File(path).lastModified()

                            Log.d("Na000007", "getDocument: $path")
                            Log.d("Na000007", "getDocument: $contentUri")


                        } catch (ex: Exception) {
                            //Log.d("HIEN_DATA", documentLocal.toString())
                        }
                    }
                    cursor.close()
                    folders.clear()
                    dateMap.clear()
                }
            }
        }
        promise.await()
    }

}
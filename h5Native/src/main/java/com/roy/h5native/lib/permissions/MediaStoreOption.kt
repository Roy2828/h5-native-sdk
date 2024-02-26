package com.roy.h5native.lib.permissions

import android.content.ContentResolver
import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import java.io.*
import java.nio.channels.FileChannel

/**
 *    desc   :  公有目录用这个类操作
 *    e-mail : 1391324949@qq.com
 *    date   : 2023/11/29 18:24
 *    author : Roy
 *    version: 1.0
 */
object MediaStoreOption {



    fun getInputStream(context: Context,uri:Uri):InputStream?{

        val resolver: ContentResolver = context.getContentResolver()
        try {
            return resolver.openInputStream(uri)
        } catch (e: Exception) {
            return null;
        }
    }

    /**
     *  保存文件
     * @param context Context
     * @param fileName String需 要待扩展  如： a.txt
     * @param inputStream InputStream //需要保存的流
     */
    fun saveFile(context: Context, fileName: String, inputStream: InputStream){
        // 操作 external.db 数据库
        // 获取 Uri 路径
        var uri: Uri = MediaStore.Files.getContentUri("external")

        // 将要新建的文件的文件索引插入到 external.db 数据库中
        // 需要插入到 external.db 数据库 files 表中, 这里就需要设置一些描述信息
        var contentValues: ContentValues = ContentValues()

        // 设置插入 external.db 数据库中的 files 数据表的各个字段的值

        // 设置存储路径 , files 数据表中的对应 relative_path 字段在 MediaStore 中以常量形式定义
        contentValues.put(MediaStore.Downloads.RELATIVE_PATH, "${Environment.DIRECTORY_DOWNLOADS}/Zhumang")
        // 设置文件名称
        contentValues.put(MediaStore.Downloads.DISPLAY_NAME, fileName) //"hello.txt"
        // 设置文件标题, 一般是删除后缀, 可以不设置
       // contentValues.put(MediaStore.Downloads.TITLE, "hello")

        // uri 表示操作哪个数据库 , contentValues 表示要插入的数据内容
        var insert: Uri? = context.contentResolver.insert(uri, contentValues)
        var os: OutputStream?= null
        var bos:BufferedOutputStream ?= null
        try {// 向 Download/hello/hello.txt 文件中插入数据
             os  = context.contentResolver.openOutputStream(insert!!)
             bos = BufferedOutputStream(os)

            var read: Int = 0
            val bytes = ByteArray(1024)
            while (inputStream.read(bytes).also { read = it } != -1) {
                bos.write(bytes, 0, read)
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }finally {
            os?.close()
            bos?.close()
            inputStream?.close()
        }
    }



    fun copyFiles(sourceFolders: List<File>, destinationFolders: List<File>): Boolean {
        require(sourceFolders.size == destinationFolders.size) { "源文件夹和目标文件夹必须具有相同的大小。" }
        var allFilesCopied = true

        // 遍历源文件夹和目标文件夹列表
        for (i in sourceFolders.indices) {
            val sourceFolder: File = sourceFolders[i]
            val destinationFolder: File = destinationFolders[i]

            // 创建目标文件夹（如果不存在）
            if (!destinationFolder.exists()) {
                destinationFolder.mkdirs()
            }

            // 获取源文件夹下的所有文件
            val files: Array<File> = sourceFolder.listFiles()
            if (files != null) {
                val fileCount = files.size
                var copiedCount = 0

                // 遍历源文件夹中的所有文件
                for (file in files) {
                    if (file.isFile()) {
                        // 构建目标文件路径
                        val destinationFilePath: String = destinationFolder.getAbsolutePath() + File.separator + file.getName()
                        try {
                            // 执行文件拷贝
                            copyFile(file, File(destinationFilePath))
                            copiedCount++
                            System.out.println("Copied file: " + file.getName())
                        } catch (e: Exception) {
                            e.printStackTrace()
                            allFilesCopied = false
                        }
                    }
                }

                // 检查是否所有文件都已拷贝
                if (copiedCount != fileCount) {
                    allFilesCopied = false
                }
            }
        }
        return allFilesCopied
    }


    fun copyFile(sourceFile: File?, destinationFile: File?) {
        // 创建输入流和输出流
        var inputStream:FileInputStream ?= null
        var outputStream:FileOutputStream ?= null
        try {
             inputStream = FileInputStream(sourceFile)
             outputStream = FileOutputStream(destinationFile)
            // 获取输入流和输出流的通道
            val sourceChannel: FileChannel = inputStream.getChannel()
            val destinationChannel: FileChannel = outputStream.getChannel()
            // 执行文件拷贝
            destinationChannel.transferFrom(sourceChannel, 0, sourceChannel.size())
        } finally {
            // 关闭流
            inputStream?.close()
            outputStream?.close()
        }
    }
}
package com.roy.h5native.lib.album

import android.app.Activity
import android.content.Intent
import androidx.fragment.app.Fragment
import com.hjq.permissions.Permission
import com.roy.h5native.jsNative.NativeManager
import com.roy.h5native.lib.permissions.XXPermissionsUtils
import com.zhongjh.albumcamerarecorder.settings.MultiMediaSetting
import com.zhongjh.common.entity.LocalFile
import com.zhongjh.common.enums.MimeType
import com.zhongjh.common.utils.UriUtils
import java.util.*


/**
 *    desc   :
 *    e-mail : 1391324949@qq.com
 *    date   : 2023/5/25 17:40
 *    author : Roy
 *    version: 1.0
 */
object AlbumSettingHelper {

    /**
     *
     * @param activity Activity
     * @param requestCode Int
     * @param maxSelectable Int? 最大选择文件数量
     * @param countable Boolean  是否启用数字选中
     * @param mimeTypes 文件包含视频或者图片  或者都包含
     * @param showSingleMediaType  仅仅显示一个多媒体类型
     * @param enableShooting 开启拍摄功能
     * @param echoCheckedLocalFiles  相册选中回显
     */
    private fun open(activity: Activity,fragment:Fragment?,requestCode: Int, maxSelectable: Int?, countable: Boolean, mimeTypes: Set<MimeType>, showSingleMediaType: Boolean, enableShooting: Boolean,echoCheckedLocalFiles: MutableList<LocalFile>?){

        fun AlbumSettingBuild.fx(){
            forResult(requestCode)
                    .countable(countable)
                    .albumMimeTypeSet(mimeTypes)
                    .showSingleMediaType(showSingleMediaType)
                    .echoCheckedLocalFiles(echoCheckedLocalFiles)
                    .maxSelectablePerMediaType(maxSelectable, maxSelectable, maxSelectable, maxSelectable, 0, 0, 0)
                    .enableShooting(enableShooting)
                    .build()
                    .open()
        }


        var list = arrayListOf(Permission.CAMERA, Permission.READ_EXTERNAL_STORAGE, Permission.WRITE_EXTERNAL_STORAGE)

        XXPermissionsUtils.requestPermissionMultiple(activity, list, "\n获取相机权限、获取存储权限","经授权，我们会在您使用扫码、拍照等服务时使用您的相机功能。\n在使用照片及视频上传、保存等服务时获取您的手机存储权限"){ permissions, aBoolean ->
            if(aBoolean){
                fragment?.apply {
                    AlbumSettingBuild
                            .create(fragment)
                            .fx()
                }?:apply {
                    AlbumSettingBuild
                            .create(activity)
                            .fx()
                }
            }
        }

    }


    /**
     * 多选包含图片和视频
     * @param activity Activity
     * @param requestCode Int
     * @param maxSelectable Int?
     */
    fun openMultipleChoice(activity: Activity, requestCode: Int, maxSelectable: Int?){
        open(activity,null, requestCode, maxSelectable, true, MimeType.ofAll(), false, true,null)
    }

    /**
     * 单选图片
     * @param activity Activity
     * @param requestCode Int
     */
    fun openSingleImgChoice(activity: Activity, requestCode: Int){
        open(activity,null, requestCode, 1, false, MimeType.ofImage(), true, true,null)
    }


    /**
     * 单选图片 没有gif
     * @param activity Activity
     * @param requestCode Int
     */
    fun openSingleImgChoiceNoGif(activity: Activity, requestCode: Int){
        open(activity, null, requestCode, 1, false, MimeType.of(MimeType.JPEG, MimeType.PNG, MimeType.BMP, MimeType.WEBP), true, true,null)
    }


    /**
     * 多选图片
     * @param activity Activity
     * @param requestCode Int
     */
    fun openMultipleImgChoice(activity: Activity, requestCode: Int, maxSelectable: Int?, echoCheckedLocalFiles: MutableList<LocalFile>?){
        open(activity,null, requestCode, maxSelectable, true, MimeType.ofImage(), true, true,echoCheckedLocalFiles)
    }

    /**
     * 多选图片
     * @param activity Activity
     * @param requestCode Int
     */
    fun openMultipleImgChoice(activity: Activity,fragment:Fragment?, requestCode: Int, maxSelectable: Int?, echoCheckedLocalFiles: MutableList<LocalFile>?){
        open(activity, fragment,requestCode, maxSelectable, true, MimeType.ofImage(), true, true,echoCheckedLocalFiles)
    }


    /**
     * 单选视频
     * @param activity Activity
     * @param requestCode Int
     */
    fun openSingleVideoChoice(activity: Activity, requestCode: Int){
        open(activity,null, requestCode, 1, false, MimeType.ofVideo(), true, false,null)
    }



    /**
     * 多选视频
     * @param activity Activity
     * @param requestCode Int
     */
    fun openSingleVideoChoice(activity: Activity, requestCode: Int, maxSelectable: Int?){
        open(activity,null, requestCode, maxSelectable, true, MimeType.ofVideo(), true, false,null)
    }



    fun checkIsVideo(url: String?):Boolean{
        var videoSet = HashSet<String>().apply {
            add("mpeg")
            add("mpg")
            add("mp4")
            add("m4v")
            add("mov")
            add("3gp")
            add("3gpp")
            add("3g2")
            add("3gpp2")
            add("mkv")
            add("webm")
            add("ts")
            add("avi")
        }

        url?.apply {
            if(this.length <10){
                return false
            }
            var index:Int  =  url.lastIndexOf(".")
            if(index ==  -1){
                return false;
            }
            var suffix =  url.substring(index, url.length)
            videoSet?.forEachIndexed{ index, mimeType ->
                if(suffix.contains(mimeType)){
                    return true
                }
            }
        }

        return false

    }


    /**
     *  单图选择获取结果 在 onActivityResult
     * @param data Intent?
     * @return LocalFile?
     */
    fun obtainLocalFileSingle(data: Intent?): LocalFile? {
        if (data != null) {
            val localFiles: List<LocalFile>? = MultiMediaSetting.obtainLocalFileResult(data)
            if (localFiles != null && localFiles.size > 0) {
                var localFile =  localFiles[0]
                uriToPath(localFile)
                return localFile
            }
        }
        return null
    }

    private fun uriToPath(localFile:LocalFile?) {
        localFile?.path ?: apply {//没有地址就从uri转换成path地址
            localFile?.uri?.let {
                localFile.path = UriUtils.uriToFile(NativeManager.instance.appContext, it)?.path
                //localFile.path = Files.getFilePathFromUri(BaseApplication.getApplication(), it)
            }
        }
    }


    /**
     * 多图选择获取结果 在 onActivityResult
     * @param data Intent?
     * @return List<LocalFile>?
     */
    fun obtainLocalFileMulti(data: Intent?):List<LocalFile>?{
        if (data != null) {
            val localFiles: List<LocalFile>? = MultiMediaSetting.obtainLocalFileResult(data)
            localFiles?.forEach {
                uriToPath(it)
            }
            return localFiles;
        }
        return null
    }

}
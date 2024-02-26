package com.roy.h5native.jsNative.im

import android.content.Intent
import android.text.TextUtils
import com.google.gson.Gson
import com.roy.h5native.ext.defaultString
import com.roy.h5native.jsNative.NativeManager
import com.roy.h5native.jsNative.base.BaseParam
import com.roy.h5native.jsNative.bean.ImAlbumBean
import com.roy.h5native.jsNative.interfaces.ActivityResultCallBack
import com.roy.h5native.jsNative.interfaces.IExecJs
import com.roy.h5native.jsNative.interfaces.IJsToNativeParam
import com.roy.h5native.lib.album.AlbumSettingHelper


/**
 *    desc   :
 *    e-mail : 1391324949@qq.com
 *    date   : 2024/1/25 19:06
 *    author : Roy
 *    version: 1.0
 */
class ImAlbum constructor(keyLabel: String, iExecJs: IExecJs?) : BaseParam(keyLabel,iExecJs), IJsToNativeParam {




    companion object {
        const val KEY_IMALBUM = "key_imalbum" //多选包含图片和视频
        const val KEY_IMALBUM_SINGLE_IMG = "key_imalbum_single_img"  //单选图片
        const val KEY_IMALBUM_SINGLE_IMG_NO_GIF = "key_imalbum_single_img_no_gif" //单选图片 没有gif
        const val KEY_IMALBUM_SINGLE_VIDEO = "key_imalbum_single_video"  //单选视频
        const val KEY_IMALBUM_MULTIPLE_VIDEO = "key_imalbum_multiple_video"//多选视频
        const val KEY_IMALBUM_MULTIPLE_IMG = "key_imalbum_multiple_img" //多选图片

    }

    fun multiResultLoadUrl(data: Intent?) {
        var album = AlbumSettingHelper.obtainLocalFileMulti(data);
        album?.apply {
            iExecJs?.loadUrl(defaultString(keyLabel), gson.toJson(this))
        }
    }


    fun singleResultLoadUrl(data: Intent?) {
        var album = AlbumSettingHelper.obtainLocalFileSingle(data)
        album?.apply {
            iExecJs?.loadUrl(defaultString(keyLabel), gson.toJson(this))
        }
    }





    init {


        var callBack = object : ActivityResultCallBack {
            override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
                //相册数据回调回来  在这里 调用 iExecJs 回调给h5
                if (keyLabel == KEY_IMALBUM && requestCode == 10) { //多选包含图片和视频
                    multiResultLoadUrl(data)
                } else if (keyLabel == KEY_IMALBUM_SINGLE_IMG && requestCode == 20) { //单选图片
                    singleResultLoadUrl(data)
                } else if (keyLabel == KEY_IMALBUM_SINGLE_IMG_NO_GIF && requestCode == 30) {//单选图片 没有gif
                    singleResultLoadUrl(data)
                } else if (keyLabel == KEY_IMALBUM_SINGLE_VIDEO && requestCode == 40) { //单选视频
                    singleResultLoadUrl(data)
                } else if (keyLabel == KEY_IMALBUM_MULTIPLE_VIDEO && requestCode == 50) {//多选视频
                    multiResultLoadUrl(data)
                } else if (keyLabel == KEY_IMALBUM_MULTIPLE_IMG && requestCode == 60) { //多选图片
                    multiResultLoadUrl(data)
                }
            }

        }


        if (!TextUtils.isEmpty(keyLabel)) {
            NativeManager.instance.addActivityResultCallBack(keyLabel, callBack)
        }
    }

    override fun jsToNative(param: String?) {
        //启动相册
        iExecJs?.softActivity()?.get()?.apply {
           try {
               var imAlbumBean = gson.fromJson(param,ImAlbumBean::class.java)
               if (keyLabel == KEY_IMALBUM) { //多选包含图片和视频
                   AlbumSettingHelper.openMultipleChoice(this, 10, imAlbumBean.maxSelectable)
               } else if (keyLabel == KEY_IMALBUM_SINGLE_IMG) { //单选图片
                   AlbumSettingHelper.openSingleImgChoice(this, 20)
               } else if (keyLabel == KEY_IMALBUM_SINGLE_IMG_NO_GIF) {  //单选图片 没有gif
                   AlbumSettingHelper.openSingleImgChoiceNoGif(this, 30)
               } else if (keyLabel == KEY_IMALBUM_SINGLE_VIDEO) { //单选视频
                   AlbumSettingHelper.openSingleVideoChoice(this, 40)
               } else if (keyLabel == KEY_IMALBUM_MULTIPLE_VIDEO) { //多选视频
                   AlbumSettingHelper.openSingleVideoChoice(this, 50, imAlbumBean.maxSelectable)
               } else if (keyLabel == KEY_IMALBUM_MULTIPLE_IMG) { //多选图片
                   AlbumSettingHelper.openMultipleImgChoice(this, 60, imAlbumBean.maxSelectable, imAlbumBean.echoCheckedLocalFiles)
               }
           } catch (e: Exception) {
               e.printStackTrace()
           }

        }

    }

}
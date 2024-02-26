package com.roy.h5native.lib.album

import com.roy.h5native.ext.defaultInt
import com.zhongjh.albumcamerarecorder.settings.AlbumSetting
import com.zhongjh.albumcamerarecorder.settings.CameraSetting
import com.zhongjh.albumcamerarecorder.settings.GlobalSetting
import com.zhongjh.albumcamerarecorder.settings.MultiMediaSetting
import com.zhongjh.common.enums.MimeType

/**
 *    desc   :
 *    e-mail : 1391324949@qq.com
 *    date   : 2023/5/27 19:31
 *    author : Roy
 *    version: 1.0
 */
class AlbumSettingOperate(albumSettingBuild: AlbumSettingBuild) {

    var albumSettingBuild: AlbumSettingBuild

    init {
        this.albumSettingBuild = albumSettingBuild
    }

    fun open() {
        val mGlobalSetting: GlobalSetting
        // 拍摄有关设置
        val cameraSetting = CameraSetting()
        // 支持的类型：图片，视频
        cameraSetting.mimeTypeSet(albumSettingBuild.cameraMimeTypes)
        cameraSetting.enableImageHighDefinition(albumSettingBuild.enableImageHighDefinition);
        // 相册
        val albumSetting = AlbumSetting(false) // 支持的类型：图片，视频
                .mimeTypeSet(albumSettingBuild.albumMimeTypes)
                .countable(albumSettingBuild.countable) // 是否显示多选图片的数字
                .echoCheckedLocalFiles(albumSettingBuild.echoCheckedLocalFiles)
                .showSingleMediaType(albumSettingBuild.showSingleMediaType)  // 如果选择的媒体只有图像或视频，是否只显示一种媒体类型
        // .originalEnable(true) // 最大原图size,仅当originalEnable为true的时候才有效
        // .maxOriginalSize(10)
        var setting:MultiMediaSetting? = null;
        if(albumSettingBuild.fragment==null){
            setting = MultiMediaSetting.from(albumSettingBuild.activity)
        }else{
            setting = MultiMediaSetting.from(albumSettingBuild.fragment!!)
        }
        // 全局
        mGlobalSetting = setting.choose(MimeType.ofAll());
        // 开启相册功能
        mGlobalSetting.albumSetting(albumSetting)
        // 开启拍摄功能
        if(albumSettingBuild.enableShooting) {
            mGlobalSetting.cameraSetting(cameraSetting)
        }
        mGlobalSetting // 设置路径和7.0保护路径等等
                .allStrategy(albumSettingBuild.saveStrategy) // for glide-V4
                .imageEngine(albumSettingBuild.imageEngine)
                .maxSelectablePerMediaType(albumSettingBuild.maxSelectable,
                        albumSettingBuild.maxSelectable,
                        albumSettingBuild.maxSelectable,
                        albumSettingBuild.maxSelectable,
                        albumSettingBuild.alreadyImageCount,
                        albumSettingBuild.alreadyVideoCount,
                        albumSettingBuild.alreadyAudioCount
                )
                .isImageEdit(albumSettingBuild.isImageEdit)
                .forResult(defaultInt(albumSettingBuild.requestCode))
    }
}
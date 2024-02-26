package com.roy.h5native.lib.album

import android.app.Activity
import androidx.fragment.app.Fragment
import com.roy.h5native.lib.album.configuration.CommonSizeFilter
import com.roy.h5native.lib.album.configuration.Glide4Engine
import com.roy.h5native.lib.album.configuration.VideoSizeFilter

import com.zhongjh.albumcamerarecorder.album.engine.ImageEngine
import com.zhongjh.albumcamerarecorder.album.filter.BaseFilter
import com.zhongjh.albumcamerarecorder.settings.AlbumSetting
import com.zhongjh.albumcamerarecorder.settings.CameraSetting
import com.zhongjh.common.entity.LocalFile
import com.zhongjh.common.entity.SaveStrategy
import com.zhongjh.common.enums.MimeType
import java.util.*

/**
 *    desc   :
 *    e-mail : 1391324949@qq.com
 *    date   : 2023/5/27 19:17
 *    author : Roy
 *    version: 1.0
 */
class AlbumSettingBuild constructor(activity: Activity,fragment:Fragment?) {

    private constructor(fragment: Fragment) : this(fragment.requireActivity(), fragment)

    var activity: Activity
    var fragment:Fragment? = null

    var countable: Boolean = false
    var albumMimeTypes: Set<MimeType>
    var cameraMimeTypes: Set<MimeType>
    var albumSetting: AlbumSetting? = null
    var cameraSetting: CameraSetting? = null

    var maxSelectable: Int? = null
    var maxImageSelectable: Int? = null
    var maxVideoSelectable: Int? = null
    var maxAudioSelectable: Int? = null
    var alreadyImageCount: Int
    var alreadyVideoCount: Int
    var alreadyAudioCount: Int
    var enableImageHighDefinition: Boolean
    var requestCode: Int? = 0
    var saveStrategy: SaveStrategy
    var imageEngine: ImageEngine
    var showSingleMediaType: Boolean
    var baseFilters = ArrayList<BaseFilter>()
    var isImageEdit: Boolean //TODO 默认不支持编辑 需要的话 需要引入库
    var enableShooting:Boolean //开启拍摄功能
    var echoCheckedLocalFiles: MutableList<LocalFile>?=null; //数据回显选中

    init {
        this.activity = activity
        this.fragment = fragment
        this.saveStrategy = SaveStrategy(true, "com.soudian.business_background_zh.fileprovider", "media")
        this.imageEngine = Glide4Engine()
        this.maxSelectable = 9
        this.maxImageSelectable = 9
        this.maxAudioSelectable = 9
        this.alreadyImageCount = 0
        this.alreadyVideoCount = 0
        this.alreadyAudioCount = 0
        this.baseFilters.add(CommonSizeFilter(hashSetOf(MimeType.GIF)))
        this.baseFilters.add(VideoSizeFilter(50 * BaseFilter.K * BaseFilter.K))
        this.albumMimeTypes = MimeType.ofAll()
        this.cameraMimeTypes = MimeType.ofAll()
        this.enableImageHighDefinition = true
        this.showSingleMediaType = false
        this.isImageEdit = false
        this.enableShooting = true
    }

    companion object {
        fun create(activity: Activity): AlbumSettingBuild {
            return AlbumSettingBuild(activity,null)
        }

        fun create(fragment: Fragment): AlbumSettingBuild {
            return AlbumSettingBuild(fragment)
        }
    }

    fun addFilter(baseFilter: BaseFilter): AlbumSettingBuild {
        baseFilters.add(baseFilter)
        return this
    }

    /**
     * 是否显示多选图片的数字
     * @param countable Boolean
     * @return AlbumSettingBuild
     */
    fun countable(countable: Boolean): AlbumSettingBuild {
        this.countable = countable
        return this
    }

    fun albumMimeTypeSet(mimeTypes: Set<MimeType>): AlbumSettingBuild {
        this.albumMimeTypes = mimeTypes
        return this
    }

    fun cameraMimeTypeSet(mimeTypes: Set<MimeType>): AlbumSettingBuild {
        this.cameraMimeTypes = mimeTypes
        return this
    }


    fun albumSetting(albumSetting: AlbumSetting?): AlbumSettingBuild {
        this.albumSetting = albumSetting
        return this
    }

    fun cameraSetting(cameraSetting: CameraSetting?): AlbumSettingBuild {
        this.cameraSetting = cameraSetting;
        return this
    }


    fun enableImageHighDefinition(enableImageHighDefinition: Boolean): AlbumSettingBuild {
        this.enableImageHighDefinition = enableImageHighDefinition
        return this
    }


    fun forResult(requestCode: Int?): AlbumSettingBuild {
        this.requestCode = requestCode
        return this
    }


    fun allStrategy(saveStrategy: SaveStrategy): AlbumSettingBuild {
        this.saveStrategy = saveStrategy
        return this
    }

    fun imageEngine(imageEngine: ImageEngine): AlbumSettingBuild {
        this.imageEngine = imageEngine
        return this
    }

    fun showSingleMediaType(showSingleMediaType: Boolean): AlbumSettingBuild{
        this.showSingleMediaType = showSingleMediaType
        return this
    }

    fun isImageEdit(isImageEdit: Boolean): AlbumSettingBuild{
        this.isImageEdit = isImageEdit
        return this
    }

    fun enableShooting(enableShooting:Boolean):AlbumSettingBuild{
        this.enableShooting = enableShooting
        return this
    }


    fun echoCheckedLocalFiles(echoCheckedLocalFiles: MutableList<LocalFile>?): AlbumSettingBuild {
        this.echoCheckedLocalFiles  = echoCheckedLocalFiles;
        return this
    }


    fun maxSelectablePerMediaType(
            maxSelectable: Int?,
            maxImageSelectable: Int?,
            maxVideoSelectable: Int?,
            maxAudioSelectable: Int?,
            alreadyImageCount: Int,
            alreadyVideoCount: Int,
            alreadyAudioCount: Int
    ): AlbumSettingBuild {
        this.maxSelectable = maxSelectable
        this.maxImageSelectable = maxImageSelectable
        this.maxVideoSelectable = maxVideoSelectable
        this.maxAudioSelectable = maxAudioSelectable
        this.alreadyImageCount = alreadyImageCount
        this.alreadyVideoCount = alreadyVideoCount
        this.alreadyAudioCount = alreadyAudioCount
        return this
    }


    fun build(): AlbumSettingOperate {

        return AlbumSettingOperate(this)
    }


}
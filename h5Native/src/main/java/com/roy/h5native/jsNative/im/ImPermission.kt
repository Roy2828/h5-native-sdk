package com.roy.h5native.jsNative.im

import com.roy.h5native.ext.defaultString
import com.roy.h5native.jsNative.base.BaseParam
import com.roy.h5native.jsNative.bean.ImPermissionBean
import com.roy.h5native.jsNative.interfaces.IExecJs
import com.roy.h5native.jsNative.interfaces.IJsToNativeParam
import com.roy.h5native.lib.permissions.XXPermissionsUtils

/**
 *    desc   :
 *    e-mail : 1391324949@qq.com
 *    date   : 2024/2/20 14:56
 *    author : Roy
 *    version: 1.0
 */
class ImPermission  constructor(keyLabel: String, iExecJs: IExecJs?) : BaseParam(keyLabel,iExecJs), IJsToNativeParam {

    companion object{
        const val KEY_PERMISSION_LOCATION = "location" //定位权限
        const val KEY_PERMISSION_CAMERA = "camera" //相机权限
        const val KEY_PERMISSION_STORAGE = "storage" //存储权限
    }


    var result = {permiss:List<String?>?,f:Boolean ->

        iExecJs?.loadUrl(defaultString(keyLabel), gson.toJson(ImPermissionBean(permiss,f)))
        Unit
    }


    override fun jsToNative(param: String?) {
        iExecJs?.softActivity()?.get()?.apply {
            when (keyLabel) {
                KEY_PERMISSION_LOCATION -> {
                    //定位权限
                    XXPermissionsUtils.requestPermissionLocation(this,result)
                }
                KEY_PERMISSION_CAMERA ->{
                    //相机权限
                    XXPermissionsUtils.requestPermissionCamera(this,result)
                }

                KEY_PERMISSION_STORAGE ->{
                    //存储权限
                    XXPermissionsUtils.requestPermissionStorage(this,result)
                }
            }
        }


    }

}
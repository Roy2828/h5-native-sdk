package com.roy.h5native.jsNative.im

import com.roy.h5native.ext.defaultString
import com.roy.h5native.jsNative.base.BaseParam
import com.roy.h5native.jsNative.bean.ImNativeStorageBean
import com.roy.h5native.jsNative.interfaces.IExecJs
import com.roy.h5native.jsNative.interfaces.IJsToNativeParam
import com.roy.h5native.utils.H5NativeStorageUtil

/**
 *    desc   :
 *    e-mail : 1391324949@qq.com
 *    date   : 2024/2/20 16:03
 *    author : Roy
 *    version: 1.0
 */
class ImNativeStorage constructor(keyLabel: String, iExecJs: IExecJs?) : BaseParam(keyLabel,iExecJs), IJsToNativeParam {


    companion object{
        val KEY_GET_NATIVE_STORAGE = "getNativeStorage"
        val KEY_SET_NATIVE_STORAGE = "setNativeStorage"
    }

    override fun jsToNative(param: String?) {
        var bean = gson.fromJson(param,ImNativeStorageBean::class.java)
        when(keyLabel){
            KEY_GET_NATIVE_STORAGE ->{
              var json =  H5NativeStorageUtil.getNativeStorage(bean.type,bean.keyName
                        ,bean.methodName)

                iExecJs?.loadUrl(defaultString(keyLabel),json)
            }
            KEY_SET_NATIVE_STORAGE ->{
                H5NativeStorageUtil.setNativeStorage(bean?.type,bean?.keyName,bean?.methodName)
            }

        }
    }


}
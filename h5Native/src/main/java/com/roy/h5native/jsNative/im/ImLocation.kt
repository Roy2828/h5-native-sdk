package com.roy.h5native.jsNative.im

import com.amap.api.location.AMapLocation
import com.roy.h5native.ext.defaultString
import com.roy.h5native.jsNative.NativeManager
import com.roy.h5native.jsNative.base.BaseParam
import com.roy.h5native.jsNative.bean.ImLocationBean
import com.roy.h5native.jsNative.interfaces.IExecJs
import com.roy.h5native.jsNative.interfaces.IJsToNativeParam
import com.roy.h5native.utils.LocationMapUtil

/**
 *    desc   :
 *    e-mail : 1391324949@qq.com
 *    date   : 2024/2/20 13:55
 *    author : Roy
 *    version: 1.0
 */
class ImLocation constructor(keyLabel: String,iExecJs: IExecJs?) : BaseParam(keyLabel,iExecJs), IJsToNativeParam, LocationMapUtil.LocationListener {

    var location =  LocationMapUtil()

    init {

    }


    companion object{
        val KEY_LOCATION = "location"
    }


    override fun jsToNative(param: String?) {
         location.startLocation(NativeManager.instance.appContext,this)
    }


    override fun locationSuccess(amapLocation: AMapLocation?) {
         iExecJs?.loadUrl(defaultString(keyLabel), gson.toJson(ImLocationBean("${amapLocation?.latitude}","${amapLocation?.longitude}")))
    }


    override fun locationError() {
        iExecJs?.loadUrl(defaultString(keyLabel),"")
    }

}
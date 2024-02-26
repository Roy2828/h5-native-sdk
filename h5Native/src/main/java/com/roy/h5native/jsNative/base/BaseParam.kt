package com.roy.h5native.jsNative.base

import com.google.gson.Gson
import com.roy.h5native.jsNative.interfaces.IExecJs

/**
 *    desc   :
 *    e-mail : 1391324949@qq.com
 *    date   : 2024/1/31 15:01
 *    author : Roy
 *    version: 1.0
 */
open class BaseParam constructor(keyLabel: String,iExecJs:IExecJs?) {
    var iExecJs:IExecJs? = null

    var keyLabel: String? = null

    init {
        this.keyLabel = keyLabel
        this.iExecJs = iExecJs
    }

    companion object{
        var gson = Gson()
    }

}
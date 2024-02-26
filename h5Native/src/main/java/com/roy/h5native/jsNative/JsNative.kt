package com.roy.h5native.jsNative

import com.roy.h5native.jsNative.interfaces.IJsToNativeParam

/**
 *    desc   :
 *    e-mail : 1391324949@qq.com
 *    date   : 2024/1/31 14:35
 *    author : Roy
 *    version: 1.0
 */
class JsNative constructor(iJsToNativeParam: IJsToNativeParam?): IJsToNativeParam {

    private var iJsToNativeParam: IJsToNativeParam? = null

    init {
      this.iJsToNativeParam = iJsToNativeParam
    }


    override fun jsToNative(param: String?) {
         this.iJsToNativeParam?.jsToNative(param)
    }
}
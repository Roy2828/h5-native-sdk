package com.roy.h5native.jsNative.interfaces

import androidx.fragment.app.FragmentActivity
import java.lang.ref.SoftReference

/**
 *    desc   :
 *    e-mail : 1391324949@qq.com
 *    date   : 2024/1/31 14:59
 *    author : Roy
 *    version: 1.0
 */
interface IExecJs {
    fun loadUrl(key:String,param:String?)

    fun softActivity():SoftReference<FragmentActivity?>?


}
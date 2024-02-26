package com.roy.h5native.test

import androidx.fragment.app.FragmentActivity
import com.roy.h5native.jsNative.base.BaseJs
import java.lang.ref.SoftReference

/**
 *    desc   :
 *    e-mail : 1391324949@qq.com
 *    date   : 2024/1/31 15:05
 *    author : Roy
 *    version: 1.0
 */
class testActivityWeb : BaseJs() {


    override fun loadUrl(key:String,param: String?) {
        //执行 js 操作
    }

    override fun softActivity(): SoftReference<FragmentActivity?>? {
        return null
    }
}
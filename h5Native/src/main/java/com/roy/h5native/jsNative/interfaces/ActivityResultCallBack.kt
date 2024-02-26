package com.roy.h5native.jsNative.interfaces

import android.content.Intent

/**
 *    desc   :
 *    e-mail : 1391324949@qq.com
 *    date   : 2024/1/31 16:44
 *    author : Roy
 *    version: 1.0
 */
interface ActivityResultCallBack {
    fun onActivityResult(requestCode:Int, resultCode:Int, data: Intent?);
}
package com.roy.h5native.jsNative

import android.content.Context
import android.content.Intent
import com.roy.h5native.jsNative.interfaces.ActivityResultCallBack

/**
 *    desc   :
 *    e-mail : 1391324949@qq.com
 *    date   : 2024/1/25 11:20
 *    author : Roy
 *    version: 1.0
 */
class  NativeManager {



    var appContext:Context?=null

    var activityResultCallBacks = HashMap<String,ActivityResultCallBack?>()

    companion object{
        val instance by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED){
            NativeManager()
        }
    }


    /**
     * 全局上下文
     * @param appContext Context
     */
    fun initApp(appContext:Context){
        this.appContext = appContext
    }



    /**
     * 这个需要放到Activity里面去 获取 回调 数据
     * @param requestCode Int
     * @param resultCode Int
     * @param data Intent?
     */
    fun onActivityResult(requestCode:Int, resultCode:Int, data:Intent?){
        activityResultCallBacks.forEach {
         var value:ActivityResultCallBack? =   it.value
            value?.onActivityResult(requestCode,resultCode,data)
        }
    }


    /**
     * 每个需要用到onActivityResult的功能都增加监听器
     * @param key String
     * @param activityResultCallBack ActivityResultCallBack
     */
    fun addActivityResultCallBack(key:String,activityResultCallBack:ActivityResultCallBack){
        activityResultCallBacks.put(key,activityResultCallBack)
    }


    /**
     * 清空所有监听器
     */
    fun removeAllActivityResultCallBack(){
        activityResultCallBacks.clear()
    }


}
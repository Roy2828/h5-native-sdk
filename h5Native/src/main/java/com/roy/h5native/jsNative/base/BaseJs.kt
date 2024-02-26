package com.roy.h5native.jsNative.base

import android.content.Intent
import android.net.Uri
import com.roy.h5native.jsNative.JsNative
import com.roy.h5native.jsNative.bean.ImAlbumBean
import com.roy.h5native.jsNative.bean.ImNativeStorageBean
import com.roy.h5native.jsNative.im.ImAlbum
import com.roy.h5native.jsNative.im.ImLocation
import com.roy.h5native.jsNative.im.ImNativeStorage
import com.roy.h5native.jsNative.im.ImPermission
import com.roy.h5native.jsNative.interfaces.IExecJs
import com.roy.h5native.jsNative.interfaces.IJsToNativeParam
import com.roy.h5native.utils.ToastUtil

/**
 *    desc   : TODO 通用处理js交互  需要使用者 继承
 *    e-mail : 1391324949@qq.com
 *    date   : 2024/1/25 11:56
 *    author : Roy
 *    version: 1.0
 */
abstract class BaseJs :IExecJs {


    private var map: HashMap<String, IJsToNativeParam?> = HashMap()


    /**
     * 格式 ： gotoSearchOrder?key_word=金融科技大厦新给哈狗和高后给哈狗和
     * 默认 h5调用原生 方法
     */
    private fun jsToNative(type: String, param: String?){
        var iJsToNativeParam:IJsToNativeParam? = getIJsToNativeParam(type, param);
        iJsToNativeParam?.apply {
            JsNative(this).jsToNative(param)
        }
    }


    private fun getIJsToNativeParam(type: String, initParam: String?):IJsToNativeParam?{
       return map.get(type)?: registerJsToNative(type, initParam)
    }


    /**
     *
     * @param type String 类型
     * @param initParam String?  默认初始化参数
     * @return IJsToNativeParam?
     */
    private fun registerJsToNative(type: String, initParam: String?):IJsToNativeParam?{
        var result =  when(type){
            ImAlbum.KEY_IMALBUM -> ImAlbum(ImAlbum.KEY_IMALBUM, this)
            ImAlbum.KEY_IMALBUM_SINGLE_IMG -> ImAlbum(ImAlbum.KEY_IMALBUM_SINGLE_IMG, this)
            ImAlbum.KEY_IMALBUM_SINGLE_IMG_NO_GIF -> ImAlbum(ImAlbum.KEY_IMALBUM_SINGLE_IMG_NO_GIF, this)
            ImAlbum.KEY_IMALBUM_SINGLE_VIDEO -> ImAlbum(ImAlbum.KEY_IMALBUM_SINGLE_VIDEO, this)
            ImAlbum.KEY_IMALBUM_MULTIPLE_VIDEO -> ImAlbum(ImAlbum.KEY_IMALBUM_MULTIPLE_VIDEO, this)
            ImAlbum.KEY_IMALBUM_MULTIPLE_IMG -> ImAlbum(ImAlbum.KEY_IMALBUM_MULTIPLE_IMG, this)
            ImLocation.KEY_LOCATION -> ImLocation(ImLocation.KEY_LOCATION ,this)
            ImPermission.KEY_PERMISSION_CAMERA -> ImPermission(ImPermission.KEY_PERMISSION_CAMERA ,this)
            ImPermission.KEY_PERMISSION_LOCATION -> ImPermission(ImPermission.KEY_PERMISSION_LOCATION,this)
            ImPermission.KEY_PERMISSION_STORAGE -> ImPermission(ImPermission.KEY_PERMISSION_STORAGE,this)
            ImNativeStorage.KEY_GET_NATIVE_STORAGE -> ImNativeStorage(ImNativeStorage.KEY_GET_NATIVE_STORAGE,this)
            ImNativeStorage.KEY_SET_NATIVE_STORAGE -> ImNativeStorage(ImNativeStorage.KEY_SET_NATIVE_STORAGE,this)
            else -> null
        }
        map.put(type, result)
        return   result
    }


    /**
     * 调用相册 包含视频和图片
     * @param param String?
     */
    protected fun jsToAlbum(param: ImAlbumBean?){
        jsToNative(ImAlbum.KEY_IMALBUM, BaseParam.gson.toJson(param))
    }

    /**
     * 调用相册 单选图片
     * @param param String?
     */
    protected fun jsToAlbumSingleImg(){
        jsToNative(ImAlbum.KEY_IMALBUM_SINGLE_IMG, "")
    }


    /**
     * 调用相册 单选图片无gif
     * @param param String?
     */
    protected fun jsToAlbumSingleImgNoGif(){
        jsToNative(ImAlbum.KEY_IMALBUM_SINGLE_IMG_NO_GIF, "")
    }



    /**
     * 调用相册 单选视频
     * @param param String?
     */
    protected fun jsToAlbumSingleVideo(){
        jsToNative(ImAlbum.KEY_IMALBUM_SINGLE_VIDEO, "")
    }


    /**
     * 调用相册 多选图片
     * @param param String?
     */
    protected fun jsToAlbumMultipleImg(param: ImAlbumBean?){

        jsToNative(ImAlbum.KEY_IMALBUM_MULTIPLE_IMG, BaseParam.gson.toJson(param))
    }


    /**
     * 调用相册 多选视频
     * @param param ImAlbumBean?
     */
    protected fun jsToAlbumMultipleVideo(param: ImAlbumBean?){
        jsToNative(ImAlbum.KEY_IMALBUM_MULTIPLE_VIDEO, BaseParam.gson.toJson(param))
    }


    /**
     * 定位
     */
    protected fun jsToStartLocation(){
        jsToNative(ImLocation.KEY_LOCATION, "")
    }


    /**
     * 拨打电话
      * @param phone String
     */
    protected fun jsToStartPhone(phone:String){
        val intent = Intent(Intent.ACTION_DIAL)
        val data = Uri.parse("tel:$phone")
        intent.data = data
        softActivity()?.get()?.startActivity(intent)
    }


    /**
     * 启动相机权限校验
     */
    protected fun jsToPermissionCamera(){
        jsToNative(ImPermission.KEY_PERMISSION_CAMERA, "")
    }


    /**
     * 启动定位权限校验
     */
    protected fun jsToPermissionLocation(){
        jsToNative(ImPermission.KEY_PERMISSION_LOCATION, "")
    }


    /**
     * 启动存储权限校验
     */
    protected fun jsToPermissionStorage(){
        jsToNative(ImPermission.KEY_PERMISSION_STORAGE, "")
    }


    /**
     * 用法  jsToGetNativeStorage("getNativeStorage?key=label","key","getNativeStorage")
     * 获取本地数据
     * @param type String   getNativeStorage?key=label
     * @param keyName String  通过key（和h5协商 固定写死）获取 h5传递过来的 key名
     * @param methodName String   （和h5协商 固定写死） “getNativeStorage”  回传给h5的
     */
    protected fun jsToGetNativeStorage(type:String,keyName:String,methodName:String){
        var bean = ImNativeStorageBean(type,keyName,methodName)
        jsToNative(ImNativeStorage.KEY_GET_NATIVE_STORAGE,BaseParam.gson.toJson(bean))
    }


    /**
     * 用法  jsToSetNativeStorage("setNativeStorage?key=label&value=1234","key","value")
     * 设置本地数据
     * @param type String  setNativeStorage?key=label&value=1234
     * @param keyName String   设置 存储的 key（和h5协商 固定写死） 键 用户来获取h5传递过来的key 来存储
     * @param methodName String  设置存储的value（和h5协商 固定写死） 键 用来获取h5传递过来的值 来存储
     */
    protected fun jsToSetNativeStorage(type:String,keyName:String,methodName:String){
        var bean = ImNativeStorageBean(type,keyName,methodName)
        jsToNative(ImNativeStorage.KEY_SET_NATIVE_STORAGE,BaseParam.gson.toJson(bean))
    }


    /**
     * 提示 弹窗
     * @param str String?
     */
    protected fun showTip(str:String?){
        softActivity()?.get()?.apply {
            ToastUtil.errorDialog(this,str)
        }
    }




}


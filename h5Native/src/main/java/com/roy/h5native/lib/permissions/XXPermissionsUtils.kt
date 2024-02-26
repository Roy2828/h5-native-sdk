package com.roy.h5native.lib.permissions

import android.content.Context
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.hjq.permissions.OnPermissionCallback
import com.hjq.permissions.Permission
import com.hjq.permissions.XXPermissions
import com.roy.h5native.R
import com.roy.h5native.lib.permissions.PermissionInterceptor

import java.util.*

/**
 *    desc   :
 *    e-mail : 1391324949@qq.com
 *    date   : 2023/11/21 14:10
 *    author : Roy
 *    version: 1.0
 */
object XXPermissionsUtils {


    /**
     * 存储权限  访问内、外部私有目录 不用申请权限
     * @param fragment Fragment
     * @param result Function1<Boolean, Unit>
     */
    fun requestPermissionStorage(fragment: Fragment, result: (List<String?>?, Boolean) -> Unit) {
        fragment.context?.apply {
            XXPermissions.with(fragment)
                .storage(this, result);
        }

    }

    /**
     * 存储权限    访问内、外部私有目录 不用申请权限
     * @param context Context
     * @param result Function1<Boolean, Unit>
     */
    fun requestPermissionStorage(context: Context, result: (List<String?>?, Boolean) -> Unit) {
        XXPermissions.with(context)
            .storage(context, result);
    }

    /**
     * 定位权限
     * @param context Context
     * @param result Function1<Boolean, Unit>
     */
    fun requestPermissionLocation(context: Context, result: (List<String?>?, Boolean) -> Unit) {
        XXPermissions.with(context)
            .location(context, result)
    }

    /**
     * 定位权限
     * @param context Context
     * @param result Function1<Boolean, Unit>
     */
    fun requestPermissionLocation(fragment: Fragment, result: (List<String?>?, Boolean) -> Unit) {
        fragment.context?.apply {
            XXPermissions.with(fragment)
                .location(this, result)
        }

    }


    /**
     * 相机权限
     * @param fragment Fragment
     * @param result Function2<List<String?>?, Boolean, Unit>
     */
    fun requestPermissionCamera(fragment: Fragment, result: (List<String?>?, Boolean) -> Unit) {
        fragment.context?.apply {
            XXPermissions.with(fragment)
                .camera(this, result)
        }

    }


    /**
     * 相机权限
     * @param fragment Fragment
     * @param result Function2<List<String?>?, Boolean, Unit>
     */
    fun requestPermissionCamera(context: Context, result: (List<String?>?, Boolean) -> Unit) {
        XXPermissions.with(context)
            .camera(context, result)
    }

    /**
     * 通讯录权限
     * @param context Context
     * @param result Function2<List<String?>?, Boolean, Unit>
     */
    fun requestPermissionReadContacts(context: Context, result: (List<String?>?, Boolean) -> Unit) {
        XXPermissions.with(context)
            .readContacts(context, result)
    }

    /**
     * 通讯录权限
     * @param context Context
     * @param result Function2<List<String?>?, Boolean, Unit>
     */
    fun requestPermissionReadContacts(
        fragment: Fragment,
        result: (List<String?>?, Boolean) -> Unit
    ) {
        fragment.context?.apply {
            XXPermissions.with(fragment)
                .readContacts(this, result)
        }

    }


    /**
     * 多个权限
     * @param context Context
     * @param permissions Array<String>
     * @param result Function1<Boolean, Unit>
     */
    fun requestPermissionMultiple(
        context: Context,
        permissions: List<String>,
        message: String?,
        tips: String,
        result: (List<String?>?, Boolean) -> Unit
    ) {
        XXPermissions
            .with(context)
            .permission(permissions)
            .requestAny(context, getStringArgs(context, "$message", tips), permissions, result)
    }


    //通讯录权限
    private fun XXPermissions.readContacts(
        context: Context,
        result: (List<String?>?, Boolean) -> Unit
    ) {
        permission(Permission.READ_CONTACTS)
            .requestAny(
                context,
                getStringArgs(
                    context,
                    "\n获取通讯录权限",
                    "经授权，我们会通过您的通讯录，获取您选择的联系人姓名、手机号自动填充至输入框，方便您的操作，拒绝不会影响您的正常使用。"
                ),
                listOf(Permission.READ_CONTACTS),
                result
            )
    }


    //相机权限
    private fun XXPermissions.camera(context: Context, result: (List<String?>?, Boolean) -> Unit) {
        permission(Permission.CAMERA)
            .requestAny(
                context,
                getStringArgs(context, "\n获取相机权限", "经授权，我们会在您使用扫码、拍照等服务时使用您的相机功能。"),
                listOf(Permission.CAMERA),
                result
            )
    }


    //存储权限
    private fun XXPermissions.storage(context: Context, result: (List<String?>?, Boolean) -> Unit) {
        permission(Permission.WRITE_EXTERNAL_STORAGE, Permission.READ_EXTERNAL_STORAGE)
            .requestAny(
                context,
                getStringArgs(context, "\n获取存储权限", "经授权，在使用照片及视频上传、保存等服务时使用您的手机存储"),
                listOf(Permission.WRITE_EXTERNAL_STORAGE, Permission.READ_EXTERNAL_STORAGE),
                result
            )
    }

    //定位
    private fun XXPermissions.location(
        context: Context,
        result: (List<String?>?, Boolean) -> Unit
    ) {
        permission(Permission.ACCESS_COARSE_LOCATION, Permission.ACCESS_FINE_LOCATION)
            .requestAny(
                context,
                getStringArgs(
                    context,
                    "\n获取定位权限",
                    "经授权，我们会收集您的位置信息，以便为您查找周边的充电宝设备等。请注意，在部分业务场景下，地理位置是必要信息，拒绝授权可能影响正常使用。"
                ),
                listOf(Permission.ACCESS_COARSE_LOCATION, Permission.ACCESS_FINE_LOCATION),
                result
            )
    }

    private fun XXPermissions.requestAny(
        context: Context,
        message: String?,
        permissions: List<String>,
        result: (List<String?>?, Boolean) -> Unit
    ) {

        fun XXPermissions.any() {
            interceptor(PermissionInterceptor()).request(object : OnPermissionCallback {
                override fun onGranted(permissions: MutableList<String>, allGranted: Boolean) {
                    result(permissions, allGranted)
                }

                override fun onDenied(permissions: MutableList<String>, doNotAskAgain: Boolean) {
                    super.onDenied(permissions, doNotAskAgain)
                    //doNotAskAgain   是否勾选了不再询问选项
                    result(permissions, false)
                }

            })
        }

        if (XXPermissions.isGranted(context, permissions)) {
            any()
        } else {
            applyStatement(context, message) {
                any()
            }

        }


    }


    /**
     * 申请说明
     * @param context Context
     * @param message String?
     * @param action Function0<Unit>
     */
    private fun applyStatement(context: Context, message: String?, action: () -> Unit) {

        AlertDialog.Builder(context)
            .setTitle("权限申请")
            .setMessage(message)
            .setCancelable(false)
            .setPositiveButton("申请") { dialog, which ->
                dialog.dismiss()
                action()
            }
            .setNegativeButton("取消") { dialog, which ->
                dialog.dismiss()
            }
            .show()
    }

    private fun getStringArgs(context: Context, arg: String, tips: String): String {
        return   context.getString(R.string.permission_application_tips, arg, tips)
    }

/*
    fun requestPermission(fragment: Fragment, result: (Boolean) -> Unit){
        XXPermissions.with(fragment)
                .permission(Permission.ACCESS_COARSE_LOCATION, Permission.ACCESS_FINE_LOCATION)
                .request(OnPermissionCallback { permissions: List<String?>?, allGranted: Boolean ->
                    if (allGranted) {
                        result(true)
                    } else {
                        result(false)
                    }
                })
    }*/
}
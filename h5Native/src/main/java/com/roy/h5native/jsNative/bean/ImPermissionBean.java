package com.roy.h5native.jsNative.bean;

import java.util.List;

/**
 * desc   :
 * e-mail : 1391324949@qq.com
 * date   : 2024/2/20 15:14
 * author : Roy
 * version: 1.0
 */
public class ImPermissionBean {
    public List<String> permissions;
    public Boolean result;

    public ImPermissionBean() {
    }

    public ImPermissionBean(List<String> permissions, Boolean result) {
        this.permissions = permissions;
        this.result = result;
    }
}

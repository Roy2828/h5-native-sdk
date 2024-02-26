package com.roy.h5native.jsNative.bean;

import com.zhongjh.common.entity.LocalFile;

import java.util.List;

/**
 * desc   :  相册使用
 * e-mail : 1391324949@qq.com
 * date   : 2024/2/20 10:34
 * author : Roy
 * version: 1.0
 */
public class ImAlbumBean {
    public int maxSelectable;
    public List<LocalFile> echoCheckedLocalFiles; //只要LocalFile里面的参数值都是一样的 就是选中状态

    public ImAlbumBean() {
    }

    public ImAlbumBean(int maxSelectable, List<LocalFile> echoCheckedLocalFiles) {
        this.maxSelectable = maxSelectable;
        this.echoCheckedLocalFiles = echoCheckedLocalFiles;
    }
}

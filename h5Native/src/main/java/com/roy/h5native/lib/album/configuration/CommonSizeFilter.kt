/*
 * Copyright 2017 Zhihu Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.roy.h5native.lib.album.configuration

import android.content.Context
import com.roy.h5native.R
import com.roy.h5native.ext.defaultBoolean

import com.zhongjh.albumcamerarecorder.album.filter.BaseFilter
import com.zhongjh.common.entity.IncapableCause
import com.zhongjh.common.entity.MultiMedia
import com.zhongjh.common.enums.MimeType
import java.util.*

class CommonSizeFilter internal constructor(var unsupportedMimeTypes: HashSet<MimeType>?) : BaseFilter() {



    public override fun constraintTypes(): Set<MimeType> {
        return HashSet()
    }

    override fun filter(context: Context, item: MultiMedia): IncapableCause? {
        unsupportedMimeTypes?.apply {
          var items =  iterator()
            while (defaultBoolean(items?.hasNext())){
               var next =  items?.next()
                next?.mimeTypeName?.apply {
                    if(this.equals(item.mimeType)){
                        return  IncapableCause(IncapableCause.DIALOG,
                                context.getString(R.string.error_gif_common))
                    }
                }

            }
        }
       return null
    }
}
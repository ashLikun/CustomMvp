package com.ashlikun.core.iview

/**
 * Created by yang on 2016/9/6.
 */

interface IProgressView {
    /**
     * 进度的回调
     *
     * @param progress 百分比
     * @param done     完成
     * @param isUpdate 是否是上传
     */
    fun upLoading(progress: Int, done: Boolean, isUpdate: Boolean, isCompress: Boolean)

    fun dismissProgressDialog()

}

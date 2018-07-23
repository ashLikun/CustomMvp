package com.ashlikun.core.iview

/**
 * 作者　　: 李坤
 * 创建时间:2017/8/26 0026　0:02
 * 邮箱　　：496546144@qq.com
 *
 *
 * 功能介绍：设配器数据改变
 */

interface INotifyView {
    fun notifyChanged()

    interface INotifyItemChangView {
        fun notifyItemChanged(position: Int)
    }

    interface INotifyItemRemovedView {
        fun notifyItemRemoved(position: Int)
    }

    interface INotifyItemAddView {
        fun notifyItemAdd(position: Int)
    }

}

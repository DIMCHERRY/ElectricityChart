package com.codercoral.electricitychart.video.codercoral.index.item

import com.chad.library.adapter.base.entity.MultiItemEntity
import com.codercoral.electricitychart.video.codercoral.index.bean.IndexBean

class IndexItem (itemType: Int, data: IndexBean?): MultiItemEntity{
    companion object {
        val IMG = 1
        val  LAYOUT= 2
    }
    private val data: IndexBean? = data
    private val itemType: Int = itemType


    override fun getItemType(): Int {
        return itemType
    }
    open fun getData(): IndexBean? {
        return data
    }
}
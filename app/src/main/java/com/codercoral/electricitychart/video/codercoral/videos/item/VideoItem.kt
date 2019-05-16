package com.codercoral.electricitychart.video.codercoral.videos.item

import com.chad.library.adapter.base.entity.MultiItemEntity
import com.codercoral.electricitychart.video.codercoral.videos.bean.VideoListBean


class VideoItem (itemType: Int, data: VideoListBean.ItemListBeanX?): MultiItemEntity{
    companion object {
        //轮播图
        val IMG = 1
        //列表
        val  LAYOUT= 2
    }
    private val data: VideoListBean.ItemListBeanX? = data
    private val itemType: Int = itemType


    override fun getItemType(): Int {
        return itemType
    }
    open fun getData(): VideoListBean.ItemListBeanX? {
        return data
    }
}
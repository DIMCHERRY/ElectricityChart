package com.codercoral.electricitychart.video.codercoral.index.bean

class IndexBean{
    var orderId: Int = 0
    var orderNo: String? = null
    var orderInfo: String? = null

    constructor(orderId: Int, orderNo: String?, orderInfo: String?) {
        this.orderId = orderId
        this.orderNo = orderNo
        this.orderInfo = orderInfo
    }
}
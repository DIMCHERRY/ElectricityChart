package com.codercoral.electricitychart.video.codercoral.index

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.codercoral.electricitychart.R
import com.codercoral.electricitychart.video.codercoral.base.BaseFragment
import com.codercoral.electricitychart.video.codercoral.index.adapter.IndexAdapter
import com.codercoral.electricitychart.video.codercoral.index.bean.IndexBean
import com.codercoral.electricitychart.video.codercoral.index.item.IndexItem
import kotlinx.android.synthetic.main.fragment_index.*

class IndexFragment : BaseFragment() {
    override fun onClick(v: View?) {

    }

    override val layoutId: Int
        get() = R.layout.fragment_index


    override fun doBusiness(mContext: Context?) {
        setRecy()
    }

    private fun setRecy() {
        var data: MutableList<IndexItem> = ArrayList()
        for (i in 0..9) {
            if (i == 0) {
                data.add(IndexItem(IndexItem.IMG, null))
            } else {
                data.add(IndexItem(IndexItem.LAYOUT, IndexBean(i, "$i", "条目 $i")))
            }
        }
        val adapter = IndexAdapter(data)
        val layoutManager = LinearLayoutManager(mactivity)
        rv_index.layoutManager = layoutManager
        rv_index.adapter = adapter
    }

    override fun widgetClick(v: View) {
    }

}
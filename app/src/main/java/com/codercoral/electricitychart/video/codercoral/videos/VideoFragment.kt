package com.codercoral.electricitychart.video.codercoral.videos

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.webkit.WebView
import android.widget.TextView
import android.widget.Toast
import android.widget.Toast.makeText
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.codercoral.electricitychart.R
import com.codercoral.electricitychart.video.codercoral.base.BaseFragment
import com.example.coderCoral.electricityChart.base.Constant.Companion.VIDEO_KEY
import com.codercoral.electricitychart.video.codercoral.videos.adapter.VideoAdapter
import com.codercoral.electricitychart.video.codercoral.videos.bean.VideoListBean
import com.codercoral.electricitychart.video.codercoral.videos.item.VideoItem
import com.codercoral.electricitychart.video.codercoral.videos.presenter.VideoContract
import com.codercoral.electricitychart.video.codercoral.videos.presenter.VideoPresenter
import com.codercoral.electricitychart.view.fragment.NewsFragment
import kotlinx.android.synthetic.main.fragment_videos.*

class VideoFragment : BaseFragment(), VideoContract.view {
    private var webview: WebView? = null

    companion object{
        fun newInstance(page: Int): VideoFragment {
            val args = Bundle()
            args.putInt("Video", page)
            val fragment = VideoFragment()
            fragment.arguments = args
            return fragment
        }
    }




    override fun onClick(v: View?) {

    }

    //presenter
    lateinit var videoPresenter: VideoPresenter

    //adapter
    lateinit var adapter: VideoAdapter

    //每页数目
    var pagnum: Int = 16

    //下一页的url
    lateinit var pageurl: String

    //加载布局
    override val layoutId: Int
        get() = R.layout.fragment_videos

    override fun doBusiness(mContext: Context?) {
        //实例化presenter
        videoPresenter = VideoPresenter(this, this.mactivity!!)
        //获取视频集合
        videoPresenter.getVideoList(pagnum)
        //处理searchview搜索点击事件
        search_view.setOnEditorActionListener(TextView.OnEditorActionListener { textView, actionId, keyEvent ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                val text = search_view.text.toString().trim { it <= ' ' }
                //判断是否为空做处理
                if (!TextUtils.isEmpty(text)) {
                    videoPresenter.searchVideos(text)
                    hideSoftInput(mactivity!!)
                    sl_video.isRefreshing = true
                } else {
                    makeText(activity, "不可为空", Toast.LENGTH_SHORT).show()
                }
                return@OnEditorActionListener true
            }
            false
        })
    }

    @SuppressLint("WrongConstant")
    fun hideSoftInput(activity: Activity) {
        val view = activity.window.peekDecorView()
        if (view != null) {
            val inputmanger = activity.getSystemService("input_method") as InputMethodManager
            inputmanger.hideSoftInputFromWindow(view.windowToken, 0)
        }

    }
    override fun widgetClick(v: View) {
    }

    /**
     * @param videoList 主要的数据列表
     * 展示数据
     */
    override fun showData(videoList: VideoListBean) {
        //加载数据
        setRecy(videoList.itemList)
        //获取到下一页url并赋值
        pageurl = videoList.nextPageUrl
    }

    /**
     * @param itemList 主要的数据列表
     * 设置加载数据
     */
    private fun setRecy(itemList: List<VideoListBean.ItemListBeanX>) {
        var data: MutableList<VideoItem> = ArrayList()
        //遍历itemlist添加我们想要的数据类型添加到多布局中
        for (itemListBeanX in itemList) {
            //添加轮播图的数据
            if (itemListBeanX.type.equals("horizontalScrollCard")) {
                data.add(VideoItem(VideoItem.IMG, itemListBeanX))
            }
            //添加video列表
            else if (itemListBeanX.type.equals("video")) {
                data.add(VideoItem(VideoItem.LAYOUT, itemListBeanX))
            }
        }
        //recycle view相关操作
        adapter = VideoAdapter(data)
        val layoutManager = LinearLayoutManager(mactivity)
        rv_video.layoutManager = layoutManager
        rv_video.adapter = adapter
        adapter.onItemClickListener = BaseQuickAdapter.OnItemClickListener({ adapter, view, position ->
            //跳转到播放视频的页面并把数据携带过去 ps：注意携带数据中的不管有多少层对象都要序列化
            var bundle = Bundle()
            bundle.putSerializable(VIDEO_KEY, data[position].getData())
            startActivityf(VideoActivity::class.java, bundle)
        })

        //下拉刷新
        sl_video.setOnRefreshListener( {
            videoPresenter.getVideoList(pagnum)
            search_view.setText("")
        })
        //上拉加载
        adapter.setOnLoadMoreListener({
            videoPresenter.loadMoreList(pageurl)
        }, rv_video)
    }

    /**
     * @param videobean
     * 加载更多
     */
    override fun showMoreList(videobean: VideoListBean) {
        //判断是否没有更多数据
        if (videobean.itemList.size < pagnum || videobean.itemList.size == 0) {
            adapter.loadMoreEnd()
            makeText(activity, "没有更多数据", Toast.LENGTH_SHORT).show()
        }
        //加载更多相关操作
        else {
            for (itemListBeanX in videobean.itemList) {
                adapter.addData(VideoItem(VideoItem.LAYOUT, itemListBeanX))
            }
            pageurl = videobean.nextPageUrl
            adapter.loadMoreComplete()
        }
    }

    /**
     * @param str 错误信息
     * 错误信息反馈
     */
    override fun showfailemsg(str: String) {
        makeText(activity, str, Toast.LENGTH_SHORT).show()
    }

    /**
     * @param isshow 是否加载完毕
     * 设置SwipeRefreshLayout是否加载中
     */
    override fun getLists(isshow: Boolean) {
            sl_video.isRefreshing = isshow
    }

    /**
     * @param videolist 搜索内容
     * 加载搜索项
     */
    override fun showSearch(videolist: VideoListBean) {
        //判断是否有相关视频并提示
        if (videolist.itemList.size == 0) {
            makeText(activity, "抱歉没有相关视频", Toast.LENGTH_SHORT).show()
        } else {
            setRecy(videolist.itemList)
        }
        sl_video.isRefreshing = false
    }
}
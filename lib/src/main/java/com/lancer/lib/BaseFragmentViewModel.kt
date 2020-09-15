package com.lancer.lib

import android.view.View
import android.widget.ProgressBar
import android.widget.RelativeLayout
import androidx.lifecycle.ViewModel

class BaseFragmentViewModel : ViewModel() {
    /**
     * 显示加载失败布局
     */
    var loadErrorView: RelativeLayout? = null

    /**
     * 显示无网络布局
     */
    var noContentView: RelativeLayout? = null

    /**
     * 根布局
     */
    var rootView: View? = null

    /**
     * 加载中界面
     *
     */
    var loadingView:ProgressBar?=null
}
package com.lancer.lib.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.CallSuper
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.lancer.lib.BaseFragmentViewModel
import com.lancer.lib.R
import kotlinx.android.synthetic.main.status_loading_view.view.*

//界面状态管理记得在使用的界面倒入layout_status.xml
abstract class BaseFragment : Fragment(), RequestLifecycle {
    private val viewModel by lazy {
        ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(BaseFragmentViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(initLayoutId(), container, false)
        initStatusView(view)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initView()
        initData()
    }

    /**
     * 在fragment基类中获取通用的控件
     */
    private fun initStatusView(view: View): View {
        viewModel.rootView = view
        viewModel.loadingView = view.findViewById(R.id.loading)
        viewModel.noContentView = view.findViewById(R.id.noContentView)
        viewModel.loadErrorView = view.findViewById(R.id.errorView)
        if (viewModel.loadingView == null) {
            throw NullPointerException("loading is null")
        }
        if (viewModel.loadErrorView == null) {
            throw NullPointerException("loadErrorView is null")
        }
        return view
    }

    //在Fragment中加载内容失败，显示该布局
    protected fun showLoadErrorView(msg: String = "加载数据失败") {
        loadFinished()
        if (viewModel.loadErrorView != null) {
            viewModel.loadErrorView?.let {
                it.visibility = View.VISIBLE
                val errorText = it.findViewById<TextView>(R.id.errorText)
                errorText.text = msg
                return@let
            }
        }
    }

    //在Fragment中没有内容可加载后，显示该布局
    protected fun showNoContentView(tip: String = "没有更多的内容了") {
        loadFinished()
        if (viewModel.noContentView != null) {
            viewModel.noContentView?.let {
                it.visibility = View.VISIBLE
                val noContentText = it.findViewById<TextView>(R.id.noContentText)
                noContentText.text = tip
                return@let
            }
        }

    }

    //隐藏错误布局
    private fun hideErrorView() {
        viewModel.loadErrorView?.visibility = View.GONE
    }

    //隐藏没有内容的布局
    private fun hideNoContentView() {
        viewModel.noContentView?.visibility = View.GONE
    }

    //开始加载，显示加载控件等
    @CallSuper
    override fun startLoading() {
        viewModel.loadingView?.visibility=View.VISIBLE
        hideNoContentView()
        hideErrorView()
    }

    //加载结束
    @CallSuper
    override fun loadFinished() {
        viewModel.loadingView?.visibility=View.GONE
        hideNoContentView()
        hideErrorView()
    }

    //加载出错
    @CallSuper
    override fun loadFailed() {
        viewModel.loadingView?.visibility=View.GONE
        hideNoContentView()
        hideErrorView()
    }

    abstract fun initData()

    abstract fun initView()

    abstract fun initLayoutId(): Int
}
package com.dqdana.code.easyadapter.common.test

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.TextView
import com.dqdana.code.easyadapter.R
import com.dqdana.code.easyadapter.annotation.RequiresContent
import com.dqdana.code.easyadapter.common.widget.EmptyView
import com.dqdana.code.easyadapter.common.widget.ErrorView
import kotlinx.android.synthetic.main.activity_test_requires_content.*

@RequiresContent
class TestRequiresContentActivity : AppCompatActivity(), ErrorView.OnErrorViewClickListener, EmptyView.OnEmptyViewClickListener {

    companion object {

        private const val ID_NONE = -1
        private const val LoadViewId = 1
        private const val EmptyViewId = 2
        private const val ErrorViewId = 3
        private const val ContentViewId = 4

        fun start(context: Context) {
            val intent = Intent(context, TestRequiresContentActivity::class.java)
            context.startActivity(intent)
        }
    }

    private var mContainer: ViewGroup? = null
    private var mCurrentId = ID_NONE

    private var mLoadView: View? = null
    private var mEmptyView: View? = null
    private var mErrorView: View? = null
    private var mContentView: View? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_requires_content)
        initView()
        initAnnotation()
    }

    private fun initView() {
        mContainer = fl_default_content_container
        btn_load.setOnClickListener { displayLoadView() }
        btn_empty.setOnClickListener { displayEmptyView() }
        btn_error.setOnClickListener { displayErrorView() }
        btn_content.setOnClickListener { displayContentView() }
        mContentView = TextView(this).apply { text = "测试内容" }
    }

    @Suppress("UNCHECKED_CAST")
    private fun initAnnotation() {
        val annotation = this.javaClass.getAnnotation(RequiresContent::class.java) ?: return
        annotation.loadView.qualifiedName?.run {
            mLoadView = buildView(Class.forName(this) as Class<View>)
        }
        annotation.emptyView.qualifiedName?.run {
            mEmptyView = buildView(Class.forName(this) as Class<View>)
        }
        annotation.errorView.qualifiedName?.run {
            mErrorView = buildView(Class.forName(this) as Class<View>)
        }
    }

    private fun buildView(cls: Class<View>): View {
        try {
            val loadViewConstructor = cls.getDeclaredConstructor(Context::class.java)
            return loadViewConstructor.newInstance(this@TestRequiresContentActivity)
        } catch (e: Throwable) {
            throw RuntimeException("Unable to create View for" + cls + ". " + e.localizedMessage, e)
        }
    }

    override fun onEmptyViewClick(view: View) {
        onRefresh()
    }

    override fun onErrorViewClick(view: View) {
        onRefresh()
    }

    /**
     * 模拟网络请求
     */
    private fun onRefresh() {
        /*
        Observable.just(1)
                .subscribeOn(Schedulers.io())
                .delay(2, TimeUnit.SECONDS)
                .compose(RxUtils.progressTransformer(this)) // 合并一个观察者, 请求时, displayLoadView(), 完毕后 displayContentView
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ integer -> contentPresenter.displayContentView() })
        */
        displayLoadView()
        mContainer?.postDelayed({
            displayContentView()
        }, 2000)
    }

    /**
     * 显示进度条
     */
    fun displayLoadView() {
        if (mCurrentId != LoadViewId) {
            displayView(LoadViewId)
        }
    }

    /**
     * 显示空白页
     */
    fun displayEmptyView() {
        if (mCurrentId != EmptyViewId) {
            val view = displayView(EmptyViewId)
            if (view is EmptyView) {
                view.setOnEmptyViewClickListener(this)
            }
        }
    }

    /**
     * 显示错误页面
     */
    fun displayErrorView() {
        if (mCurrentId != ErrorViewId) {
            val view = displayView(ErrorViewId)
            if (view is ErrorView) {
                view.setOnErrorViewClickListener(this)
            }
        }
    }

    /**
     * 显示内容
     */
    fun displayContentView() {
        if (mCurrentId != ContentViewId && mContentView != null) {
            mContainer?.run {
                val layoutParams = ViewGroup.LayoutParams(MATCH_PARENT, MATCH_PARENT)
                removeAllViews()
                addView(mContentView, layoutParams)
                mCurrentId = ContentViewId
            }
        }
    }

    private fun displayView(viewId: Int): View? {
        val view = when (viewId) {
            LoadViewId -> mLoadView
            EmptyViewId -> mEmptyView
            ErrorViewId -> mErrorView
            else -> null
        } ?: return null

        val layoutParams = ViewGroup.LayoutParams(MATCH_PARENT, MATCH_PARENT)
        mContainer?.run {
            removeAllViews()
            addView(view, layoutParams)
            mCurrentId = viewId
            return view
        }

        return null
    }
}
package com.dqdana.code.easyadapter.common.widget

import android.content.Context
import android.support.annotation.DrawableRes
import android.support.annotation.StringRes
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import com.dqdana.code.easyadapter.R
import com.dqdana.code.easyadapter.common.utils.ViewUtils
import kotlinx.android.synthetic.main.common_view_default_empty.view.*

class DefaultEmptyView(context: Context) : FrameLayout(context), EmptyView, View.OnClickListener {

    init {
        val view = LayoutInflater.from(context).inflate(R.layout.common_view_default_empty, this, false)
        addView(view)
    }

    private var listener: EmptyView.OnEmptyViewClickListener? = null

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        setOnClickListener(this)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        setOnClickListener(null)
        listener = null
    }

    override fun onClick(v: View) {
        listener?.onEmptyViewClick(v)
    }

    override fun buildEmptyImageView(drawableRes: Int): EmptyView {
        supportUiEmptyImageView.setImageResource(drawableRes)
        return this
    }

    override fun buildEmptyTitle(stringRes: Int): EmptyView {
        supportUiEmptyTitle.setText(stringRes)
        return this
    }

    override fun buildEmptyTitle(title: String): EmptyView {
        supportUiEmptyTitle.text = title
        return this
    }

    override fun buildEmptySubtitle(stringRes: Int): EmptyView {
        supportUiEmptySubtitle.setText(stringRes)
        return this
    }

    override fun buildEmptySubtitle(subtitle: String): EmptyView {
        supportUiEmptySubtitle.text = subtitle
        return this
    }

    override fun shouldDisplayEmptySubtitle(display: Boolean): EmptyView {
        ViewUtils.setGone(supportUiEmptySubtitle, !display)
        return this
    }

    override fun shouldDisplayEmptyTitle(display: Boolean): EmptyView {
        ViewUtils.setGone(supportUiEmptyTitle, !display)
        return this
    }

    override fun shouldDisplayEmptyImageView(display: Boolean): EmptyView {
        ViewUtils.setGone(supportUiEmptyImageView, !display)
        return this
    }

    override fun setOnEmptyViewClickListener(listener: EmptyView.OnEmptyViewClickListener) {
        this.listener = listener
    }
}

interface EmptyView {

    fun buildEmptyImageView(@DrawableRes drawableRes: Int): EmptyView

    fun buildEmptyTitle(@StringRes stringRes: Int): EmptyView

    fun buildEmptyTitle(title: String): EmptyView

    fun buildEmptySubtitle(@StringRes stringRes: Int): EmptyView

    fun buildEmptySubtitle(subtitle: String): EmptyView

    fun shouldDisplayEmptySubtitle(display: Boolean): EmptyView

    fun shouldDisplayEmptyTitle(display: Boolean): EmptyView

    fun shouldDisplayEmptyImageView(display: Boolean): EmptyView

    fun setOnEmptyViewClickListener(listener: OnEmptyViewClickListener)

    interface OnEmptyViewClickListener {
        fun onEmptyViewClick(view: View)
    }
}

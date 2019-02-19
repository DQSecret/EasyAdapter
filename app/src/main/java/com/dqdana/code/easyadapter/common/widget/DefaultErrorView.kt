package com.dqdana.code.easyadapter.common.widget

import android.content.Context
import android.support.annotation.DrawableRes
import android.support.annotation.StringRes
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import com.dqdana.code.easyadapter.R
import com.dqdana.code.easyadapter.common.utils.ViewUtils
import kotlinx.android.synthetic.main.common_view_default_error.view.*

class DefaultErrorView(context: Context) : FrameLayout(context), ErrorView, View.OnClickListener {

    init {
        val view = LayoutInflater.from(context).inflate(R.layout.common_view_default_error, this, false)
        addView(view)
    }

    private var listener: ErrorView.OnErrorViewClickListener? = null

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
        listener?.onErrorViewClick(v)
    }

    override fun buildErrorImageView(drawableRes: Int): ErrorView {
        supportUiErrorImageView.setImageResource(drawableRes)
        return this
    }

    override fun buildErrorTitle(stringRes: Int): ErrorView {
        supportUiErrorTitle.setText(stringRes)
        return this
    }

    override fun buildErrorTitle(title: String): ErrorView {
        supportUiErrorTitle.text = title
        return this
    }

    override fun buildErrorSubtitle(stringRes: Int): ErrorView {
        supportUiErrorSubtitle.setText(stringRes)
        return this
    }

    override fun buildErrorSubtitle(subtitle: String): ErrorView {
        supportUiErrorSubtitle.text = subtitle
        return this
    }

    override fun shouldDisplayErrorSubtitle(display: Boolean): ErrorView {
        ViewUtils.setGone(supportUiErrorSubtitle, !display)
        return this
    }

    override fun shouldDisplayErrorTitle(display: Boolean): ErrorView {
        ViewUtils.setGone(supportUiErrorTitle, !display)
        return this
    }

    override fun shouldDisplayErrorImageView(display: Boolean): ErrorView {
        ViewUtils.setGone(supportUiErrorImageView, !display)
        return this
    }

    override fun setOnErrorViewClickListener(listener: ErrorView.OnErrorViewClickListener) {
        this.listener = listener
    }
}

interface ErrorView {
    fun buildErrorImageView(@DrawableRes drawableRes: Int): ErrorView

    fun buildErrorTitle(@StringRes stringRes: Int): ErrorView

    fun buildErrorTitle(title: String): ErrorView

    fun buildErrorSubtitle(@StringRes stringRes: Int): ErrorView

    fun buildErrorSubtitle(subtitle: String): ErrorView

    fun shouldDisplayErrorSubtitle(display: Boolean): ErrorView

    fun shouldDisplayErrorTitle(display: Boolean): ErrorView

    fun shouldDisplayErrorImageView(display: Boolean): ErrorView

    fun setOnErrorViewClickListener(listener: OnErrorViewClickListener)

    interface OnErrorViewClickListener {
        fun onErrorViewClick(view: View)
    }
}
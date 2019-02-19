package com.dqdana.code.easyadapter.common.widget

import android.content.Context
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.dqdana.code.easyadapter.R

class DefaultLoadView(context: Context) : FrameLayout(context) {

    init {
        val view = LayoutInflater.from(context).inflate(R.layout.common_view_default_load, this, false)
        addView(view)
    }
}

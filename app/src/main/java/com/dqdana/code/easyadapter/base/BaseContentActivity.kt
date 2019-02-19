package com.dqdana.code.easyadapter.base

import android.support.v7.app.AppCompatActivity
import android.view.View
import com.dqdana.code.easyadapter.common.widget.EmptyView
import com.dqdana.code.easyadapter.common.widget.ErrorView
import com.dqdana.code.easyadapter.retention.RequiresContent

@RequiresContent
abstract class BaseContentActivity
    : AppCompatActivity(),
        EmptyView.OnEmptyViewClickListener,
        ErrorView.OnErrorViewClickListener {

    override fun onEmptyViewClick(view: View) {

    }

    override fun onErrorViewClick(view: View) {

    }
}
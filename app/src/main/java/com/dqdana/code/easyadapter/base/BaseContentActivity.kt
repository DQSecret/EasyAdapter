package com.dqdana.code.easyadapter.base

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.dqdana.code.easyadapter.retention.RequiresContent

@RequiresContent
abstract class BaseContentActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
    }

    abstract fun initView()

}
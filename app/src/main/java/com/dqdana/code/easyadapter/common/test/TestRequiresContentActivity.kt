package com.dqdana.code.easyadapter.common.test

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.dqdana.code.easyadapter.R
import com.dqdana.code.easyadapter.common.widget.DefaultLoadView
import com.dqdana.code.easyadapter.retention.RequiresContent
import kotlinx.android.synthetic.main.activity_test_requires_content.*

@RequiresContent
class TestRequiresContentActivity : AppCompatActivity() {

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, TestRequiresContentActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_requires_content)
        initView()
    }

    private fun initView() {
        val view = DefaultLoadView(this)
        fl_default_content_container.addView(view)
    }
}

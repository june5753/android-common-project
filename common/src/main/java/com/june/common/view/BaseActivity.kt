package com.june.common.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

/**
 *封装通用的基类BaseActivity
 */
abstract class BaseActivity : AppCompatActivity(), IInitView {
    // 是否是第一次调用onResume
    private var isFirstOnResume = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(getLayoutID())
        initVariable(savedInstanceState)
        initView(savedInstanceState)
        initListener()
        loadDataOnCreate()
    }


    override fun onResume() {
        super.onResume()

        onResumeEveryTime()
        if (isFirstOnResume) {
            isFirstOnResume = false
            onResumeFirst()
        } else {
            onResumeExceptFirst()
        }
    }

    /**
     * 第一次onResume的时候调用
     */
    protected open fun onResumeFirst() {}

    /**
     * 除了第一次onResume都调用
     */
    protected open fun onResumeExceptFirst() {}

    /**
     * 在onResume的时候每次都调用
     */
    protected open fun onResumeEveryTime() {}

}
package com.june.common.view

import android.os.Bundle
import android.view.View

/**
 * 初始化View要实现的接口
 */
interface IInitView {

    /**
     * 获取布局id
     */
    fun getLayoutID(): Int

    /**
     * 获取布局View
     */
    fun getLayoutView(): View? = null

    /**
     * 初始化变量
     */
    fun initVariable(savedInstanceState: Bundle?) {}

    /**
     * 初始化视图
     */
    fun initView(savedInstanceState: Bundle?) {}

    /**
     * 初始化监听
     */
    fun initListener() {}

    /**
     * OnCreate的时候加载一次数据
     */
    fun loadDataOnCreate() {}
}
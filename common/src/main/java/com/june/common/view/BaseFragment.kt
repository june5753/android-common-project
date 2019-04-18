package com.june.common.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

/**
 *
 *封装通用的基类BaseFragment
*/

abstract class BaseFragment : Fragment(), IInitView {
    // 是否完成初始化
    private var isInitialized = false
    // fragment是否是第一次对用户可见
    private var isFragmentFirstVisible = true
    // fragment当前是否对用户可见
    private var isFragmentVisible = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val layoutID = getLayoutID()
        val view: View

        view = if (layoutID == 0) {
            getLayoutView() ?: throw NullPointerException("ContentView can not be null")
        } else {
            inflater.inflate(layoutID, container, false)
        }
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initVariable(savedInstanceState)
        initView(savedInstanceState)
        initListener()
        loadDataOnCreate()

        isInitialized = true
        if (isFragmentVisible) {
            onVisibleEveryTime()
            isFragmentFirstVisible = false
            onVisibleFirst()
        }
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)

        if (hidden) {
            isFragmentVisible = false
        } else {
            isFragmentVisible = true
            if (isInitialized) {
                onVisibleEveryTime()
                if (isFragmentFirstVisible) {
                    isFragmentFirstVisible = false
                    onVisibleFirst()
                } else {
                    onVisibleExceptFirst()
                }
            }
        }
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)

        if (isVisibleToUser) {
            isFragmentVisible = true
            if (isInitialized) {
                onVisibleEveryTime()
                if (isFragmentFirstVisible) {
                    isFragmentFirstVisible = false
                    onVisibleFirst()
                } else {
                    onVisibleExceptFirst()
                }
            }
        } else {
            isFragmentVisible = false
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()

        isInitialized = false
    }

    /**
     * 当按下返回键，调用该方法，返回true代表消费该事件，默认返回false
     * 注意，该方法的使用场景是，在Fragment嵌套在FragmentActivity中时，当返回键按下时，由FragmentActivity
     * 显示的调用该方法，并根据返回值，判断该FragmentActivity是否消费该事件
     */
    open fun onBackPressed(): Boolean = false

    /**
     * 第一次对用户可见的时候调用
     */
    open fun onVisibleFirst() {}

    /**
     * 除了第一次对用户可见都调用
     */
    open fun onVisibleExceptFirst() {}

    /**
     * 在每次对用户可见的时候都调用
     */
    open fun onVisibleEveryTime() {}

    /**
     * 界面不可见时调用
     */
    open fun onInvisible() {}

    /**
     * 是否完成初始化
     */
    fun isInitialized(): Boolean = isInitialized

    /**
     * Fragment是否对用户可见
     */
    fun isFragmentVisibleToUser(): Boolean = isFragmentVisible
}

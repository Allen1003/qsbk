package com.allen.base.base.adapter

import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

/**
 * 给ViewPager创建Fragment使用
 *
 */
class BaseFragmentPageAdapter : FragmentStatePagerAdapter {

    private var mFragments = arrayListOf<Fragment>()
    private var mTitles = arrayListOf<String>()

    constructor(manager: FragmentManager) : super(manager)

    constructor(manager: FragmentManager, fragments: ArrayList<Fragment>) : super(manager) {
        this.mFragments = fragments
    }

    constructor(manager: FragmentManager, fragments: Array<Fragment>) : super(manager) {
        this.mFragments.addAll(fragments.toList())
    }

    constructor(manager: FragmentManager, fragments: ArrayList<Fragment>, titles: ArrayList<String>) : super(manager) {
        this.mFragments = fragments
        this.mTitles = titles
    }


    constructor(manager: FragmentManager, fragments: ArrayList<Fragment>, titles: Array<String>) : super(manager) {
        this.mFragments = fragments
        this.mTitles.addAll(titles.toList())
    }

    constructor(manager: FragmentManager, fragments: Array<Fragment>, titles: Array<String>) : super(manager) {
        this.mFragments.addAll(fragments.toList())
        this.mTitles.addAll(titles.toList())
    }

    override fun getItem(position: Int): Fragment {
        return mFragments[position]
    }

    override fun getCount(): Int {
        return mFragments.size
    }


    override fun getPageTitle(position: Int): CharSequence? {
        return if (mTitles.isNotEmpty() && position < mTitles.size) {
            mTitles[position]
        } else {
            super.getPageTitle(position)
        }
    }

    /**
     * 更新数据
     */
    fun setData(fragments: ArrayList<Fragment>, titles: ArrayList<String>) {
        mFragments = fragments
        mTitles = titles
        notifyDataSetChanged()
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {

    }

}
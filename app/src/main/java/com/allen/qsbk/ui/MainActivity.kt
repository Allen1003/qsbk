package com.allen.qsbk.ui

import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.allen.app.core.RouterHub
import com.allen.app.data.bean.*
import com.allen.app.helper.RouteHelper
import com.allen.base.base.activity.BaseActivity
import com.allen.base.base.adapter.BaseFragmentPageAdapter
import com.allen.base.widget.btm_nav_view.BtmNavItemView
import com.allen.base.widget.btm_nav_view.BtmNavViewListener
import com.allen.base.widget.btm_nav_view.LottieNavItemView
import com.allen.qsbk.R
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : BaseActivity(), BtmNavViewListener {

    override fun getLayoutResID() = R.layout.activity_main

    private val mFragments = ArrayList<Fragment>()

    private val items = TreeMap<Int, BtmNavItemView>()

    override fun initViews() {
        super.initViews()
        loadFragments()
        val pageAdapter = BaseFragmentPageAdapter(supportFragmentManager, mFragments)
        mViewPager.adapter = pageAdapter
        bottomBar.create(items, 0)
        bottomBar.setBtmNavViewListener(this)

    }

    override fun initEvents() {
        mViewPager?.addOnPageChangeListener(object : ViewPager.SimpleOnPageChangeListener() {
            override fun onPageSelected(position: Int) {
                bottomBar?.select(position)
            }
        })
    }

    override fun onBtmNavViewClick(id: Int, oldId: Int) {
        mViewPager?.currentItem = id
    }

    private fun loadFragments() {
        loadHome()
        loadDynamic()
        loadLive()
        loadScrip()
        loadMe()
    }

    private fun loadHome() {
        val home = RouteHelper.obtainARouterFragment(RouterHub.ROUTER_INFO_FRAGMENT)
        if (home != null) {
            mFragments.add(home)
            items[FRAGMENT_ID_INFO] = LottieNavItemView(
                this, FRAGMENT_ID_INFO, getString(
                    R.string.app_menu_info
                )
                , "icon_tabbar_lottery.json"
                , R.mipmap.ic_qiushi_normal
                , selectedIconId = R.mipmap.ic_qiushi_select
            )
        }
    }

    private fun loadDynamic() {
        val dynamic = RouteHelper.obtainARouterFragment(RouterHub.ROUTER_DYNAMIC_FRAGMENT)
        if (dynamic != null) {
            mFragments.add(dynamic)
            items[FRAGMENT_ID_DYNAMIC] = LottieNavItemView(
                this, FRAGMENT_ID_DYNAMIC, getString(
                    R.string.app_menu_dynamic
                )
                , "icon_tabbar_lottery.json"
                , R.mipmap.ic_qiuyoucircle_normal
                , selectedIconId = R.mipmap.ic_qiuyoucircle_select
            )
        }
    }


    private fun loadLive() {
        val live = RouteHelper.obtainARouterFragment(RouterHub.ROUTER_LIVE_INDEX_FRAGMENT)
        if (live != null) {
            mFragments.add(live)
            items[FRAGMENT_ID_LIVE] = LottieNavItemView(
                this, FRAGMENT_ID_LIVE, getString(R.string.app_menu_live)
                , "icon_tabbar_lottery.json"
                , R.mipmap.ic_live_normal
                , selectedIconId = R.mipmap.ic_live_select
            )
        }
    }

    private fun loadScrip() {
        val scrip = RouteHelper.obtainARouterFragment(RouterHub.ROUTER_SCRIP_FRAGMENT)
        if (scrip != null) {
            mFragments.add(scrip)
            items[FRAGMENT_ID_SCRIP] = LottieNavItemView(
                this, FRAGMENT_ID_SCRIP, getString(
                    R.string.app_menu_scrip
                )
                , "icon_tabbar_lottery.json"
                , R.mipmap.ic_message_normal
                , selectedIconId = R.mipmap.ic_message_select
            )
        }
    }

    private fun loadMe() {
        val me = RouteHelper.obtainARouterFragment(RouterHub.ROUTER_ME_FRAGMENT)
        if (me != null) {
            mFragments.add(me)
            items[FRAGMENT_ID_ME] = LottieNavItemView(
                this, FRAGMENT_ID_ME, getString(
                    R.string.app_menu_me
                )
                , "icon_tabbar_lottery.json"
                , R.mipmap.ic_mine_normal
                , selectedIconId = R.mipmap.ic_mine_select
            )
        }
    }
}
package com.allen.base.widget.btm_nav_view

interface BtmNavViewListener {

    /**  点击之前 预判断条件  */
    fun onBtmNavViewPreClick(id: Int): Boolean{
        return true
    }
    /**   已经选中时的点击事件  */
    fun onBtmNavViewSameClick(id: Int){

    }
    /**   点击事件 */
    fun onBtmNavViewClick(id: Int, oldId: Int){

    }

}
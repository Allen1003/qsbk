package com.module.content.ui.info.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.allen.app.data.bean.UserInfo
import com.allen.base.base.basic.extras.loadCircle
import com.allen.base.utils.ViewUtils.setText
import com.module.content.R
import kotlinx.android.synthetic.main.info_layout_user.view.*

/**
 * author : Allen
 * date   : 2020/08/15
 * time   : 15:57
 * desc   :
 */
class InfoUserView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {
    init {
        LayoutInflater.from(context).inflate(R.layout.info_layout_user, this)
    }

    fun setData(user: UserInfo?) {
        setText(tvUserName, user?.login)
        ivUserAvatar.loadCircle(user?.medium)
    }
}
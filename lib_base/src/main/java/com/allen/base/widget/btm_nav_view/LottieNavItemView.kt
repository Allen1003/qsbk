package com.allen.base.widget.btm_nav_view

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.widget.ImageView
import android.widget.TextView
import com.airbnb.lottie.LottieAnimationView
import com.allen.base.R

class LottieNavItemView : BtmNavItemView {

    var text: String = ""
    var json: String? = ""

    var ltItem: LottieAnimationView
    var ivItem: ImageView
    var tvText: TextView

    var unSelectedIconId: Int
    var selectedIconId: Int

    var refreshAnimationIconId: Int
    var refreshAnimationText: String

    var isAnimationMode = false

    constructor(
        context: Context,
        itemId: Int,
        text: String,
        json: String?,
        unSelectedIconId: Int
        ,
        selectedIconId: Int = 0,
        refreshAnimationIconId: Int = 0,
        refreshAnimationText: String = "刷新",
        enableSameClickAnimation: Boolean = false
    ) : super(context) {
        this.text = text
        this.json = json
        this.itemId = itemId
        this.unSelectedIconId = unSelectedIconId
        this.selectedIconId = selectedIconId
        this.refreshAnimationIconId = refreshAnimationIconId
        this.refreshAnimationText = refreshAnimationText
        this.enableSameClickAnimation = enableSameClickAnimation
        val view = LayoutInflater.from(context)
            .inflate(R.layout.base_layout_main_btm_nav_item_lottie, this)
        ltItem = view.findViewById(R.id.ltItem)
        ivItem = view.findViewById(R.id.ivItem)
        tvText = view.findViewById(R.id.tvText)

        if (json.isNullOrEmpty()) {
            isAnimationMode = false
        } else {
            isAnimationMode = true
            ltItem.setAnimation(json)
            ltItem.repeatCount = 0
        }

        ivItem.visibility = View.VISIBLE
        ivItem.setImageDrawable(context.resources.getDrawable(unSelectedIconId))
        tvText.text = text
        tvText.isSelected = false

    }

    override fun isCanClick(): Boolean {
        return !(ltItem.isAnimating || isSameClickAnimation)
    }

    override fun setItemSelect(isSelected: Boolean) {
        super.setSelected(isSelected)

        if (isSelected) {
            ivItem.setImageDrawable(context.resources.getDrawable(selectedIconId))
            if (isAnimationMode) {
                ivItem.visibility = View.GONE
                ltItem.visibility = View.VISIBLE
                ltItem.playAnimation()
            } else {
                ivItem.visibility = View.VISIBLE
            }
            return
        }

        ivItem.setImageDrawable(context.resources.getDrawable(unSelectedIconId))
        ltItem.visibility = View.GONE
        ivItem.visibility = View.VISIBLE

        if (isSameClickAnimation) {
            endSameClickAnimation()
        }
    }

    private var objectAnimator: ObjectAnimator? = null
    override fun startSameClickAnimation() {
        super.startSameClickAnimation()

        ivItem.setImageDrawable(context.resources.getDrawable(refreshAnimationIconId))
        tvText.text = refreshAnimationText
        ltItem.visibility = View.GONE
        ivItem.visibility = View.VISIBLE

        if (null == objectAnimator) {
            objectAnimator = ObjectAnimator.ofFloat(ivItem, "rotation", 360f)
            objectAnimator!!.interpolator = AccelerateInterpolator()
            objectAnimator!!.repeatCount = ValueAnimator.INFINITE
        }
        objectAnimator?.start()
    }

    override fun endSameClickAnimation() {
        if (!isSameClickAnimation)
            return
        super.endSameClickAnimation()
        objectAnimator?.end()
        ivItem.visibility = View.VISIBLE
        ivItem.setImageDrawable(context.resources.getDrawable(if (isSelected) selectedIconId else unSelectedIconId))
        tvText.text = text
    }

}
package com.allen.app.data.bean

import com.allen.base.data.ListCommon

/**
 * author : Allen
 * date   : 2020/08/13
 * time   : 16:32
 * desc   :
 */
class Info : ListCommon<InfoBean>() {
    override var items: List<InfoBean>?
        get() = super.items
        set(value) {}
}

data class InfoBean(
    val tag: String,
    val high_url: String,
    val pic_url: String,
    val hot_comment: HotComment?,
    val votes: Votes?,
    val pic_size: List<Int>,
    val share_url: String,
    val content: String,
    val state: String,
    val share_count: Int,
    val type: String,
    val format: String,
    val comments_count: Int,
    val allow_comment: Boolean,
    val low_url: String,
    val origin_url: String,
    val user: UserInfo?,
    val loop: Int
)

//神评
data class HotComment(
    val like_count: Int,
    val content: String,
    val user: UserInfo?,
    val hot_comment_type: Int
)

//用户信息
data class UserInfo(
    val uid: Int,
    val medium: String,
    val thumb: String,
    val gender: String,
    val age: Int,
    val state: String,
    val astrology: String,
    val login: String,
    val titles: List<Titles>?
)

data class Titles(
    val cmd_desc: String,
    val remark: String,
    val deeplink: String,
    val cmd: Int,
    val icon: String
)

data class Votes(
    val down: Int,
    val up: Int
)
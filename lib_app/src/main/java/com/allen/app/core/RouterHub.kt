package com.allen.app.core

interface RouterHub {
    companion object {
        /**
         * 宿主 App 组件
         */
        private const val APP = "/app"

        /**
         * 小说组件
         */
        private const val BOOK = "/book"

        /**
         * ----------------------------------
         */
        //书架
        const val BOOK_SHELF = "$BOOK/book_shelf "
        //发现
        const val BOOK_FIND = "$BOOK/book_find"
        //我的
        const val BOOK_ME = "$BOOK/book_me"
        //搜索
        const val BOOK_SEARCH = "$BOOK/SearchActivity"

        //书本详情
        const val BOOK_DETAILS = "$BOOK/BookDetailsActivity "
        //阅读界面
        const val READ_BOOK = "$BOOK/ReadBookActivity "
    }
}
package com.allen.base.utils


import androidx.lifecycle.AndroidViewModel
import java.lang.reflect.ParameterizedType

object ClassUtil {

    /**
     * 获取泛型ViewModel的class对象
     */
    fun <T> getViewModel(obj: Any): Class<T>? {
        val currentClass = obj.javaClass
        val tClass = getGenericClass<T>(currentClass, AndroidViewModel::class.java)
        return if (tClass == null || tClass == AndroidViewModel::class.java) {
            null
        } else tClass
    }

    private fun <T> getGenericClass(klass: Class<*>, filterClass: Class<*>): Class<T>? {
        val type = klass.genericSuperclass
        if (type == null || type !is ParameterizedType) return null
        val types = type.actualTypeArguments
        for (t in types) {
            try {
                val tClass = t as Class<T>
                if (filterClass.isAssignableFrom(tClass)) {
                    return tClass
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        return null
    }
}

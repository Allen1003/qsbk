package com.allen.base.base.basic.extras

import androidx.lifecycle.MutableLiveData

/**
 *  判断内容是否为空
 */
fun MutableLiveData<*>.valueIsNull() = this.value == null


/**
 * 简化MutableLiveData<Int> 加法
 */
infix fun MutableLiveData<Int>.plus(x:Int) : MutableLiveData<Int>{
    if(!this.valueIsNull()){
       postValue(value!!.plus(x))
    }
    return this
}

/**
 * 简化MutableLiveData<Int> 减法
 */
infix fun MutableLiveData<Int>.sub(x:Int) : MutableLiveData<Int>{
    if(!this.valueIsNull()){
        postValue(value!!.minus(x))
    }
    return this
}

/**
 * 简化MutableLiveData<T> postValue
 */
infix fun <T:Any> MutableLiveData<T>.post(newValue:T){
    this.postValue(newValue)
}

infix fun <T:Any> MutableLiveData<T>.set(newValue:T){
    this.value = newValue
}

infix fun <T:Any>MutableLiveData<T>.sameAs( equals : T) : Boolean{
    return if(value == null){
        false
    }else{
        value!!  ==  equals
    }
}


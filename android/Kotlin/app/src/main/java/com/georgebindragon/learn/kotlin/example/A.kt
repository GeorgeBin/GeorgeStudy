package com.georgebindragon.learn.kotlin.example

/**
 * author：
 *
 * description：
 * action：
 *
 * modification：
 */


class A {

    // 变量
    var int: Int = 1;
    var int2 = 2 // 可以自动推测变量类型
    val int3 = 3 // 只读
    private val test4 = "hello" // private 修饰，则不自动生成 getter、setter

    @Override
    fun test() {

        var color = 1
        color = 2

    }
}
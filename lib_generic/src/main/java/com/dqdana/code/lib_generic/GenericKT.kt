package com.dqdana.code.lib_generic

import com.dqdana.code.lib_generic.GenericKT.GenericWildcard.GenericErasure.Child
import com.dqdana.code.lib_generic.GenericKT.GenericWildcard.GenericErasure.People
import java.util.*

class GenericKT {

    class GenericClass<T>(var content: T) {

        interface GenericInterface<T> {
            fun todo(t: T)
        }
    }

    class GenericMethod {

        /**
         * kotlin 中 * = Any?
         */
        fun union(s1: Set<*>, s2: Set<*>): Set<*> {
            val result = HashSet(s1)
            result.addAll(s2)
            return result
        }

        fun <E> union2(s1: Set<E>, s2: Set<E>): Set<E> {
            val result = HashSet(s1)
            result.addAll(s2)
            return result
        }
    }

    class Book : Comparable<Any> {
        override fun compareTo(other: Any): Int {
            return 0
        }
    }

    class ChildBook {
    }

    class GenericWildcard {
        private fun swap(list: MutableList<Any>, i: Int, j: Int) {
            val oI = list[i]
            val oJ = list[j]
            list[i] = oJ
            list[j] = oI
        }

        private fun swapUnlimited(list: MutableList<*>, i: Int, j: Int) {
            swapWrap(list, i, j)
        }

        private fun <E> swapWrap(list: MutableList<E>, i: Int, j: Int) {
            val temp = list.set(j, list[i])
            list[i] = temp
        }

        private fun <K : Book, V : ChildBook> compare(arg1: K, arg2: V): V? {
            return if (arg1.compareTo(arg2) == 0) {
                arg2
            } else {
                null
            }
        }

        private fun testCompare() {
            val s1 = "str"
            val s2 = "str"
            // compare(s1, s2);
            val b = Book()
            val cb = ChildBook()
            // compare(cb, b);
            val result = compare(b, cb)

        }

        private fun <E> add(dst: MutableList<in E>, src: List<E>) {
            // src.addAll(dst);
            dst.addAll(src)
        }

        private fun <E : Comparable<E>> max(e1: List<E>?): E? {
            if (e1 == null) {
                return null
            }
            //迭代器返回的元素属于 E 的某个子类型
            val iterator = e1.iterator()
            var result: E = iterator.next()
            while (iterator.hasNext()) {
                val next = iterator.next()
                if (next.compareTo(result) > 0) {
                    result = next
                }
            }
            return result
        }

        /**
         * TODO - DQ - 2019/2/13 : 以下三个概念, 暂时不太懂, 需要后续理解
         * 边界, 协变, 逆变
         */
        class GenericErasure {
            interface Game {
                fun play()
            }

            interface Program {
                fun code()
            }

            class People<T>(private val mPeople: T) where T : Program, T : Game {
                fun habit() {
                    mPeople.code()
                    mPeople.play()
                }
            }

            class Child : Game, Program {
                override fun play() = println("play")
                override fun code() = println("code")
            }
        }
    }
}

fun main() {
    val c = Child()
    val p = People(c)
    p.habit()
}
package com.dqdana.code.lib_generic;

import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Generic: 泛型测试 - <T>
 * 作用:
 * 1 - 提高类型安全, 编译时期检查出 ClassCastException 异常
 * 2 - 消除强制类型转换, 去除 Object 提高可读性
 * 分类:
 * 1 - 泛型类
 * 2 - 泛型接口
 * 3 - 泛型方法
 * 通配符
 * 1 - <?> 无限制通配符
 * 2 - <? extends E> 声明上限, 都是 E 及其子类
 * 3 - <? super E> 声明下限, 都是 E 及其父类
 */
public class Generic {

}

/**
 * 泛型分类例子
 *
 * @param <T> 实例对象, 未知名时, 默认为 Object
 */
class GenericClass<T> {

    private T mContent;

    public GenericClass(T mContent) {
        this.mContent = mContent;
    }

    public T getContent() {
        return mContent;
    }

    public void setContent(T pContent) {
        this.mContent = pContent;
    }

    /**
     * 泛型接口使用场景: 策略模式的公共策略
     * eg: java.util.Comparator 比较对象是否相等的接口
     */
    public interface GenericInterface<T> {
        void todo(T t);
    }
}

/**
 * 不在泛型类内部的泛型方法
 */
class GenericMethod {

    /**
     * 传统方法 会有 unchecked ... raw type 的警告
     */
    @SuppressWarnings("unchecked")
    public Set union(Set s1, Set s2) {
        Set result = new HashSet(s1);
        result.addAll(s2);
        return result;
    }

    public <E> Set<E> union2(Set<E> s1, Set<E> s2) {
        Set<E> result = new HashSet<>(s1);
        result.addAll(s2);
        return result;
    }
}

class GenericWildcard {

    /**
     * 要使用泛型, 但是不确定或者不关心实际要操作的类型, 可以使用无限制通配符（尖括号里一个问号, 即 <?> ）, 表示可以持有任何类型
     * <p>
     * 但是在下面这种情况, 会提示编译错误,
     * 原因是, 假如 list = List<String>, list.set(index, Object), 这样是不合法的
     * <p>
     * 此时则需要, 带类型参数的泛型方法
     */
    // private void swap(List<?> list, int i, int j) {
    private void swap(List<Object> list, int i, int j) {
        Object oI = list.get(i);
        Object oJ = list.get(j);
        list.set(i, oJ);
        list.set(j, oI);
    }

    private void swapUnlimited(List<?> list, int i, int j) {
        swapWrap(list, i, j);
    }

    private <E> void swapWrap(List<E> list, int i, int j) {
        E temp = list.set(j, list.get(i));
        list.set(i, temp);
    }


    /**
     * 上界通配符 < ? extends E>
     * <p>
     * 用于灵活读取, 使得方法可以读取 E 或 E 的任意子类型的容器对象。
     * <p>
     * 即, 生产者, 返回的东西 需要有一个上限
     */
    class Book implements Comparable {
        @Override
        public int compareTo(@NotNull Object o) {
            return 0;
        }
    }

    class ChildBook {
    }

    private <K extends Book, V extends ChildBook> V compare(K arg1, V arg2) {
        if (arg1.compareTo(arg2) == 0) {
            return arg2;
        } else {
            return null;
        }
    }

    private void testCompare() {
        String s1 = "str";
        String s2 = "str";
        // compare(s1, s2);
        Book b = new Book();
        ChildBook cb = new ChildBook();
        // compare(cb, b);
        ChildBook result = compare(b, cb);

    }


    /**
     * 下界通配符 < ? extends E>
     * <p>
     * < ? super E> 用于灵活写入或比较, 使得对象可以写入父类型的容器, 使得父类型的比较方法可以应用于子类对象。
     * 即, 消费者, 操作 E, 需要容器足够大, 容器为父类即可满足
     * <p>
     * dst ≥ src  : 边界范围
     */
    private <E> void add(List<? super E> dst, List<E> src) {
        // src.addAll(dst);
        dst.addAll(src);
    }


    /**
     * 综合说明:
     * 代码中的类型参数 E 的范围是 <E extends Comparable<? super E>>, 我们可以分步查看：
     * <p>
     * 1. 要进行比较, 所以 E 需要是可比较的类, 因此需要 extends Comparable<...>（注意这里不要和继承的 extends 搞混了, 不一样）
     * 2. Comparable< ? super E> 要对 E 进行比较, 即 E 的消费者, 所以需要用 super
     * 3. 而参数 List< ? extends E> 表示要操作的数据是 E 的子类的列表, 指定上限, 这样容器才够大
     */
    private <E extends Comparable<? super E>> E max(List<? extends E> e1) {
        if (e1 == null) {
            return null;
        }
        //迭代器返回的元素属于 E 的某个子类型
        Iterator<? extends E> iterator = e1.iterator();
        E result = iterator.next();
        while (iterator.hasNext()) {
            E next = iterator.next();
            if (next.compareTo(result) > 0) {
                result = next;
            }
        }
        return result;
    }
}
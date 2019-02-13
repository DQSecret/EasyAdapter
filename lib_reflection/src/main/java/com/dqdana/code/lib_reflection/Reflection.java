package com.dqdana.code.lib_reflection;

import java.lang.reflect.Modifier;
import java.util.Arrays;

/**
 * 反射知识点梳理
 */
public class Reflection {

    enum Sex {FEMALE, MALE}

    public static void main(String[] args) {
        Reflection r = new Reflection();
        // r.getSomeClass();
        // r.getSomeRelated();
        r.getModifier();
    }

    /**
     * 得到一个 Class 对象
     */
    private void getSomeClass() {
        // Object.getClass
        Class s = "DQ".getClass();
        System.out.println(s);

        Class e = Sex.FEMALE.getClass();
        System.out.println(e);

        byte[] bytes = new byte[2014];
        Class<? extends byte[]> b = bytes.getClass();
        System.out.println(b);

        // 类名 + .class
        Class i1 = Integer.class;
        Class i2 = int.class;
        Class iArr = int[][].class;
        String str = i1 + "\n" + i2 + "\n" + iArr;
        System.out.println(str);

        // Class.forName
        try {
            Class name = Class.forName("com.dqdana.code.lib_reflection.Reflection");
            System.out.println(name);
        } catch (ClassNotFoundException e1) {
            e1.printStackTrace();
        }

        // 静态属性 TYPE
        Class doubleWrapper = Double.TYPE;
        System.out.println(doubleWrapper);
    }

    /**
     * 根据 class 获取一些相关类
     */
    private void getSomeRelated() {
        Class c = Model.class;
        System.out.println("getSuperclass: " + c.getSuperclass());
        System.out.println("getClasses: " + Arrays.toString(c.getClasses()));
        System.out.println("getDeclaredClasses: " + Arrays.toString(c.getDeclaredClasses()));
        System.out.println("getDeclaringClass: " + c.getDeclaringClass());
    }

    /**
     * Class 修饰符
     */
    private void getModifier() {
        /*
            访问权限控制: public private protected
            抽象: abstract
            只能有一个实例: static
            不允许修改: final
            线程同步: synchronized
            原生函数: native
            严格浮点精度: strictfp
            **
            Interface 不能是 final 的
            enum 不能是 abstract 的
            **
            详见 java.lang.reflect.Modifier
         */
        // java.lang.reflect.Modifier
        String result = Modifier.toString(Modifier.PUBLIC);
        System.out.println(result);
    }

    /**
     * 获得类中成员
     * java.lang.reflect.Member 是一个接口，代表 Class 的成员，每个成员都有类型，分为是否从父类继承，还有是否可以直接访问
     * 主要使用以下三个实现类
     */
    private void getMember() {

    }
}

class Model {

    enum Sex {FEMALE, MALE}

    private class Sub {
    }

    private int id = 1;
    public String name = "model";

    private int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Model(int id) {
        this.id = id;
    }

    public Model(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
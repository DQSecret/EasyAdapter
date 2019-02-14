package com.dqdana.code.lib_reflection;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.Arrays;

/**
 * 反射知识点梳理
 */
public class Reflection {

    private Model obj;

    enum Sex {FEMALE, MALE}

    public static void main(String[] args) {
        Reflection r = new Reflection();
        // r.getSomeClass();
        // r.getSomeRelated();
        // r.getModifier();
        // r.getMember();
        // r.getMethod();
        r.getInvoke();
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
     * || - java.lang.reflect.Constructor：表示该 Class 的构造函数
     * || - java.lang.reflect.Field：表示该 Class 的成员变量
     * || - java.lang.reflect.Method：表示该 Class 的成员方法
     */
    @SuppressWarnings({"unchecked", "JavaReflectionMemberAccess"})
    private void getMember() {
        // Class 提供的方法
        try {
            Class c = Model.class;
            // 获取构造函数
            c.getDeclaredConstructor(); // 只要声明了就能获取到, 无论 priv/pub
            c.getConstructor(); // 只能获取到 public
            c.getDeclaredConstructors(); // 如上, 获取到所有构造器, 返回列表

            // 获取成员变量, 同上
            c.getDeclaredField("id");
            Field[] fields = c.getDeclaredFields();
            System.out.println(Arrays.toString(fields));

            // 获取成员方法
            c.getDeclaredMethod("getName", c);
            Method[] methods = c.getDeclaredMethods();
            System.out.println(Arrays.toString(methods));
        } catch (NoSuchMethodException | NoSuchFieldException e) {
            e.printStackTrace();
        }

        // 获取变量的类型 及 修饰符
        try {
            Class<?> c = Class.forName(Model.class.getName());
            Field[] fields = c.getDeclaredFields();
            for (Field field : fields) {
                String string = "Field: " + field.getName() + "\n"
                        + "Type: " + field.getType() + "\n"
                        + "GenericType: " + field.getGenericType() + "\n"
                        + "Modifier: " + Modifier.toString(field.getModifiers());
                System.out.println(string);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        // 获取和修改变量的值
        try {
            Model model = new Model();
            Class c = model.getClass();

            Field id = c.getDeclaredField("id");
            Field index = c.getDeclaredField("index");
            Field name = c.getDeclaredField("name");
            System.out.println(id.getName() + " old: " + model.getId());
            System.out.println(index.getName() + " old: " + model.index);
            System.out.println(name.getName() + " old: " + model.name);

            // 私有属性 or final, 需要设置 无障碍访问
            id.setAccessible(true);
            id.setInt(model, 1234);
            // index.setInt(model, 1025); // 反射, 不会自动拆装箱
            index.set(model, 1025);
            name.set(model, "name");
            System.out.println(id.getName() + " new: " + model.getId());
            System.out.println(index.getName() + " new: " + model.index);
            System.out.println(name.getName() + " new: " + model.name);

        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * 关于方法的反射, 单独写一个测试方法
     */
    private void getMethod() {
        Model model = new Model();
        Class cls = Model.class;
        String pCanonicalName = cls.getCanonicalName(); // 获取类的完整路径
        System.out.println(pCanonicalName);

        Method[] pDecMethods = cls.getDeclaredMethods();
        for (Method method : pDecMethods) {
            System.out.println("method.getName(): " + method.getName()); // 方法名
            System.out.println("method.toGenericString(): " + method.toGenericString()); // 完整的方法信息
            System.out.println("method.getModifiers(): " + Modifier.toString(method.getModifiers())); // 修饰符

            System.out.println("method.getReturnType(): " + method.getReturnType()); // 返回值
            System.out.println("method.getGenericReturnType(): " + method.getGenericReturnType()); // 完整返回值

            // 参数类型
            Class<?>[] parameterTypes = method.getParameterTypes();
            Type[] genericParameterTypes = method.getGenericParameterTypes();
            for (int i = 0; i < parameterTypes.length; i++) {
                System.out.println("    method.getParameterTypes.it: " + parameterTypes[i]);
                System.out.println("    method.getGenericParameterTypes.it: " + genericParameterTypes[i]);
            }

            // 异常和注解, 同上, 此处省略

            // 方法的修饰符 省略

            // 方法的类型
            boolean isSyn = method.isSynthetic(); // 用于维持 内部类外部类, private 变量 的可见性
            boolean isVar = method.isVarArgs(); // 可变参数
            boolean isBri = method.isBridge(); // 桥接, object -> string 类似于适配器模式, 用于泛型被擦除时, 恢复

            System.out.println();
        }
    }

    /**
     * 终于到, 最常用的反射调用方法了
     * eg: java.lang.reflect.Method.invoke()
     */
    private void getInvoke() {
        try {
            Class cls = Class.forName("com.dqdana.code.lib_reflection.Model");
            Model obj = (Model) cls.newInstance();

            Method[] declaredMethods = cls.getDeclaredMethods();

            // 单独获取一个方法, 需要方法名
            try {
                @SuppressWarnings("unchecked") Method m = cls.getDeclaredMethod("getId");
                Object invokeResult = m.invoke(obj);
                System.out.println("getId: " + invokeResult.toString());
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }

            // 获取所有方法
            for (Method declaredMethod : declaredMethods) {
                // 只为了获取 私有方法, 共有的用不着反射
                String mod = Modifier.toString(declaredMethod.getModifiers());
                if (mod.contains("private")) {

                    // 调用一个方法, 需要知道 方法名 | 返回值
                    String methodName = declaredMethod.getName();
                    Type returnType = declaredMethod.getGenericReturnType();
                    int modifiers = declaredMethod.getModifiers();

                    declaredMethod.setAccessible(true);

                    Object invokeResult;
                    switch (methodName) {
                        case "getUuid":
                            invokeResult = declaredMethod.invoke(obj);
                            break;
                        case "getUuidTemp":
                            invokeResult = declaredMethod.invoke(obj, "DQ self invoke");
                            break;
                        case "GET_UUID":
                            // 修改属性
                            Field field = cls.getDeclaredField("UUID");
                            field.setAccessible(true);
                            field.set(obj, "DQ self mod to Vera");
                            // 反射调用, static 方法反射时, obj 参数可以为 null
                            invokeResult = declaredMethod.invoke(null);
                            break;
                        default:
                            invokeResult = "null";
                            break;
                    }
                    String invokeResultStr = "Invoke of " + methodName + ", return " + invokeResult.toString();
                    System.out.println(invokeResultStr);
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchFieldException e) {
            e.printStackTrace();
        }
    }
}

class Model<T> {

    enum Sex {FEMALE, MALE}

    private class Sub {
    }

    private static String UUID = "UUID";
    private final int id = 1;
    public String name = "model";
    Integer index = 1006;
    T type; // 泛型 T 反射获取时, 会被擦拭成 Object

    public Model() {
    }

    public String getName() {
        return name;
    }

    public Model(String name) {
        this.name = name;
    }

    private static String GET_UUID() {
        return UUID;
    }

    int getId() {
        return id;
    }

    private String getUuid() {
        return name;
    }

    private String getUuidTemp(String uuid) {
        return uuid;
    }

    public void setName(String name) {
        this.name = name;
    }
}
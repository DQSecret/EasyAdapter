package support.ui.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.ViewGroup;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 根据 Value.Class 和 ViewHolder.Class 反射生成对应的类
 */
public class BaseEasyViewHolderFactory {

    private Context context;

    private List<Class> valueClasses = new ArrayList<>();
    private Map<Class, Class<? extends EasyViewHolder>> viewHolderClasses = new HashMap<>();


    public BaseEasyViewHolderFactory(Context context) {
        this.context = context;
    }

    /**
     * 保存数据
     * Value-ViewHolder 的对应关系
     */
    public void save(@NonNull Class valueClass, @NonNull Class<? extends EasyViewHolder> viewHolderClass) {
        valueClasses.add(valueClass);
        viewHolderClasses.put(valueClass, viewHolderClass);
    }

    /**
     * 根据具体的 model 值, 获取对应的 ViewType, 用于在 Adapter 中区分多类型 View
     */
    public int getItemViewTypeByValue(Object obj) {
        return valueClasses.indexOf(obj.getClass());
    }

    /**
     * 根据 viewType 生成对应的 ViewHolder
     */
    public EasyViewHolder create(ViewGroup parent, int viewType) {
        Class valueCls = valueClasses.get(viewType);

        // 两个参数的构造器, 每个字类都会实现
        try {
            Class<? extends EasyViewHolder> holderCls = viewHolderClasses.get(valueCls); // 可能为空
            if (holderCls != null) {
                Constructor<? extends EasyViewHolder> constructor = holderCls.getDeclaredConstructor(Context.class, ViewGroup.class);
                return constructor.newInstance(context, parent);
            } else {
                throw new RuntimeException("Unable to create ViewHolder for " + valueCls + " cause holderCls is null.");
            }
        } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
            e.printStackTrace();
            throw new RuntimeException("Unable to create ViewHolder for " + valueCls + ". " + e.getCause().getMessage(), e);
        }
    }
}

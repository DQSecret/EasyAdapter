package support.ui.adapters;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class EasyViewHolder<V> extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

    /**
     * 以下两个构造器, 都是通用的, 所以可以放在父类中
     * 预留一个 (Context context, ViewGroup parent)两个参数的构造器
     * 由子类去实现, 其中调用父类的本方法
     * 同时, 反射生成 Holder 的时候, 使用子类的构造器
     */
    public EasyViewHolder(Context context, ViewGroup parent, @LayoutRes int layoutId) {
        this(LayoutInflater.from(context).inflate(layoutId, parent));
    }

    private EasyViewHolder(@NonNull View itemView) {
        super(itemView);
        bindListeners();
    }

    /**
     * 子类自己去实现, bindTo 的具体逻辑和 UI显示
     *
     * @param position : 下标
     * @param value    : 具体的 model 类型
     */
    abstract void bindTo(int position, V value);

    /**
     * 可以在这里添加通用事件: 单击|长按事件
     * 按需自定义
     */
    private void bindListeners() {
        // itemView.setOnClickListener(this);
        // itemView.setOnLongClickListener(this);
    }

    @Override
    public void onClick(View v) {
    }

    @Override
    public boolean onLongClick(View v) {
        return false;
    }
}

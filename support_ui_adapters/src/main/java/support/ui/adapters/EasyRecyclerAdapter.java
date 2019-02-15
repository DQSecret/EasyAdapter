package support.ui.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class EasyRecyclerAdapter extends RecyclerView.Adapter<EasyViewHolder> {

    private List<Object> dataList = new ArrayList<>();
    private BaseEasyViewHolderFactory viewHolderFactory;

    /**
     * 单一类型类型
     */
    public EasyRecyclerAdapter(Context context, Class valueClass, Class<? extends EasyViewHolder> EasyViewHolderClass) {
        this.viewHolderFactory = new BaseEasyViewHolderFactory(context);
        bind(valueClass, EasyViewHolderClass);
    }

    /**
     * 适用于多类型 ViewHolder
     */
    public EasyRecyclerAdapter(BaseEasyViewHolderFactory viewHolderFactory, Class valueClass, Class<? extends EasyViewHolder> EasyViewHolderClass) {
        this.viewHolderFactory = viewHolderFactory;
        bind(valueClass, EasyViewHolderClass);
    }

    /**
     * 在 viewHolderFactory 保存 Value-ViewHolder 的对应关系
     */
    private void bind(Class valueClass, Class<? extends EasyViewHolder> easyViewHolderClass) {
        this.viewHolderFactory.save(valueClass, easyViewHolderClass);
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return viewHolderFactory.getItemViewTypeByValue(dataList.get(position));
    }

    @NonNull
    @Override
    public EasyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        EasyViewHolder holder = viewHolderFactory.create(viewGroup, i);
        bindListeners(holder);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull EasyViewHolder easyViewHolder, int i) {
        //noinspection unchecked
        easyViewHolder.bindTo(i, dataList.get(i));
    }

    /**
     * 可以在这里添加通用事件: 单击|长按事件
     */
    private void bindListeners(EasyViewHolder holder) {
//        if (holder != null) {
//            holder.setItemClickListener(itemClickListener);
//            holder.setLongClickListener(longClickListener);
//        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    /// 以下方法为对 dataList 操作的通用方法
    ////////////////////////////////////////////////////////////////////////////////////////////////

    public int getIndex(Object item) {
        return dataList.indexOf(item);
    }

    public void add(Object obj) {
        this.dataList.add(obj);
        notifyItemInserted(getIndex(obj));
    }

    public void add(Object obj, int position) {
        this.dataList.add(position, obj);
        notifyItemInserted(position);
    }

    public void addAll(List<?> objects) {
        this.dataList.clear();
        this.dataList.addAll(objects);
        notifyDataSetChanged();
    }

    public void appendAll(List<?> objects) {
        if (objects == null) throw new IllegalArgumentException("objects can not be null");

        List<?> toAppends = filter(objects);
        final int toAppendSize = toAppends.size();
        if (toAppendSize <= 0) return;

        int prevSize = this.dataList.size();
        List<Object> data = new ArrayList<>(prevSize + toAppendSize);
        data.addAll(dataList);
        data.addAll(toAppends);
        dataList = data;
        notifyItemRangeInserted(prevSize, toAppends.size());
    }

    /**
     * 去掉重复数据
     */
    private List<?> filter(List<?> data) {
        List<Object> returnData = new ArrayList<>();
        List<?> localDataList = this.dataList;
        for (Object o : data) {
            if (!localDataList.contains(o)) {
                returnData.add(o);
            }
        }
        return returnData;
    }

    public boolean update(Object data, int position) {
        Object oldData = dataList.set(position, data);
        if (oldData != null) {
            notifyItemChanged(position);
        }
        return oldData != null;
    }

    public boolean remove(Object data) {
        return dataList.contains(data) && remove(getIndex(data));
    }

    public boolean remove(int position) {
        boolean validIndex = isValidIndex(position);
        if (validIndex) {
            dataList.remove(position);
            notifyItemRemoved(position);
        }
        return validIndex;
    }

    private boolean isValidIndex(int position) {
        return position >= 0 && position < getItemCount();
    }

    public void clear() {
        dataList.clear();
        notifyDataSetChanged();
    }
}
package com.dqdana.code.easyadapter.userinfo;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dqdana.code.easyadapter.R;

import support.ui.adapters.EasyViewHolder;

public class UserInfoViewHolder extends EasyViewHolder<UserInfo> {

    public UserInfoViewHolder(Context context, ViewGroup parent) {
        super(context, parent, R.layout.view_holder_user_info);
    }

    @Override
    protected void bindTo(int position, UserInfo value) {
        TextView tvUserName = itemView.findViewById(R.id.tv_user_name);
        tvUserName.setText(value.getName());
        TextView tvUserAge = itemView.findViewById(R.id.tv_user_age);
        tvUserAge.setText(String.valueOf(value.getAge()));
    }
}

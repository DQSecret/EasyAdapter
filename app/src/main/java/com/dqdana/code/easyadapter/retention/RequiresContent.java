package com.dqdana.code.easyadapter.retention;

import android.view.View;

import com.dqdana.code.easyadapter.common.widget.DefaultEmptyView;
import com.dqdana.code.easyadapter.common.widget.DefaultErrorView;
import com.dqdana.code.easyadapter.common.widget.DefaultLoadView;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Inherited // 可以被子类继承
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface RequiresContent {

    // default 默认参数, 使用时可以不写任何参数, 加快速度

    Class<? extends View> loadView() default DefaultLoadView.class;

    Class<? extends View> emptyView() default DefaultEmptyView.class;

    Class<? extends View> errorView() default DefaultErrorView.class;
}
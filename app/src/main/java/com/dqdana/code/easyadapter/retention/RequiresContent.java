package com.dqdana.code.easyadapter.retention;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Inherited // 可以被子类继承
@Retention(RetentionPolicy.RUNTIME)
public @interface RequiresContent {

//    Class<? extends View> loadView() default DefaultLoadView.class;
//
//    Class<? extends View> emptyView() default DefaultEmptyView.class;
//
//    Class<? extends View> errorView() default DefaultErrorView.class;
}
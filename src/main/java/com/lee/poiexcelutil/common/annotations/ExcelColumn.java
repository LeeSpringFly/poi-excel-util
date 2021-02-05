package com.lee.poiexcelutil.common.annotations;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Documented
public @interface ExcelColumn {

    String name() default "";   // 字段名称

    String description() default "";  // 字段描述

    boolean notNull() default false;  // 是否可以 null 值

    int position() default -1;
}

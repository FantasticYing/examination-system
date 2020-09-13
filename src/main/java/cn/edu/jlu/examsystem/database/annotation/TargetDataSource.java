package cn.edu.jlu.examsystem.database.annotation;

import java.lang.annotation.*;

/**
 * 指定数据源
 * @author WangZeying 2020/9/7 14:26
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface TargetDataSource {
    String value() default "";
}

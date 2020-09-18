package cn.edu.jlu.examsystem.database.aop;

import cn.edu.jlu.examsystem.database.annotation.TargetDataSource;
import cn.edu.jlu.examsystem.database.bean.DynamicDataSource;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * 处理TargetDataSource注解，动态切换数据源
 * @author WangZeying 2020/9/7 14:34
 */
@Slf4j
@Aspect
@Component
@Order(-1)
public class SwitchDataSourceAspect {

    @Around("@annotation(cn.edu.jlu.examsystem.database.annotation.TargetDataSource)")
    public Object switchDataSource(ProceedingJoinPoint point) throws Throwable {
        MethodSignature signature = (MethodSignature) point.getSignature();
        TargetDataSource dataSource = signature.getMethod().getAnnotation(TargetDataSource.class);
        String dataSourceKey = dataSource.value();
        DynamicDataSource.setDataSourceKey(dataSourceKey);

        log.debug("========>> 切换数据源 name=[{}]", dataSourceKey);

        try {
            return point.proceed();
        } finally {
            DynamicDataSource.clearDataSourceKey();
            log.debug("========>> 清除数据源 name=[{}]", dataSourceKey);
        }
    }
}

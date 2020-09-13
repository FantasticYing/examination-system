package cn.edu.jlu.examsystem.database.bean;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * 动态切换数据源
 * @author WangZeying 2020/9/7 13:25
 */
public class DynamicDataSource extends AbstractRoutingDataSource {

    private static final ThreadLocal<Object> DATASOURCE_KEY_HOLDER = new InheritableThreadLocal<>();

    @Override
    protected Object determineCurrentLookupKey() {
        return DATASOURCE_KEY_HOLDER.get();
    }

    public static void setDataSourceKey(Object key) {
        DATASOURCE_KEY_HOLDER.set(key);
    }

    public static void clearDataSourceKey() {
        DATASOURCE_KEY_HOLDER.remove();
    }

}

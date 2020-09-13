package cn.edu.jlu.examsystem.database.config;

import cn.edu.jlu.examsystem.database.bean.DynamicDataSource;
import com.zaxxer.hikari.HikariDataSource;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author WangZeying 2020/9/7 11:24
 */
@Data
@Configuration
@EnableAspectJAutoProxy
@ConfigurationProperties(prefix = "mysql")
@MapperScan(basePackageClasses = cn.edu.jlu.examsystem.database.mapper.PackageMarker.class)
public class MultiDataSourceConfig {

    private List<DataSourceProperties> datasource;

    @Getter
    @Setter
    public static class DataSourceProperties {
        private String name;
        private String jdbcUrl;
        private String username;
        private String password;
        private boolean defaultDataSource;
        private boolean readOnly;
    }

    @Bean
    public DataSource dataSource() {

        DynamicDataSource dynamicDataSource = new DynamicDataSource();
        dynamicDataSource.setLenientFallback(false);

        Map<Object, Object> nameToDataSource = new HashMap<>(datasource.size());

        boolean hasDefault = false;

        for (DataSourceProperties prop : datasource) {
            HikariDataSource dataSource = new HikariDataSource();

            dataSource.setJdbcUrl(prop.jdbcUrl);
            dataSource.setUsername(prop.username);
            dataSource.setPassword(prop.password);
            dataSource.setReadOnly(prop.readOnly);
            dataSource.setPoolName(prop.name);

            if (prop.defaultDataSource) {
                dynamicDataSource.setDefaultTargetDataSource(dataSource);
                hasDefault = true;
            }
            nameToDataSource.put(prop.name, dataSource);

        }

        if (!hasDefault){
            throw new RuntimeException("必须有一个默认数据源");
        }

        dynamicDataSource.setTargetDataSources(nameToDataSource);
        dynamicDataSource.afterPropertiesSet();
        return dynamicDataSource;
    }
}

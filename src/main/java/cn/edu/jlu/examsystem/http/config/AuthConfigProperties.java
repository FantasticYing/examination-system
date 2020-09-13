package cn.edu.jlu.examsystem.http.config;

import cn.edu.jlu.examsystem.biz.enums.RoleEnum;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;

/**
 * @author WangZeying 2020/9/6 15:06
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "application.auth")
public class AuthConfigProperties {
    private List<String> nonLoginPrefixes;
    private Map<RoleEnum, List<String>> roleToPrefixes;
    private Map<RoleEnum, String > secret;
}

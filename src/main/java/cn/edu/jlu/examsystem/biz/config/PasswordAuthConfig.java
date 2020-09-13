package cn.edu.jlu.examsystem.biz.config;

import cn.edu.jlu.examsystem.biz.bean.PasswordEncoder;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author WangZeying 2020/9/11 13:55
 */
@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "application.auth.password")
public class PasswordAuthConfig {

    private String salt;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return rawPassword -> DigestUtils.sha256Hex(rawPassword + salt);
    }
}

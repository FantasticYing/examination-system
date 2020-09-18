package cn.edu.jlu.examsystem.biz.service;

import cn.edu.jlu.examsystem.biz.bean.PasswordEncoder;
import cn.edu.jlu.examsystem.biz.domain.dto.request.AuthRequest;
import cn.edu.jlu.examsystem.biz.domain.dto.response.CoreUserInfo;
import cn.edu.jlu.examsystem.biz.excepiton.BaseException;
import cn.edu.jlu.examsystem.biz.redis.UserAuthRedisClient;
import cn.edu.jlu.examsystem.database.annotation.TargetDataSource;
import cn.edu.jlu.examsystem.database.entity.CoreUserEntity;
import cn.edu.jlu.examsystem.database.repository.UserRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import static cn.edu.jlu.examsystem.database.common.DatabaseConstants.EXAM_READ_ONLY;

/**
 * @author WangZeying 19:10
 */
@Service
public class AuthService {
    
    private final UserRepository userRepository;
    
    private final UserAuthRedisClient authRedisClient;
    
    private final PasswordEncoder passwordEncoder;
    
    public AuthService(UserRepository userRepository, RedisTemplate<String, Object> redisTemplate, UserAuthRedisClient authRedisClient, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.authRedisClient = authRedisClient;
        this.passwordEncoder = passwordEncoder;
    }
    
    @TargetDataSource(EXAM_READ_ONLY)
    public String createAuth(AuthRequest auth) {
        CoreUserEntity userEntity = null;
        if (!StringUtils.isBlank(auth.getUsername())) {
            userEntity =
                    userRepository.findByUsernameAndPasswordAndIsDel(auth.getUsername(), passwordEncoder.encoding(auth.getPassword()), (short) 0);
        } else if (!StringUtils.isBlank(auth.getEmail())) {
            userEntity =
                    userRepository.findByEmailAndPasswordAndIsDel(auth.getEmail(), passwordEncoder.encoding(auth.getPassword()), (short) 0);
        } else if (!StringUtils.isBlank(auth.getMobile())) {
            userEntity =
                    userRepository.findByMobileAndPasswordAndIsDel(auth.getMobile(), passwordEncoder.encoding(auth.getPassword()), (short) 0);
        }
        if (userEntity == null) {
            throw new BaseException(HttpStatus.UNAUTHORIZED.value(), "用户名或密码错误");
        }
        
        CoreUserInfo coreUserInfo = CoreUserInfo.builder()
                .id(userEntity.getId())
                .username(userEntity.getUsername())
                .nickname(userEntity.getNickname())
                .roleId(userEntity.getRoleId())
                .build();
        
        return authRedisClient.createAuthAndGetSessionId(coreUserInfo);
    }
    
    public CoreUserInfo getAuth(String sessionId) {
        CoreUserInfo userInfo = authRedisClient.getAuth(sessionId);
        if (userInfo == null) {
            throw new BaseException(HttpStatus.UNAUTHORIZED.value(), "请登录");
        }
        return userInfo;
    }
    
    public Boolean expireAuth(String sessionId) {
        return authRedisClient.expireAuth(sessionId);
    }
    
}

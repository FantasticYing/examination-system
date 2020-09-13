package cn.edu.jlu.examsystem.biz.service;

import cn.edu.jlu.examsystem.biz.bean.PasswordEncoder;
import cn.edu.jlu.examsystem.biz.domain.dto.request.AuthRequest;
import cn.edu.jlu.examsystem.biz.domain.dto.response.CoreUserInfo;
import cn.edu.jlu.examsystem.biz.redis.UserAuthRedisClient;
import cn.edu.jlu.examsystem.database.annotation.TargetDataSource;
import cn.edu.jlu.examsystem.database.entity.CoreUserEntity;
import cn.edu.jlu.examsystem.database.repository.UserRepository;
import org.springframework.data.redis.core.RedisTemplate;
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
        CoreUserEntity userEntity =
                userRepository.findByUsernameAndPasswordAndIsDel(auth.getUsername(), passwordEncoder.encoding(auth.getPassword()), (short) 0);
        if (userEntity == null) {
            return null;
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
        if (sessionId == null) {
            return null;
        }
        return authRedisClient.getAuthAndRefresh(sessionId);
    }

    public Boolean expireAuth(String sessionId) {
        return authRedisClient.expireAuth(sessionId);
    }

}

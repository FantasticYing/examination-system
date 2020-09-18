package cn.edu.jlu.examsystem.biz.redis;

import cn.edu.jlu.examsystem.biz.domain.dto.response.CoreUserInfo;
import org.apache.catalina.SessionIdGenerator;
import org.apache.catalina.util.StandardSessionIdGenerator;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Objects;

/**
 * @author WangZeying 2020/9/6 23:36
 */
@Component
public class UserAuthRedisClient {

    private final RedisTemplate<String, Object> redisTemplate;
    private final SessionIdGenerator sessionIdGenerator = new StandardSessionIdGenerator();

    private static final Duration expiredTime = Duration.ofDays(3);
    public static final String SESSION_KEY_PREFIX = RedisUtils.key(RedisConstants.APP_KEY_PREFIX, "session");

    public UserAuthRedisClient(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    private static String sessionKey(String sessionId) {
        return RedisUtils.key(SESSION_KEY_PREFIX, sessionId);
    }

    public String createAuthAndGetSessionId(CoreUserInfo userInfo) {

        String sessionId = sessionIdGenerator.generateSessionId();

        redisTemplate.opsForValue()
                .set(sessionKey(sessionId), userInfo, expiredTime);

        return sessionId;
    }

    public CoreUserInfo getAuth(String sessionId) {
        if (sessionId == null){
            return null;
        }
        String key = sessionKey(sessionId);
        return (CoreUserInfo) redisTemplate.opsForValue().get(key);
    }

    public CoreUserInfo getAuthAndRefresh(String sessionId) {
        if (sessionId == null){
            return null;
        }
        String key = sessionKey(sessionId);
        CoreUserInfo userInfo = (CoreUserInfo) redisTemplate.opsForValue().get(key);
        if (Objects.isNull(userInfo)) {
            redisTemplate.expire(key, Duration.ofDays(3));
        }
        return userInfo;
    }

    public boolean expireAuth(String sessionId) {
        if (sessionId == null){
            return false;
        }
        return redisTemplate.delete(sessionKey(sessionId));
    }

}

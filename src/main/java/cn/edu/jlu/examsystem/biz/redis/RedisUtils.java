package cn.edu.jlu.examsystem.biz.redis;

import static cn.edu.jlu.examsystem.biz.redis.RedisConstants.SEPARATOR;

/**
 * @author WangZeying 2020/9/6 23:51
 */
public class RedisUtils {
    private RedisUtils(){}

    public static String key(String... key) {
        return String.join(SEPARATOR, key);
    }
}

package cn.edu.jlu.examsystem.biz.bean;

/**
 * @author WangZeying 2020/9/11 13:56
 */
public interface PasswordEncoder {
    String encoding(String rawPassword);

    default String decoding(String encodedPassword) {
        throw new UnsupportedOperationException();
    }

    default boolean match(String raw, String encoded) {
        throw new UnsupportedOperationException();
    }
}

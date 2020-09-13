package cn.edu.jlu.examsystem.biz.excepiton;

import lombok.Getter;

/**
 * @author WangZeying 2020/9/6 22:07
 */
public class BaseException extends RuntimeException {

    @Getter
    private final int code;

    public BaseException(int code, String message) {
        super(message);
        this.code = code;
    }

    public BaseException(int code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

    public BaseException(int code, Throwable cause) {
        super(cause);
        this.code = code;
    }
}

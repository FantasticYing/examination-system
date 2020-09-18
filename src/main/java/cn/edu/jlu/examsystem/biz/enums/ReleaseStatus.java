package cn.edu.jlu.examsystem.biz.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * '0-未答卷，1-已开考，开始计时，2-已交卷，等待判卷，4-完成;
 *
 * @author WangZeying 2020/9/18 17:25
 */
@Getter
@AllArgsConstructor
public enum ReleaseStatus {
    STARTING((byte) 1, "已开考，开始计时"),
    SUBMITTED((byte) 2, "已交卷，等待判卷"),
    DONE((byte) 4, "完成"),
    ;

    private final byte id;
    private final String name;

    private static final Map<Byte, ReleaseStatus> MAP;

    static {
        MAP = Stream.of(ReleaseStatus.values())
                .collect(Collectors.toMap(ReleaseStatus::getId, Function.identity()));
    }

    public static ReleaseStatus fromId(byte id) {
        return MAP.get(id);
    }

}

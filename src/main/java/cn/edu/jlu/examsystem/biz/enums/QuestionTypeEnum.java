package cn.edu.jlu.examsystem.biz.enums;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * @author WangZeying 2020/9/7 20:51
 */
@Getter
public enum QuestionTypeEnum {

    CHOICE((short) 1, "单选"),
    MULTIPLE_ANSWER((short) 2, "多选"),
    TRUE_O_FALSE((short) 3, "判断"),
    FILL_IN((short) 4, "填空"),
    SHORT_ANSWER((short) 5, "简答");

    final short id;
    final String name;

    QuestionTypeEnum(short id, String name) {
        this.id = id;
        this.name = name;
    }

    private static final Map<Short, QuestionTypeEnum> ID_MAP = new HashMap<>(3);

    static {
        for (QuestionTypeEnum value : QuestionTypeEnum.values()) {
            ID_MAP.put(value.id, value);
        }
    }

    public static QuestionTypeEnum fromId(Short id) {
        return ID_MAP.get(id);
    }


    /**
     * 是不是客观题？
     *
     * @param id id
     * @return true->客观题
     */
    public static boolean isObjective(Short id) {
        return id != null &&
                (id.equals(CHOICE.id)
                        || id.equals(MULTIPLE_ANSWER.id)
                        || id.equals(TRUE_O_FALSE.id));
    }

}

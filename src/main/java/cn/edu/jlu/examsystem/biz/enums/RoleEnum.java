package cn.edu.jlu.examsystem.biz.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * @author WangZeying 2020/9/6 16:26
 */
public enum RoleEnum {
    ADMIN((short) 1, "管理员"),
    STUDENT((short) 2, "学生"),
    TEACHER((short) 3, "老师");

    private final short id;
    private final String name;

    RoleEnum(short id, String name) {
        this.id = id;
        this.name = name;
    }

    private static final Map<Short, RoleEnum> idMap = new HashMap<>(3);

    static {
        for (RoleEnum value : RoleEnum.values()) {
            idMap.put(value.id, value);
        }
    }

    public static RoleEnum fromId(short id) {
        RoleEnum roleEnum = idMap.get(id);
        if (roleEnum == null) {
            throw new RuntimeException("无效的角色id: "+id);
        }
        return roleEnum;
    }
}

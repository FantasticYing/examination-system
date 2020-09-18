package cn.edu.jlu.examsystem.biz.domain.dto.response;

import cn.edu.jlu.examsystem.common.util.ConvertUtils;
import cn.edu.jlu.examsystem.database.entity.CoreUserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.List;

/**
 * @author WangZeying 2020/9/6 2:33
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CoreUserInfo {
    private Long id;
    
    private String username;
    
    private String nickname;
    
    private Short roleId;
    
    public static CoreUserInfo from(CoreUserEntity entity) {
        return CoreUserInfo.builder()
                .id(entity.getId())
                .nickname(entity.getNickname())
                .roleId(entity.getRoleId())
                .username(entity.getUsername())
                .build();
    }
    
    public static List<CoreUserInfo> from(Collection<CoreUserEntity> entity) {
        return ConvertUtils.extractList(entity, CoreUserInfo::from);
    }
}

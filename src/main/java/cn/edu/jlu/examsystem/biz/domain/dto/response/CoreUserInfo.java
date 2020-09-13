package cn.edu.jlu.examsystem.biz.domain.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
}

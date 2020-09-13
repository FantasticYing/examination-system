package cn.edu.jlu.examsystem.biz.domain.dto.request;

import lombok.Data;

/**
 * @author WangZeying 2020/9/6 3:39
 */
@Data
public class AuthRequest {
    private String username;
    private String email;
    private String mobile;
    private String password;
}

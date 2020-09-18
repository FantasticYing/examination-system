package cn.edu.jlu.examsystem.biz.domain.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

/**
 * @author WangZeying 2020/9/7 0:42
 */
@Data
@NoArgsConstructor
public class UserRegisterRequest {
    
    @NotBlank(message = "用户名不可为空")
    @Size(min = 6, max = 32, message = "用户名长度6-32字符")
    private String username;
    
    @NotBlank(message = "真实姓名不可为空")
    @Size(max = 256, message = "用户名长度6-32字符")
    private String nickname;
    
    @Email
    @NotBlank(message = "邮箱不可为空")
    private String email;
    
    @NotBlank(message = "手机号不可为空")
    @Size(min = 11, max = 32, message = "手机号长度11-32字符")
    private String mobile;
    
    @NotBlank(message = "密码不可为空")
    @Size(min = 6, max = 32, message = "密码长度6-32字符")
    private String password;
    
    @Min(1)
    @Max(3)
    @NotNull
    private Short roleId;
    
    @NotBlank(message = "邀请码不可为空")
    private String secret;
    
}

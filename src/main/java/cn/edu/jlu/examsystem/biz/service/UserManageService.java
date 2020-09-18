package cn.edu.jlu.examsystem.biz.service;

import cn.edu.jlu.examsystem.biz.bean.PasswordEncoder;
import cn.edu.jlu.examsystem.biz.domain.dto.BatchGet;
import cn.edu.jlu.examsystem.biz.domain.dto.Pagination;
import cn.edu.jlu.examsystem.biz.domain.dto.request.UserQueryRequest;
import cn.edu.jlu.examsystem.biz.domain.dto.request.UserRegisterRequest;
import cn.edu.jlu.examsystem.biz.domain.dto.response.CoreUserInfo;
import cn.edu.jlu.examsystem.biz.enums.RoleEnum;
import cn.edu.jlu.examsystem.biz.excepiton.BaseException;
import cn.edu.jlu.examsystem.database.annotation.TargetDataSource;
import cn.edu.jlu.examsystem.database.entity.CoreUserEntity;
import cn.edu.jlu.examsystem.database.entity.UserValidationEntity;
import cn.edu.jlu.examsystem.database.mapper.CoreUserMapper;
import cn.edu.jlu.examsystem.database.repository.UserRepository;
import cn.edu.jlu.examsystem.database.repository.UserValidateRepository;
import cn.edu.jlu.examsystem.http.config.AuthConfigProperties;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

import static cn.edu.jlu.examsystem.database.common.DatabaseConstants.EXAM_READ_ONLY;
import static cn.edu.jlu.examsystem.database.common.DatabaseConstants.EXAM_READ_WRITE;

/**
 * @author WangZeying 2020/9/7 1:07
 */
@Service
public class UserManageService {
    
    private final Map<RoleEnum, String> roleAndSecret;
    
    private final UserRepository userRepository;
    
    private final UserValidateRepository validateRepository;
    
    private final PasswordEncoder passwordEncoder;
    
    private final CoreUserMapper coreUserMapper;
    
    public UserManageService(AuthConfigProperties properties, UserRepository userRepository, UserValidateRepository validateRepository, PasswordEncoder passwordEncoder, CoreUserMapper coreUserMapper) {
        roleAndSecret = properties.getSecret();
        this.userRepository = userRepository;
        this.validateRepository = validateRepository;
        this.passwordEncoder = passwordEncoder;
        this.coreUserMapper = coreUserMapper;
    }
    
    @Transactional(rollbackFor = Exception.class)
    @TargetDataSource(EXAM_READ_WRITE)
    public Long registerUser(UserRegisterRequest request) {
        RoleEnum roleEnum = RoleEnum.fromId(request.getRoleId());
        String secret = roleAndSecret.get(roleEnum);
        if (!StringUtils.equals(request.getSecret(), secret)) {
            throw new BaseException(HttpStatus.BAD_REQUEST.value(), "邀请码无效,请联系运维");
        }
        
        UserValidationEntity validation = validateRepository.checkUserInfoDup(request.getUsername(), request.getEmail(), request.getMobile());
        
        if (!validation.validated()) {
            String field;
            if (validation.isExistUsername()) {
                field = "用户名";
            } else if (validation.isExistEmail()) {
                field = "email";
            } else {
                field = "手机号";
            }
            throw new BaseException(HttpStatus.BAD_REQUEST.value(), field + "已使用");
        }
        
        CoreUserEntity userEntity =
                CoreUserEntity
                        .builder()
                        .username(request.getUsername())
                        .password(passwordEncoder.encoding(request.getPassword()))
                        .email(request.getEmail())
                        .mobile(request.getMobile())
                        .nickname(request.getNickname())
                        .roleId(request.getRoleId())
                        .build();
        
        try {
            return userRepository.save(userEntity).getId();
        } catch (DataIntegrityViolationException e) {
            throw new BaseException(HttpStatus.BAD_REQUEST.value(), "用户名、邮箱、手机号等可能有重复", e);
        }
        
    }
    
    @TargetDataSource(EXAM_READ_ONLY)
    public BatchGet<CoreUserInfo> query(UserQueryRequest request) {
        PageHelper.startPage(request.getPageNum(), request.getPerPage(),"id desc");
        List<CoreUserEntity> entities = coreUserMapper.selectAllByNicknameContaining(request.getName(), request.getRoleId());
        PageInfo<CoreUserEntity> pageInfo = PageInfo.of(entities);
        return BatchGet.of(CoreUserInfo.from(pageInfo.getList()), Pagination.fromPage(pageInfo));
    }
    
    @Transactional
    @TargetDataSource(EXAM_READ_WRITE)
    public Boolean delete(Long id) {
        return coreUserMapper.softDel(id);
    }
}

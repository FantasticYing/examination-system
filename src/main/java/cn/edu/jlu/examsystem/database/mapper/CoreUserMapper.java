package cn.edu.jlu.examsystem.database.mapper;
import cn.edu.jlu.examsystem.database.entity.CoreUserEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author WangZeying 2020/9/22 15:36
*/
@Mapper
public interface CoreUserMapper {
    int insert(CoreUserEntity record);

    CoreUserEntity selectByPrimaryKey(Long id);

    Boolean softDel(Long id);

    int updateByPrimaryKeySelective(CoreUserEntity record);
    
    List<CoreUserEntity> selectAllByNicknameContaining(@Param("containingNickname")String containingNickname, @Param("roleId")Short roleId);

	
}
package com.fei.usercenter.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fei.usercenter.model.domain.User;
import org.apache.ibatis.annotations.Mapper;

/**
* @author www
* @description 针对表【user(用户)】的数据库操作Mapper
* @createDate 2024-05-26 17:01:04
* @Entity com.fei.usercenter.model.domain.User
*/
@Mapper
public interface UserMapper extends BaseMapper<User> {

}





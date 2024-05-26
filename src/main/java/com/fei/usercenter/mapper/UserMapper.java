package com.fei.usercenter.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fei.usercenter.model.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * projectName:com.fei.usercenter.mapper
 *
 * @author 飞
 * @create by 2024/5/2615:05
 * description:
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
}

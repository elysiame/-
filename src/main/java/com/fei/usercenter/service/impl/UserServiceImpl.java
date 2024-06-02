package com.fei.usercenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fei.usercenter.common.ErrorCode;
import com.fei.usercenter.exception.BusinessException;
import com.fei.usercenter.model.domain.User;
import com.fei.usercenter.service.UserService;
import com.fei.usercenter.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static com.fei.usercenter.constant.UserConstant.UER_LOGIN_STATE;

/**
* @author fei
* @createDateTime 2024-05-26 17:01:04
*/
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService {

    /**
     * 盐值：混淆密码
     */
    public static final String SALT = "fei";
    @Resource
    private UserMapper userMapper;

    QueryWrapper<User> queryWrapper = new QueryWrapper<>();

    /**
     * 用户登录态键
     */

    @Override
    public long userRegister(String userAccount, String userPassword, String checkPassword, String planetCode) {
        //1.校验账户密码

        if (StringUtils.isAnyBlank(userAccount,userPassword,checkPassword,planetCode)){
            throw  new BusinessException(ErrorCode.PARAMS_ERROR,"参数为空");
        }

        if (userAccount.length()<3){
            throw  new BusinessException(ErrorCode.PARAMS_ERROR,"账户太短");
        }
        if (userPassword.length()<6||checkPassword.length()<6){
            throw  new BusinessException(ErrorCode.PARAMS_ERROR,"密码太短");
        }

        //校验账户是否包含特殊字符
        String validPatter = "^[a-zA-Z0-9_]+$";
        Matcher matcher = Pattern.compile(validPatter).matcher(userAccount);
        if (!matcher.find()){
            throw  new BusinessException(ErrorCode.PARAMS_ERROR,"账户不能包含特殊字符");
        }
        //账户不能重复
        queryWrapper.eq("user_account",userAccount);
        long count = userMapper.selectCount(queryWrapper);

        //该账户已经被人注册
        if (count>0){
            throw  new BusinessException(ErrorCode.PARAMS_ERROR,"账号重复");
        }

        //星球编号不能重复
        queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("planet_code",planetCode);
        count = userMapper.selectCount(queryWrapper);
        if (count>0){
            throw  new BusinessException(ErrorCode.PARAMS_ERROR,"星球编号重复");
        }

        //2.加密
        String encryptUserPassword = DigestUtils.md5DigestAsHex((SALT+userPassword).getBytes());

        //3.插入数据
        User user = new User();
        user.setUserAccount(userAccount);
        user.setUserPassword(encryptUserPassword);
        user.setPlanetCode(planetCode);
        int saveResult = userMapper.insert(user);
        if (saveResult==0){
            throw  new BusinessException(ErrorCode.PARAMS_ERROR,"插入失败");
        }
        return user.getId();
    }

    @Override
    public User userLogin(String userAccount, String userPassword, HttpServletRequest request) {
        //1.验证
        if (userAccount==null||userAccount.length()<3)
        {
            throw  new BusinessException(ErrorCode.PARAMS_ERROR,"账户过短");
        }
        if (userPassword==null||userPassword.length()<6)
        {
            throw  new BusinessException(ErrorCode.PARAMS_ERROR,"密码过短");
        }

        String validPatter = "^[a-zA-Z0-9_]+$";
        Matcher matcher = Pattern.compile(validPatter).matcher(userAccount);
        if (!matcher.find()){
            throw  new BusinessException(ErrorCode.PARAMS_ERROR,"账户不能包含特殊字符");
        }
        //2.加密
        String handlePassword = DigestUtils.md5DigestAsHex((SALT+userPassword).getBytes());
        //查询账户
        queryWrapper.eq("user_account",userAccount);
        queryWrapper.eq("user_password",handlePassword);
        User user = userMapper.selectOne(queryWrapper);

        if (user==null){
            log.info("user login failed, userAccount can't match userPassword");
            throw  new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        //3.脱敏
        User saftyUser = getSafetyUser(user);

        //4.记录用户的登录态
        request.getSession().setAttribute(UER_LOGIN_STATE,saftyUser);
        return saftyUser;
    }

    @Override
    public List<User> searchUsers(String username) {
        if (StringUtils.isNotBlank(username)) {
            queryWrapper.like("username",username);
        }
        List<User> users = userMapper.selectList(queryWrapper);
        return users.stream()
                .map(user -> getSafetyUser(user))
                .collect(Collectors.toList());
    }

    /**
     * 用户注销
     * @param request 请求
     */
    @Override
    public Integer userLogout(HttpServletRequest request) {
        //移除登录态
        request.getSession().removeAttribute(UER_LOGIN_STATE);
        return 1;
    }

    @Override
    public User getSafetyUser(User originUser){
        User safetyUser = new User();
        safetyUser.setId(originUser.getId());
        safetyUser.setUsername(originUser.getUsername());
        safetyUser.setUserAccount(originUser.getUserAccount());
        safetyUser.setAvatarUrl(originUser.getAvatarUrl());
        safetyUser.setGender(originUser.getGender());
        safetyUser.setPhone(originUser.getPhone());
        safetyUser.setEmail(originUser.getEmail());
        safetyUser.setPlanetCode(originUser.getPlanetCode());
        safetyUser.setUserRole(originUser.getUserRole());
        safetyUser.setUserStatus(originUser.getUserStatus());
        safetyUser.setCreateTime(originUser.getCreateTime());

        return safetyUser;
    }
}





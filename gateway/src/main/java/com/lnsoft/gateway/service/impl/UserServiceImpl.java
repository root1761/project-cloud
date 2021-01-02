package com.lnsoft.gateway.service.impl;

import com.lnsoft.gateway.model.User;
import com.lnsoft.gateway.dao.UserMapper;
import com.lnsoft.gateway.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户基本信息 服务实现类
 * </p>
 *
 * @author Louyp
 * @since 2020-12-31
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

}

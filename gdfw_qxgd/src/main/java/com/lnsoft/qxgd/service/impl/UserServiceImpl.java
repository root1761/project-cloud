package com.lnsoft.qxgd.service.impl;

import com.lnsoft.qxgd.model.User;
import com.lnsoft.qxgd.dao.UserMapper;
import com.lnsoft.qxgd.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.seata.spring.annotation.GlobalLock;
import io.seata.spring.annotation.GlobalTransactional;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户基本信息 服务实现类
 * </p>
 *
 * @author Louyp
 * @since 2020-11-23
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
    @GlobalTransactional(name = "fsp-create-order",rollbackFor = Exception.class) //此注解开启全局事务

    public String test(){
    return null;
}
}

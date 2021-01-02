package com.lnsoft.seata.service.impl;

import com.lnsoft.seata.model.TUser;
import com.lnsoft.seata.dao.TUserMapper;
import com.lnsoft.seata.service.ITUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Louyp
 * @since 2021-01-01
 */
@Service
public class TUserServiceImpl extends ServiceImpl<TUserMapper, TUser> implements ITUserService {

}

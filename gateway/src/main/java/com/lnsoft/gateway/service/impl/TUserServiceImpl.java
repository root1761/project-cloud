package com.lnsoft.gateway.service.impl;

import com.lnsoft.gateway.model.TUser;
import com.lnsoft.gateway.dao.TUserMapper;
import com.lnsoft.gateway.service.ITUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Louyp
 * @since 2020-12-31
 */
@Service
public class TUserServiceImpl extends ServiceImpl<TUserMapper, TUser> implements ITUserService {

}

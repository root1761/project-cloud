package com.lnsoft.qxgd.service.impl;

import com.lnsoft.qxgd.feign.TUserService;
import com.lnsoft.qxgd.model.User;
import com.lnsoft.qxgd.dao.UserMapper;
import com.lnsoft.qxgd.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.seata.core.context.RootContext;
import io.seata.core.exception.TransactionException;
import io.seata.spring.annotation.GlobalLock;
import io.seata.spring.annotation.GlobalTransactional;
import io.seata.tm.api.GlobalTransactionContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * <p>
 * 用户基本信息 服务实现类
 * </p>
 *
 * @author Louyp
 * @since 2020-11-23
 */
@Slf4j
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private TUserService iUserService;
@Override
@GlobalTransactional(name = "fsp_tx_group",rollbackFor = Exception.class) //此注解开启全局事务
    public Boolean insert(){
    try{
    final Boolean forObject = iUserService.insert();
    System.out.println(forObject);
        final boolean save = super.save(new User());
        return save;
} catch (Exception e) {
        // TODO: handle exception
        try {
            log.info("载入事务id进行回滚");
            GlobalTransactionContext.reload(RootContext.getXID()).rollback();
        } catch (TransactionException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
    }
		return false;
}
}

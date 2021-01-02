package com.lnsoft.gateway.controller;


import com.lnsoft.gateway.model.TUser;
import com.lnsoft.gateway.model.User;
import com.lnsoft.gateway.service.ITUserService;
import com.lnsoft.gateway.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Louyp
 * @since 2020-12-31
 */
@RestController
@RequestMapping("/t-user")
@Transactional(rollbackFor = Exception.class)
public class TUserController {
    @Autowired
    private ITUserService itUserService;
    @Autowired
    private IUserService iUserService;
    @RequestMapping("/insert")
    public Boolean insert(){
        final TUser user = new TUser();
        user.setId(UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE);
        final boolean save = itUserService.save(user);

        return save;


    }
}

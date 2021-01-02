package com.lnsoft.seata.controller;


import com.lnsoft.seata.model.TUser;
import com.lnsoft.seata.service.ITUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Louyp
 * @since 2021-01-01
 */
@RestController
@RequestMapping("/t-user")
public class TUserController {
    @Autowired
    private ITUserService itUserService;
    @RequestMapping("/insert")
    public Boolean insert(){
        final TUser user = new TUser();
        user.setId(UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE);
        final boolean save = itUserService.save(user);

        return save;


    }
}

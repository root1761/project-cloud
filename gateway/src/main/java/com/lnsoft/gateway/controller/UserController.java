package com.lnsoft.gateway.controller;


import com.lnsoft.gateway.model.User;
import com.lnsoft.gateway.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * <p>
 * 用户基本信息 前端控制器
 * </p>
 *
 * @author Louyp
 * @since 2020-12-31
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private IUserService iUserService;
@RequestMapping("/insert")
    public Boolean insert(){
    final User user = new User();
    user.setId(UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE);
    final boolean save = iUserService.save(user);
    return save;


}
}

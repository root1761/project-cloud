package com.lnsoft.auth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.lnsoft.auth.dao.TUserMapper;
import com.lnsoft.auth.dao.UserMapper;
import com.lnsoft.auth.model.TUser;
import com.mysql.cj.protocol.x.MessageConstants;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Security;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * @author Louyp
 * @version 1.0
 * @data 2021/01/12 9:37
 */
@Service("userDetailsService")
@Slf4j
public class UserDetailServiceImpl implements UserDetailsService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private TUserMapper tUserMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("UserDetailServiceImpl[]loadUserByUsername[]username:{}",username);

        if(StringUtils.isEmpty(username)){
            throw new UsernameNotFoundException(username);
        }
     /*   LambdaQueryWrapper<TUser> queryWrapper= new LambdaQueryWrapper<TUser>();
        queryWrapper.eq()
        tUserMapper.selectOne()*/
        final UserDetails userDetails = new User("zhangsan",passwordEncoder.encode("111"),getAuthorities());
        return userDetails ;
    }
    private Collection<GrantedAuthority> getAuthorities(){
        List<GrantedAuthority> authList = new ArrayList<GrantedAuthority>();
        authList.add(new SimpleGrantedAuthority("ROLE_USER"));
        authList.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        return authList;
    }

}

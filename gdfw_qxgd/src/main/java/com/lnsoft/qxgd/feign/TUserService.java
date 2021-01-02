package com.lnsoft.qxgd.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Louyp
 * @version 1.0
 * @data 2020/12/31 14:08
 */
@FeignClient(value = "seata")
@RequestMapping("t-user")
public interface TUserService {
    @RequestMapping("/insert")
    public Boolean insert();
}

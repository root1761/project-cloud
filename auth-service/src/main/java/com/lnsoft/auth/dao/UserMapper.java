package com.lnsoft.auth.dao;


import org.apache.ibatis.annotations.*;

import java.util.HashMap;
import java.util.List;

/**
 * @author: louyp
 * @since:2020/11/26 20:46
 */
public interface UserMapper {
    @Select("select distinct t1.namecode NAMECODE,t1.DEFAULT_PWD PASSWORD from isc.isc_user t1\n" +
            "     where t1.state=1")
    @Results({@Result(property = "NAMECODE", column = "NAMECODE"), @Result(property = "PASSWORD", column = "PASSWORD"), @Result(property = "relationCodes", javaType = List.class, column = "NAMECODE", many = @Many(select = "com.lnsoft.activiti.mapper.UserMapper.selectByNameCode"))})
    List<HashMap<String,Object>> getUser();
    @Select("select 'GROUP_'||t3.relation_code RELATIONCODE  from isc.isc_user t1,\n" +
            "     isc.isc_actor_role_r t2,\n" +
            "     isc.isc_role_layer_by_org t3,\n" +
            "    isc.isc_sprole t4,\n" +
            "    isc.isc_rolegroup t5\n" +
            "     where t1.id = t2.actor_id\n" +
            "     and t2.org_role_id = t3.id\n" +
            "     and t3.role_id = t4.id\n" +
            "     and t4.rolegroup_id = t5.id\n" +
            "     and t5.application_id = '40280a0e672ea26901672ebd800a0008'\n" +
            "     and t1.state=1" +
            "     and t1.namecode=#{namecode}")
    List<String> selectByNameCode(@Param("namecode") String namecode);
}

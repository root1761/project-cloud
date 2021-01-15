package com.lnsoft.auth.conf;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author Louyp
 * @version 1.0
 * @data 2020/11/23 15:02
 */
@Configuration
@EnableTransactionManagement
@MapperScan("com.lnsoft.auth.dao")
public class MapperPlusConfig {

    @Bean
    public PaginationInterceptor paginationInterceptor(){
        return new PaginationInterceptor();
    }
}

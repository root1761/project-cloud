/*
package com.lnsoft.qxgd.filter;

import com.alibaba.druid.filter.FilterAdapter;
import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.alibaba.druid.proxy.jdbc.DataSourceProxy;
import com.baomidou.mybatisplus.core.toolkit.AES;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.PropertySource;
import org.springframework.core.env.SimpleCommandLinePropertySource;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.Properties;

*/
/**
 * @author Louyp
 * @version 1.0
 * @data 2020/12/27 18:05
 *//*

@NoArgsConstructor
@Slf4j
@Component
public class DruidForMybatisPlusDecryptFilter extends FilterAdapter {

    */
/**
     * 获取命令行中的key
     *//*

    @Autowired
    private ConfigurableEnvironment configurableEnvironment;

    */
/**
     * 重写获取密码解密
     * 1. 参考DruidFilter的代码，取的是file类型的配置文件，这里从启动参数中获取即可
     * 2. 如果不配置，且不需要解密则放行
     *
     * @param dataSourceProxy
     *//*

    @Override
    public void init(DataSourceProxy dataSourceProxy) {
        if (!(dataSourceProxy instanceof DruidDataSource)) {
            log.error("ConfigLoader only support DruidDataSource");
        }
        DruidDataSource dataSource = (DruidDataSource) dataSourceProxy;
        String mpwKey = null;
        for (PropertySource<?> ps : configurableEnvironment.getPropertySources()) {
            if (ps instanceof SimpleCommandLinePropertySource) {
                SimpleCommandLinePropertySource source = (SimpleCommandLinePropertySource) ps;
                mpwKey = source.getProperty("mpw.key");
                break;
            }
        }
        if (null != mpwKey) {
            // 证明加密
            Properties properties = this.setProperties(dataSource, mpwKey);
            try {
                // 将信息配置进druid
                DruidDataSourceFactory.config(dataSource, properties);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        log.info("数据库连接成功！");
    }

    */
/**
     * 通过命令行的密钥进行解密
     *
     * @param dataSource 数据源
     * @param mpwKey     解密key
     * @return
     *//*

    private Properties setProperties(DruidDataSource dataSource, String mpwKey) {
        Properties properties = new Properties();
        // 先解密
        try {
            String userName = AES.decrypt(dataSource.getUsername().substring(4), mpwKey);
            properties.setProperty(DruidDataSourceFactory.PROP_USERNAME, userName);
            dataSource.setUsername(userName);
            String password = AES.decrypt(dataSource.getPassword().substring(4), mpwKey);
            properties.setProperty(DruidDataSourceFactory.PROP_PASSWORD, password);
            dataSource.setPassword(password);
            String url = AES.decrypt(dataSource.getUrl().substring(4), mpwKey);
            properties.setProperty(DruidDataSourceFactory.PROP_URL, url);
            dataSource.setUrl(url);
        } catch (Exception e) {
            log.info("druid decrypt failed!");
            e.printStackTrace();
        }
        return properties;
    }

}*/

package com.marketplace.configurations;


import oracle.jdbc.pool.OracleDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.sql.SQLException;

@Configuration
@ComponentScan(value = "com.marketplace")
public class OracleConfiguration {
    private static final String CONN_USER = "altair_user";
    private static final String CONN_PASSWORD = "1111";
    private static final String HOST = "localhost";
    private static final String PORT = "1521";
    private static final String SID = "XE";
    private static final String URL = String.format("jdbc:oracle:thin:@%s:%s:%s", HOST,PORT,SID);

    @Bean
    DataSource dataSource() throws SQLException {
        OracleDataSource dataSource = new OracleDataSource();
        dataSource.setUser(CONN_USER);
        dataSource.setPassword(CONN_PASSWORD);
        dataSource.setURL(URL);
        dataSource.setImplicitCachingEnabled(true);
        dataSource.setFastConnectionFailoverEnabled(true);
        return  dataSource;
    }



}

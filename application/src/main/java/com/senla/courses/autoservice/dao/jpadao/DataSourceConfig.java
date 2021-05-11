package com.senla.courses.autoservice.dao.jpadao;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import javax.sql.DataSource;


@Configuration
public class DataSourceConfig {

    @Bean
    public DataSource dataSource() {
        return new SimpleDriverDataSource() {
            {
                setDriverClass(org.h2.Driver.class);
                setUsername("sa");
                setUrl("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1");
                setPassword("pass");
            }
        };
    }

    /*@Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(org.h2.Driver.class.getName());
        dataSource.setUsername("sa");
        dataSource.setPassword("pass");
        dataSource.setUrl("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1");
        return dataSource;
    }*/

}

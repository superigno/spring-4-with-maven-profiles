package com.pc.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.apache.tomcat.jdbc.pool.DataSource;

import com.pc.model.AppProperties;

/**
 * @author gino.q
 *
 */
@Configuration
@ComponentScan(basePackages = "com.pc")
@PropertySource({"classpath:application.properties"})
public class ApplicationConfig {
	
	@Autowired
    private Environment env;
 
    @Bean
    public DataSource dataSource() {
    	DataSource dataSource = new DataSource();
        dataSource.setDriverClassName(env.getRequiredProperty("jdbc.driverClassName"));
        dataSource.setUrl(env.getRequiredProperty("jdbc.url"));
        dataSource.setUsername(env.getRequiredProperty("jdbc.username"));
        dataSource.setPassword(env.getRequiredProperty("jdbc.password"));
        return dataSource;
    }
 
    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        jdbcTemplate.setResultsMapCaseInsensitive(true);
        return jdbcTemplate;
    }
    
    @Bean
    public AppProperties appProperties() {
    	AppProperties prop = new AppProperties();
    	prop.setAppDirectory(env.getRequiredProperty("app.directory"));
    	prop.setMerchantIds(env.getRequiredProperty("app.merchantIds", String[].class));
    	prop.setSettlementStartDate(env.getRequiredProperty("app.settlementStartDate"));
    	prop.setSettlementEndDate(env.getRequiredProperty("app.settlementEndDate"));
    	prop.setScanPeriodInMinutes(env.getRequiredProperty("app.scanPeriodInMinutes", Integer.class));
    	prop.setProductionMode(env.getRequiredProperty("app.isProductionMode", Boolean.class));
    	prop.setBaseCurrency(env.getRequiredProperty("app.baseCurrency"));
    	return prop;
    }
    
}

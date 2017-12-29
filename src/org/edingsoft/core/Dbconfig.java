package org.edingsoft.core;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
public class Dbconfig {
	@Bean(name="dataSource")
	public BasicDataSource getDataSource(){
		 BasicDataSource ds = new BasicDataSource();
		 ds.setUrl("jdbc:mysql://118.178.129.111:3306/saas?useUnicode=true&characterEncoding=utf8");
		 ds.setUsername("bocloud");
		 ds.setDriverClassName("com.mysql.jdbc.Driver");
		 ds.setPassword("Usa!bixi1");
		 ds.setMaxActive(10);
		 ds.setDefaultAutoCommit(false);
		 return ds;
	}
	
	@Bean(autowire=Autowire.BY_TYPE )
	public JdbcTemplate getJdbcTemplate(){
		JdbcTemplate jdbcTemplate=new JdbcTemplate();
		jdbcTemplate.setDataSource(getDataSource());
		return jdbcTemplate;
	}
	
}
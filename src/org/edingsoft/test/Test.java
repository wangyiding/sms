package org.edingsoft.test;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class Test {
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
		@RequestMapping("/test")
		public @ResponseBody String show() throws Exception{
			System.out.println(jdbcTemplate.getDataSource().getConnection().isClosed());
			return "hello";
		}
		
		@RequestMapping("/testj")
		public @ResponseBody Map showj() throws Exception{
			System.out.println("jsonTest");
			Map m1=new HashMap();
			m1.put("中文测试", "完美中文");
			return m1;
		
		}
}

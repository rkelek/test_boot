package com.test.project.api.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.test.project.api.schema.DataResultVo;
import com.test.project.api.schema.ResultVo;
import com.test.project.common.util.StringUtil;
import com.test.project.test.TestService;

@RestController
@RequestMapping("/api/test")
public class TestCtr {
	
	@Autowired private StringRedisTemplate template;
	@Autowired private RedisTemplate<String, Object> mapTemplate;
	
	@Autowired
	private TestService kService;
	
	
	@GetMapping("/1")
	public ResultVo getTest1(HttpServletRequest req, HttpServletResponse rep) throws Exception {
		DataResultVo resultVo = new DataResultVo();
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("test1", 1);
		map.put("test2", 2);
		map.put("test3", 3);
		Map<String, Object> map1 = new HashMap<String, Object>();
		map1.put("test4", 4);
		map.put("data", map1);
		mapTemplate.opsForValue().set("test", map);
		mapTemplate.expire("test", 1, TimeUnit.MINUTES);
		Map<String, Object> test = (Map<String, Object>) mapTemplate.opsForValue().get("test");
		
		System.out.println("test : " + test);
	
		
		
		return resultVo;
	}
	
	
	
	
	
	@GetMapping("/2")
	public ResultVo getTest2(HttpServletRequest req, HttpServletResponse rep) throws Exception {
		DataResultVo resultVo = new DataResultVo();
		
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat full = new SimpleDateFormat("yyyy-MM-dd");
		String today = full.format(cal.getTime());
		
		template.opsForValue().set("data1", "test");
        template.expire("data1", 30, TimeUnit.MINUTES);
		
		Map<String, Object> redisMap = (Map<String, Object>) mapTemplate.opsForValue().get("JEJU"+today);
		System.out.println("redisMap : " + redisMap);
		
		//mapTemplate.delete("JEJU"+today);
		
		
		
		return resultVo;
	}
	
	@GetMapping("/3")
	public ResultVo getTest3(HttpServletRequest req, HttpServletResponse rep) throws Exception {
		DataResultVo resultVo = new DataResultVo();
		
		System.out.println("pull request 입니다");
		System.out.println("브랜치가 쓴겁니다");
		
		System.out.println("3안 입니다.");
		
		return resultVo;
	}

	
	
	
}

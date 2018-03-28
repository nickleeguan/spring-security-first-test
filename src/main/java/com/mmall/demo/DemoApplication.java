package com.mmall.demo;

import org.apache.catalina.User;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.access.prepost.PreFilter;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@SpringBootApplication
@RestController
@EnableAutoConfiguration
//启用security标签的权限校验
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@RequestMapping("/")
	public String home(){
		return "hello spring boot";
	}

	@RequestMapping("/hello")
	public String hello(){
		return "hello world";
	}

	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")//方法调用前
	@PostAuthorize("hasRole('ROLE_ADMIN')")//方法调用后验证
//	@PreFilter("")
//	@PostFilter("")
	@RequestMapping("/roleAuth")
	public String role(){
		return "hello world";
	}


	@PreAuthorize("#id<10 and principal.username.equals(#username) and #user.username.equals('abc')")//方法调用前
	@PostAuthorize("returnObject%2 == 0")//方法调用后验证
//	@PreFilter("")
//	@PostFilter("")
	@RequestMapping("/test")
	public Integer test(Integer id, String username, User user){
		return id;
	}

	//对集合处理前后进行校验
	@PreFilter("filterObject%2 == 0")
	@PostFilter("filterObject%4 == 0")
	@RequestMapping("/test2")
	public List<Integer> test2(List<Integer> idList){
		return idList;
	}
}

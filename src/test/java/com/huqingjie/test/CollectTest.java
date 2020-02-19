package com.huqingjie.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.huqingjie.cms.domain.Collect;
import com.huqingjie.cms.service.CollectService;
import com.huqingjie.cms.util.CMSException;
import com.huqingjie.utils.StringUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-beans.xml")
public class CollectTest {
	
	@Autowired
	CollectService collectService;
	// 测试添加
	@Test
	public void UrlTest1() {
		
		String url = "http://localhost/collect?id=51";
		
		boolean httpUrl = StringUtil.isHttpUrl(url);
		
		
		if (httpUrl) {
			Collect collect = new Collect();
			collect.setUrl(url);
			collectService.insert(collect);
			System.out.println("正确的url");
		}else {
			System.out.println("错误的");
		}
		
	}
	// 测试添加
	@Test
	public void UrlTest2() {
		
		String url = "htp://localhost/collect?id=51";
		
		boolean httpUrl = StringUtil.isHttpUrl(url);
		
		if (httpUrl) {
			Collect collect = new Collect();
			collect.setUrl(url);
			collectService.insert(collect);
			System.out.println("正确的url");
		}else {
			System.out.println("错误的");
		}
		
	}
	// 测试删除
	@Test
	public void delCollect() {
		
		int del = collectService.del(37);
		
		if (del>0) {
			System.out.println("删除成功");
		}else {
			System.out.println("删除失败");
		}
		
	}
	
	
}

package com.huqingjie.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.huqingjie.cms.dao.CategoryMapper;
import com.huqingjie.cms.dao.ChannelMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-beans.xml")
public class SelectCount {
	// 测试返回是值，从数据库中获取id的数组，然后随机返回数组的一个值(联动)
	@Autowired
	ChannelMapper channelMapper;
	@Autowired
	CategoryMapper categoryMapper;
	
	@Test
	public void select() {
		
		int[] channelArray = channelMapper.selectByIdCount();
		int a = (int)(Math.random()*channelArray.length);
		int chId = channelArray[a];
		System.out.println(chId);
		
		int[] categoryArray = categoryMapper.selectByIdArray(chId);
		int ca = (int)(Math.random()*categoryArray.length);
		int k = channelArray[ca];
		System.out.println(k);
	}
	
}

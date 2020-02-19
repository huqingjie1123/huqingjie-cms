package com.huqingjie.test;

import java.io.File;
import java.util.Date;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSON;
import com.huqingjie.cms.dao.CategoryMapper;
import com.huqingjie.cms.dao.ChannelMapper;
import com.huqingjie.cms.domain.ArticleWithBLOBs;
import com.huqingjie.utils.FileUtilIO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:producer.xml","classpath:spring-beans.xml"})
public class SendArticleToKafka {
	// 用爬虫爬到数据，使用kafka把爬到的数据加载到数据库
	@Autowired
	KafkaTemplate<String, String> kafkaTemplate;
	@Autowired
	ChannelMapper channelMapper;
	@Autowired
	CategoryMapper categoryMapper;
	
	@Test
	public void testSendArticles() throws Exception {
		
		File file = new File("D:/1708D");
		File[] listFiles = file.listFiles();
		
		for (File file2 : listFiles) {
			String title = file2.getName().replace(".txt", "");
			String content = FileUtilIO.readFile(file2, "utf8");
			
			ArticleWithBLOBs article = new ArticleWithBLOBs();
			
			int[] channelArray = channelMapper.selectByIdCount();
			int chan = (int)(Math.random()*channelArray.length);
			int channelId = channelArray[chan];
			
			int[] categoryArray = categoryMapper.selectByIdArray(channelId);
			int cate = (int)(Math.random()*categoryArray.length);
			int categoryId = categoryArray[cate];
			
			String summary = content.substring(0, 150);
			
			article.setTitle(title);// 标题
			article.setContent(content);// 内容
			article.setChannelId(channelId);
			article.setCategoryId(categoryId);
			article.setCreated(new Date());//发布时间默认系统时间
			article.setStatus(0);//文章状态 0:待审核
			article.setHits(0);//点击量
			article.setSummary(summary);// 摘要
			article.setDeleted(0);//是否删除// 0:未删除
			article.setUpdated(new Date());//更新时间
			article.setHot(0);//非热文章
			
			String string = JSON.toJSONString(article);
			
			kafkaTemplate.send("article", string);
			
		}
		
	}
	
	
	
}

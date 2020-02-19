package com.huqingjie.test;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.github.pagehelper.PageInfo;
import com.huqingjie.cms.dao.ArticleRes;
import com.huqingjie.cms.domain.Article;
import com.huqingjie.cms.service.ArticleService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-beans.xml")
public class SendToEs {
	 // 取出mysql中cms_article的数据，发送到es中
	@Autowired
	ArticleRes articleRes;
	
	@Autowired
	ArticleService articleService;
	
	@Test
	public void sendEs() {
		
		Article article = new Article();
		article.setStatus(1);
		
		List<Article> list = articleService.selectByArticle(article);
		
		articleRes.saveAll(list);
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
//	@Test
//	public void testImportMysqlToEs() {
//		// new 一个article对象，设置要查询的数据
//		Article article = new Article();
//		article.setStatus(1);
//		// 从mysql中查询所有的信息
//		List<Article> selectByArticle = articleService.selectByArticle(article);
//		// 把查询出来的文章批量保存到es索引
//		articleRes.saveAll(selectByArticle);
//	}
	

	
	
}

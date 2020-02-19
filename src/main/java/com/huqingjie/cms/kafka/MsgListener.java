package com.huqingjie.cms.kafka;

import java.util.List;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.listener.MessageListener;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.huqingjie.cms.dao.ArticleRes;
import com.huqingjie.cms.domain.Article;
import com.huqingjie.cms.domain.ArticleWithBLOBs;
import com.huqingjie.cms.service.ArticleService;

public class MsgListener implements MessageListener<String, String>{
	
	@Autowired
	ArticleRes articleRes;
	@Autowired
	private ArticleService articleService;
	
	public void onMessage(ConsumerRecord<String, String> data) {
		
		String article = data.value();
		
		ArticleWithBLOBs bs = JSON.parseObject(article, ArticleWithBLOBs.class);

		articleService.insertSelective(bs);
		
//		String article = data.value();
//		
//		if(article.startsWith("add")) {
//			
//			String string = article.substring(4);
//			
//			ArticleWithBLOBs parseObject = JSON.parseObject(string,ArticleWithBLOBs.class);
//			
//			PageInfo<Article> selects = articleService.selects(parseObject, 1);
//			
//			articleRes.saveAll(selects.getList());
//		
//		}
//		
//		if(article.startsWith("del")) {
//			String msg = article.substring(4);
//			
//			ArticleWithBLOBs articleWithBLOs = JSON.parseObject(msg, ArticleWithBLOBs.class);
//			
//			articleRes.deleteById(articleWithBLOs.getId());
//		}
		
		
	
	}
	
}

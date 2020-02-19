package com.huqingjie.cms.dao;


import java.util.List;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.huqingjie.cms.domain.Article;

public interface ArticleRes extends ElasticsearchRepository<Article, Integer>{
	
	// 根据标题查询
	List<Article> findByTitle(String key);
}

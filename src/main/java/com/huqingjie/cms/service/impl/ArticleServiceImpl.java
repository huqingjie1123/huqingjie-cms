package com.huqingjie.cms.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.huqingjie.cms.dao.ArticleMapper;
import com.huqingjie.cms.domain.Article;
import com.huqingjie.cms.domain.ArticleWithBLOBs;
import com.huqingjie.cms.service.ArticleService;
@Service
public class ArticleServiceImpl implements ArticleService {
	
	@Resource
	private ArticleMapper articleMapper;
	
	@Override
	public PageInfo<Article> selects(Article article, int pageNum) {
		
		PageHelper.startPage(pageNum, 3);
		
		List<Article> list = articleMapper.selects(article);
		
		PageInfo<Article> info = new PageInfo<>(list);
		
		return info;
	}

	@Override
	public int insertSelective(ArticleWithBLOBs record) {
		return articleMapper.insertSelective(record);
	}

	@Override
	public int updateByPrimaryKeySelective(ArticleWithBLOBs record) {
		return articleMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public ArticleWithBLOBs selectByPrimaryKey(Integer id) {
		return articleMapper.selectByPrimaryKey(id);
	}
	
}

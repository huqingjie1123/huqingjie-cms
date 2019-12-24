package com.huqingjie.cms.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.huqingjie.cms.dao.ArticleMapper;
import com.huqingjie.cms.dao.ComplainMapper;
import com.huqingjie.cms.domain.Complain;
import com.huqingjie.cms.service.ComplainService;
import com.huqingjie.cms.util.CMSException;
import com.huqingjie.cms.vo.ComplainVO;
import com.huqingjie.utils.StringUtil;
@Service
public class ComplainServiceImpl implements ComplainService {
	
	@Resource
	private	ComplainMapper comMapper;
	@Resource
	private ArticleMapper articleMapper;
	
	@Override
	public boolean insert(Complain complain) {
		try {
			boolean b = StringUtil.isHttpUrl(complain.getUrl());
			if (!b) {
				throw new CMSException("url 不合法");
			}
			
			articleMapper.updateComplainnum(complain.getArticleId());
			comMapper.insert(complain);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("举报失败");
		}
	}

	@Override
	public PageInfo<Complain> selects(ComplainVO complainVO,int pageNum) {
		PageHelper.startPage(pageNum, 3);
		
		List<Complain> list = comMapper.selects(complainVO);
		
		PageInfo<Complain> info = new PageInfo<>(list);
		
		return info;
	}
	
	
	
	
}

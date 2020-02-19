package com.huqingjie.cms.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.huqingjie.cms.dao.CollectMapper;
import com.huqingjie.cms.domain.Collect;
import com.huqingjie.cms.service.CollectService;
import com.huqingjie.cms.util.CMSException;
import com.huqingjie.utils.StringUtil;
@Service
public class CollectServiceImpl implements CollectService {
	
	@Resource
	private CollectMapper collectMapper;

	@Override
	public PageInfo<Collect> selectCollect(int pageNum) {
		
		PageHelper.startPage(pageNum, 5);
		
		List<Collect> list = collectMapper.selectCollect();
		
		PageInfo<Collect> info = new PageInfo<>(list);
		
		return info;
		
	}

	@Override
	public int del(Integer id) {
		return collectMapper.del(id);
	}

	@Override
	public boolean insert(Collect co) {
		
		try {
			boolean b = StringUtil.isHttpUrl(co.getUrl());
			if (!b) {
				throw new CMSException("url 不合法");
			}
			
			collectMapper.insert(co);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("收藏失败");
		}
		
	}
	
}

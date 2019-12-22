package com.huqingjie.cms.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.huqingjie.cms.dao.CategoryMapper;
import com.huqingjie.cms.dao.ChannelMapper;
import com.huqingjie.cms.domain.Category;
import com.huqingjie.cms.domain.Channel;
import com.huqingjie.cms.service.ChannelService;
@Service
public class ChannelServiceImpl implements ChannelService {
	
	@Resource
	private ChannelMapper channelMapper;
	
	@Resource
	private CategoryMapper categoryMapper;

	@Override
	public List<Channel> selects() {
		return channelMapper.selects();
	}

	@Override
	public List<Category> selectsByChannelId(Integer channelId) {
		return categoryMapper.selectsByChannelId(channelId);
	}
	
}

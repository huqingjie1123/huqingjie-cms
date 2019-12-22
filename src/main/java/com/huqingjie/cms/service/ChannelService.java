package com.huqingjie.cms.service;

import java.util.List;

import com.huqingjie.cms.domain.Category;
import com.huqingjie.cms.domain.Channel;

public interface ChannelService {
	/**
	 * 
	 * @Title: selects 
	 * @Description: 所有栏目
	 * @return
	 * @return: List<Channel>
	 */
	List<Channel> selects();
	/**
	 * 
	 * @Title: selectsByChannelId 
	 * @Description: 根据栏目查询分类
	 * @param channel
	 * @return
	 * @return: List<Category>
	 */
	List<Category> selectsByChannelId(Integer channelId);
}

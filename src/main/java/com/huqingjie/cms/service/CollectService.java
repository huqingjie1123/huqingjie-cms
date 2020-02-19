package com.huqingjie.cms.service;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.huqingjie.cms.domain.Collect;

public interface CollectService {
	/**
	 * 查询收藏记录
	 * @Title: selectCollect 
	 * @Description: TODO
	 * @return
	 * @return: List<Collect>
	 */
	PageInfo selectCollect(int pageNum);
	/**
	 * 删除
	 * @Title: del 
	 * @Description: TODO
	 * @param id
	 * @return
	 * @return: int
	 */
	int del(Integer id);
	/**
	 * 收藏
	 * @Title: insert 
	 * @Description: TODO
	 * @param record
	 * @return
	 * @return: int
	 */
	boolean insert(Collect co);
}

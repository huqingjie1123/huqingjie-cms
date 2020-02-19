package com.huqingjie.cms.dao;

import java.util.List;

import com.huqingjie.cms.domain.Collect;

public interface CollectMapper {
	/**
	 * 查询所有收藏记录
	 * @Title: selectCollect 
	 * @Description: TODO
	 * @return
	 * @return: List<Collect>
	 */
	List<Collect> selectCollect();
	/**
	 * 删除收藏
	 * @Title: del 
	 * @Description: TODO
	 * @param id
	 * @return
	 * @return: int
	 */
	int del(Integer id);
	
    int deleteByPrimaryKey(Integer id);

    int insert(Collect record);

    int insertSelective(Collect record);

    Collect selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Collect record);

    int updateByPrimaryKey(Collect record);
}
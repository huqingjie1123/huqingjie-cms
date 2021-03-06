package com.huqingjie.cms.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.huqingjie.cms.domain.Category;

public interface CategoryMapper {
	/**
	 * @Title: selectsByChannelId 
	 * @Description: 根据栏目查询分类
	 * @param channelId
	 * @return
	 * @return: List<Category>
	 */
	List<Category> selectsByChannelId(Integer channelId);
	
	int deleteByPrimaryKey(Integer id);

    int insert(Category record);

    int insertSelective(Category record);

    Category selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Category record);

    int updateByPrimaryKey(Category record);
    
    int[] selectByIdArray(@Param("chId")int chId);
    
}
package com.huqingjie.cms.dao;

import java.util.List;

import com.huqingjie.cms.domain.Slide;

public interface SlideMapper {
    /**
     	* 查询
     * @Title: selects 
     * @Description: TODO
     * @return
     * @return: List<Slide>
     */
	List<Slide> selects();
	
	int deleteByPrimaryKey(Integer id);

    int insert(Slide record);

    int insertSelective(Slide record);

    Slide selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Slide record);

    int updateByPrimaryKey(Slide record);
}
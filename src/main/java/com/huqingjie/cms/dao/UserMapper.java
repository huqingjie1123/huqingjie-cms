package com.huqingjie.cms.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.huqingjie.cms.domain.User;

public interface UserMapper {
	/**
	 * 
	 * @Title: selects 
	 * @Description: 查询 分页
	 * @param name
	 * @return
	 * @return: List<User>
	 */
	List<User> selects(@Param("name")String name);
	/**
	 * 
	 * @Title: selectByName 
	 * @Description: 按用户查询
	 * @param name
	 * @return
	 * @return: User
	 */
	User selectByName(@Param("username")String name);
	
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
}
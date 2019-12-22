package com.huqingjie.cms.service;


import com.github.pagehelper.PageInfo;
import com.huqingjie.cms.domain.User;

public interface UserService {
	/**
	 * 
	 * @Title: selects 
	 * @Description: 列表，分页，模糊
	 * @param name
	 * @param pageNum
	 * @return
	 * @return: PageInfo<User>
	 */
	PageInfo<User> selects(String name,int pageNum);
	/**
	 * 
	 * @Title: updateByPrimaryKeySelective 
	 * @Description: TODO
	 * @param record
	 * @return
	 * @return: int
	 */
	int updateByPrimaryKeySelective(User record);
	/**
	 * 
	 * @Title: insertSelective 
	 * @Description: 注册
	 * @param record
	 * @return
	 * @return: int
	 */
	int insertSelective(User record);
	/**
	 * 登陆
	 * @Title: login 
	 * @Description: TODO
	 * @param user
	 * @return
	 * @return: User
	 */
	User login(User user);
	
}

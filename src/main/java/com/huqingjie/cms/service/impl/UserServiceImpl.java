package com.huqingjie.cms.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.huqingjie.cms.dao.UserMapper;
import com.huqingjie.cms.domain.User;
import com.huqingjie.cms.service.UserService;
import com.huqingjie.cms.util.CMSException;
import com.huqingjie.cms.util.Md5Util;
import com.huqingjie.utils.StringUtil;

@Service
public class UserServiceImpl implements UserService {

	@Resource
	private UserMapper mapper;

	@Override
	public PageInfo<User> selects(String name, int pageNum) {

		PageHelper.startPage(pageNum, 3);

		List<User> list = mapper.selects(name);

		PageInfo<User> info = new PageInfo<>(list);

		return info;
	}

	@Override
	public int updateByPrimaryKeySelective(User record) {
		return mapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int insertSelective(User user) {

		// 用户名不能为空
		if (!StringUtil.hasText(user.getUsername())) {
			throw new CMSException("用户名不能为空");
		}
		// 用户名是否重复
		User u = mapper.selectByName(user.getUsername());
		if (null != u) {
			throw new CMSException("用户名已存在");
		}
		// 密码不能为空
		if (!(StringUtil.hasText(user.getPassword()))) {
			throw new CMSException("密码不能为空");
		}
		// 密码长度
		if (!(user.getPassword().length() >= 6 && user.getPassword().length() <= 12)) {
			throw new CMSException("密码长度为6-12位");
		}
		// 两次密码是否一致
		if (!user.getPassword().equals(user.getPassword())) {
			throw new CMSException("两次密码不一致");
		}

		// 注册用户默认的角色为0
		user.setRole("0");// 0:普通用户,1:管理员
		user.setCreated(new Date());// 默认注册时间
		user.setLocked(0);// 用户状态,未锁定
		user.setNickname(user.getUsername());

		// 为密码加密
		user.setPassword(Md5Util.md5Encoding(user.getPassword()));
		
		return mapper.insert(user);
	}

	@Override
	public User login(User user) {

		// 用户名不能为空
		if (!StringUtil.hasText(user.getUsername())) {
			throw new CMSException("用户名不能为空");
		}
		// 密码不能为空
		if (!(StringUtil.hasText(user.getPassword()))) {
			throw new CMSException("密码不能为空");
		}
		// 校验账号密码是否正确
		User u = mapper.selectByName(user.getUsername());
		if (null==u) {
			throw new CMSException("无此用户");
		}else {
			//校验密码
			if (!u.getPassword().equals(user.getPassword())) {
				throw new CMSException("密码不正确");
			}
		}

		return u;
	}

}

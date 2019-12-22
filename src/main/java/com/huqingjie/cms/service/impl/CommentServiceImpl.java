package com.huqingjie.cms.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.huqingjie.cms.dao.CommentMapper;
import com.huqingjie.cms.domain.Comment;
import com.huqingjie.cms.service.CommentService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
@Service
public class CommentServiceImpl implements CommentService {
	@Resource
	private CommentMapper commentMapper;

	@Override
	public int insert(Comment comment) {
		return commentMapper.insert(comment);
	}

	@Override
	public PageInfo<Comment> selects(Comment comment,Integer pageNum) {
		
		PageHelper.startPage(pageNum, 3);
		List<Comment> list = commentMapper.selects(comment);
		return new PageInfo<Comment>(list);
	}

}

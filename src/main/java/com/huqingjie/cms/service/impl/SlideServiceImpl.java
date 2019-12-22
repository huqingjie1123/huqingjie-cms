package com.huqingjie.cms.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.huqingjie.cms.dao.SlideMapper;
import com.huqingjie.cms.domain.Slide;
import com.huqingjie.cms.service.SlideService;
@Service
public class SlideServiceImpl implements SlideService {
	
	@Resource
	private SlideMapper mapper;
	
	@Override
	public List<Slide> selects() {
		return mapper.selects();
	}
	
	
	
}

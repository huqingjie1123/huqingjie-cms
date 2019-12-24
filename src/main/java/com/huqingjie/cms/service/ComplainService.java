package com.huqingjie.cms.service;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.huqingjie.cms.domain.Complain;
import com.huqingjie.cms.vo.ComplainVO;

public interface ComplainService {
	/**
	 * 添加举报
	 * @Title: insert 
	 * @Description: TODO
	 * @param complain
	 * @return
	 * @return: int
	 */
	boolean insert(Complain complain);
	/**
	 * 
	 * @Title: selects 
	 * @Description: TODO
	 * @param complainVO
	 * @return
	 * @return: List<Complain>
	 */
	PageInfo<Complain> selects(ComplainVO complainVO,int pageNum);
}

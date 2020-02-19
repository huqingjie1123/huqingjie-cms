package com.huqingjie.cms.dao;

import java.util.List;

import com.huqingjie.cms.domain.Complain;
import com.huqingjie.cms.vo.ComplainVO;

public interface ComplainMapper {
	/**
	 * 添加举报
	 * @Title: insert 
	 * @Description: TODO
	 * @param complain
	 * @return
	 * @return: int
	 */
	int insert(Complain complain);
	/**
	 * 查询举报
	 * @Title: selects 
	 * @Description: TODO
	 * @param complainVO
	 * @return
	 * @return: List<Complain>
	 */
	List<Complain> selects(ComplainVO complainVO);

}

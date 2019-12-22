package com.huqingjie.cms.service;

import com.github.pagehelper.PageInfo;
import com.huqingjie.cms.domain.Article;
import com.huqingjie.cms.domain.ArticleWithBLOBs;

public interface ArticleService {
	/**
	 * 
	 * @Title: selects 
	 * @Description: 列表 分页
	 * @param article
	 * @param pageNum
	 * @return
	 * @return: PageInfo<Article>
	 */
	PageInfo<Article> selects(Article article,int pageNum);
	/**
	 * 
	 * @Title: insertSelective 
	 * @Description: 添加文章
	 * @param record
	 * @return
	 * @return: int
	 */
    int insertSelective(ArticleWithBLOBs record);
	/**
	 * 修改
	 * @Title: updateByPrimaryKeySelective 
	 * @Description: TODO
	 * @param record
	 * @return
	 * @return: int
	 */
    int updateByPrimaryKeySelective(ArticleWithBLOBs record);
    /**
     * 文章详情
     * @Title: selectByPrimaryKey 
     * @Description: TODO
     * @param id
     * @return
     * @return: ArticleWithBLOBs
     */
    ArticleWithBLOBs selectByPrimaryKey(Integer id);

    
    
}

package com.huqingjie.cms.service;

import java.util.List;

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
    /**
     * 查询数据库所有值
     * @Title: selectByArticle 
     * @Description: TODO
     * @param article
     * @return
     * @return: List<Article>
     */
    List<Article> selectByArticle(Article article);
    /**
     * 点击次数加一
     * @Title: insertByCom 
     * @Description: TODO
     * @param id
     * @return: void
     */
    void insertByCom(Integer id);
    
}

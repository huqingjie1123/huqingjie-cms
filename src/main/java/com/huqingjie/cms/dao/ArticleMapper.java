package com.huqingjie.cms.dao;

import java.util.List;

import com.huqingjie.cms.domain.Article;
import com.huqingjie.cms.domain.ArticleWithBLOBs;

public interface ArticleMapper {
	/**
	 * 
	 * @Title: selects 
	 * @Description: 查询文章
	 * @param article
	 * @return
	 * @return: List<Article>
	 */
	List<Article> selects(Article article);
	
    int deleteByPrimaryKey(Integer id);

    int insert(ArticleWithBLOBs record);

    int insertSelective(ArticleWithBLOBs record);

    ArticleWithBLOBs selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ArticleWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(ArticleWithBLOBs record);

    int updateByPrimaryKey(Article record);
    /**
     * 更新举报数量
     * @Title: updateComplainnum 
     * @Description: TODO
     * @param articleId
     * @return
     * @return: int
     */
    int updateComplainnum(Integer articleId);
    /**
     * 查询数据库中的所有值
     * @Title: selectByArticle 
     * @Description: TODO
     * @param article
     * @return
     * @return: List<Article>
     */
    List<Article> selectByArticle(Article article);
    
    void insertByCom(Integer id);
}
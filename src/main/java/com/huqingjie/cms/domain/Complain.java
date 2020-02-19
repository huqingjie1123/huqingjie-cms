package com.huqingjie.cms.domain;

/**
 * 举报文章
 * 
 * @ClassName: Complain
 * @Description: TODO
 * @author: lenovo
 * @date: 2019年12月23日 下午6:27:05
 */
public class Complain {

	private Integer id;
	private String typename;// 举报类型
	private String content;// 举报内容
	private Integer articleId;// 被举报的文章
	private String picurl;// 举报图片

	private String url;// 举报的文章地址

	private Integer user_id;// 举报人人ID

	private String created;// 举报时间

	private String complain_num;// 举报次数

	private Article article;
	private User user;
	
	
	public String getComplain_num() {
		return complain_num;
	}

	public void setComplain_num(String complain_num) {
		this.complain_num = complain_num;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTypename() {
		return typename;
	}

	public void setTypename(String typename) {
		this.typename = typename;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getArticleId() {
		return articleId;
	}

	public void setArticleId(Integer articleId) {
		this.articleId = articleId;
	}

	public String getPicurl() {
		return picurl;
	}

	public void setPicurl(String picurl) {
		this.picurl = picurl;
	}

	public Integer getUser_id() {
		return user_id;
	}

	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}

	public String getCreated() {
		return created;
	}

	public void setCreated(String created) {
		this.created = created;
	}

	public Article getArticle() {
		return article;
	}

	public void setArticle(Article article) {
		this.article = article;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}

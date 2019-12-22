package com.huqingjie.cms.controller;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.PageInfo;
import com.huqingjie.cms.domain.Article;
import com.huqingjie.cms.domain.ArticleWithBLOBs;
import com.huqingjie.cms.domain.Category;
import com.huqingjie.cms.domain.Channel;
import com.huqingjie.cms.domain.Comment;
import com.huqingjie.cms.domain.User;
import com.huqingjie.cms.service.ArticleService;
import com.huqingjie.cms.service.ChannelService;
import com.huqingjie.cms.service.CommentService;

@RequestMapping("my")
@Controller
public class MyController {
	
	@Resource
	private ChannelService channelService;
	@Resource
	private ArticleService articleService;
	@Resource
	private CommentService commentService;
	
	@RequestMapping(value = {"","/","index"})
	public String index() {
		return "my/index";
	}
	/**
	 * 
	 * @Title: selectChannels 
	 * @Description: 去发布文章
	 * @return
	 * @return: List<Channel>
	 */
	@GetMapping("article/publish")
	public String publish() {
		return "my/article/publish";
	}
	
	/**
	 * 查询我的评论
	 * @Title: comments 
	 * @Description: TODO
	 * @return
	 * @return: String
	 */
	@RequestMapping("article/comments")
	public String comments(Model model,HttpSession session) {
		User user = (User) session.getAttribute("user");
		Comment comment = new Comment();
		comment.setUserId(user.getId());
		PageInfo<Comment> info = commentService.selects(comment, 1);
		model.addAttribute("info", info);
		return "my/article/comments";
		
	}
	/**
	 * 
	 * @Title: selectChannels 
	 * @Description: 所有栏目
	 * @return
	 * @return: List<Channel>
	 */
	@ResponseBody
	@GetMapping("channel/selects")
	public List<Channel> selectChannels(){
		return channelService.selects();
	}
	/**
	 * 
	 * @Title: selectsByChannelId 
	 * @Description: 根据栏目查询分类
	 * @param channelId
	 * @return
	 * @return: List<Category>
	 */
	@ResponseBody
	@GetMapping("category/selectsByChannelId")
	public List<Category> selectsByChannelId(Integer channelId){
		return channelService.selectsByChannelId(channelId);
	}
	
	/**
	 * 发布文章
	 * @Title: publish 
	 * @Description: TODO
	 * @param myFile
	 * @param article
	 * @return
	 * @throws IllegalStateException
	 * @throws IOException
	 * @return: boolean
	 */
	@ResponseBody
	@RequestMapping("article/publish")
	public boolean publish(MultipartFile myFile,ArticleWithBLOBs article,HttpServletRequest request) throws IllegalStateException, IOException{
		
		String path = "d:/pic/";
		
		if (!myFile.isEmpty()) {
			String filename = myFile.getOriginalFilename();
			
			String newFileName = UUID.randomUUID()+filename.substring(filename.lastIndexOf("."));
			
			myFile.transferTo(new File(path,newFileName));
			
			article.setPicture(newFileName);
		}
		//初始化操作
		article.setCreated(new Date());//发布时间默认系统时间
		article.setStatus(0);//文章状态 0:待审核
		article.setHits(0);//点击量
		article.setDeleted(0);//是否删除// 0:未删除
		article.setUpdated(new Date());//更新时间
		//从session获取当前登录人的信息。用来指定发布人
		HttpSession session = request.getSession();
		User u = (User) session.getAttribute("user");
		article.setUserId(u.getId());//发布人
		article.setHot(0);//非热文章
		
		return articleService.insertSelective(article)>0;
	}
	/**
	 * 文章详情
	 * @Title: article 
	 * @Description: TODO
	 * @param id
	 * @param m
	 * @return
	 * @return: String
	 */
	@GetMapping("article/article")
	public String article(Integer id,Model m) {
		
		ArticleWithBLOBs art = articleService.selectByPrimaryKey(id);
		m.addAttribute("art", art);
		
		return "my/article/article";
	}
	/**
	 * 我的文章列表
	 * @Title: articles 
	 * @Description: TODO
	 * @param m
	 * @param article
	 * @param pageNum
	 * @return
	 * @return: String
	 */
	@GetMapping("article/articles")
	public String articles(HttpServletRequest request,Model m,Article article,@RequestParam(defaultValue = "1")int pageNum ) {
		HttpSession session = request.getSession();
		User u = (User) session.getAttribute("user");
		//只查询当前用户的文章
		article.setUserId(u.getId());
		PageInfo<Article> info = articleService.selects(article, pageNum);
		m.addAttribute("info", info);
		
		return "my/article/articles";
	}
	/**
	 * 文章修改
	 * @Title: update 
	 * @Description: TODO
	 * @param art
	 * @return
	 * @return: boolean
	 */
	@ResponseBody
	@PostMapping("article/update")
	public boolean update(ArticleWithBLOBs art) {
		return articleService.updateByPrimaryKeySelective(art)>0;
	}
	
}

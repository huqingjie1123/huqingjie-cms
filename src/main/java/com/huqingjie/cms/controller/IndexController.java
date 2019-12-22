package com.huqingjie.cms.controller;

import java.util.Date;
import java.util.List;

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

import com.github.pagehelper.PageInfo;
import com.huqingjie.cms.domain.Article;
import com.huqingjie.cms.domain.ArticleWithBLOBs;
import com.huqingjie.cms.domain.Category;
import com.huqingjie.cms.domain.Channel;
import com.huqingjie.cms.domain.Comment;
import com.huqingjie.cms.domain.Slide;
import com.huqingjie.cms.domain.User;
import com.huqingjie.cms.service.ArticleService;
import com.huqingjie.cms.service.ChannelService;
import com.huqingjie.cms.service.CommentService;
import com.huqingjie.cms.service.SlideService;

@Controller
public class IndexController {

	@Resource
	private ChannelService channelService;
	@Resource
	private ArticleService articleService;
	@Resource
	private SlideService slideService;
	@Resource
	private CommentService commentService;

	@RequestMapping(value = { "", "/", "index" })
	public String index(Model model, Article article, @RequestParam(defaultValue = "1") Integer pageNum) {
		// 0.封装查询条件
		article.setStatus(1);
		model.addAttribute("article", article);
		// 1. 查询出所有的栏目
		List<Channel> channels = channelService.selects();
		model.addAttribute("channels", channels);

		// 如果栏目为空,则默认显示推荐的文章
		if (null == article.getChannelId()) {

			// 1.查询广告
			List<Slide> slides = slideService.selects();
			model.addAttribute("slides", slides);

			Article a2 = new Article();
			a2.setHot(1);// 1 推荐文章的标志
			a2.setStatus(1); // 2审核过的文章
			// 2.查询推荐下的所有的文章
			PageInfo<Article> info = articleService.selects(a2, pageNum);
			model.addAttribute("info", info);
		}

		// 如果栏目不为空.则查询栏目下所有分类
		if (null != article.getChannelId()) {
			List<Category> categorys = channelService.selectsByChannelId(article.getChannelId());

			// 查询栏目下所有的文章
			PageInfo<Article> info = articleService.selects(article, pageNum);
			model.addAttribute("info", info);
			model.addAttribute("categorys", categorys);

			// 如果分类不为空.则查询分类下 文章
			if (null != article.getCategoryId()) {
				PageInfo<Article> info2 = articleService.selects(article, pageNum);
				model.addAttribute("info", info2);
			}
		}
		// 页面右侧显示最近发布的5篇文章
		Article last = new Article();
		last.setStatus(1);
		PageInfo<Article> lastInfo = articleService.selects(last, 1);
		model.addAttribute("lastInfo", lastInfo);

		return "index/index";

	}

	// 查询单个文章
	@GetMapping("article")
	public String article(Integer id, Model model) {
		ArticleWithBLOBs art = articleService.selectByPrimaryKey(id);
		model.addAttribute("art", art);

		Comment comment = new Comment();
		comment.setArticleId(art.getId());
		PageInfo<Comment> info = commentService.selects(comment, 1);
		model.addAttribute("info", info);

		return "/index/article";
	}

	@ResponseBody
	@PostMapping("addComment")
	public boolean addComment(Comment comment,HttpServletRequest request) {
		HttpSession session = request.getSession();
		//获取session中的用户对象
		User user = (User) session.getAttribute("user");
		if(null==user)
		 return false;//没有登录，不能评论
		comment.setUserId(user.getId());
		comment.setCreated(new Date());
		return commentService.insert(comment)>0;
		
	}

	
	 

}

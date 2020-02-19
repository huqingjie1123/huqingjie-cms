package com.huqingjie.cms.controller;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.PageInfo;
import com.huqingjie.cms.dao.ArticleRes;
import com.huqingjie.cms.domain.Article;
import com.huqingjie.cms.domain.ArticleWithBLOBs;
import com.huqingjie.cms.domain.Category;
import com.huqingjie.cms.domain.Channel;
import com.huqingjie.cms.domain.Collect;
import com.huqingjie.cms.domain.Comment;
import com.huqingjie.cms.domain.Complain;
import com.huqingjie.cms.domain.Slide;
import com.huqingjie.cms.domain.User;
import com.huqingjie.cms.service.ArticleService;
import com.huqingjie.cms.service.ChannelService;
import com.huqingjie.cms.service.CollectService;
import com.huqingjie.cms.service.CommentService;
import com.huqingjie.cms.service.ComplainService;
import com.huqingjie.cms.service.SlideService;
import com.huqingjie.cms.util.CMSException;
import com.huqingjie.cms.util.HLUtils;

@Controller
public class IndexController {

	@Resource
	private ChannelService channelService;
	@Resource
	private CollectService collectService;
	@Resource
	private ArticleService articleService;
	@Resource
	private SlideService slideService;
	@Autowired
	KafkaTemplate<String, String> kafkaTemplate;
	@Resource
	private CommentService commentService;
	@Resource
	private ComplainService complainService;
	@SuppressWarnings("rawtypes")
	@Autowired
	RedisTemplate redisTemplate;
	@Autowired
	ArticleRes articleRes;
	@Autowired
	ElasticsearchTemplate elasticsearchTemplate;
	
	
	@RequestMapping(value = {"index/article/search"})
	public String search(Model m,String key) {
		// 1. 查询出所有的栏目
		List<Channel> channels = channelService.selects();
		m.addAttribute("channels", channels);
		// 无高亮
//		List<Article> list = articleRes.findByTitle(key);
//		PageInfo<Article> info = new PageInfo<>(list);
//		m.addAttribute("info", info);
		// 高亮搜索
		PageInfo<?> info = HLUtils.findByHighLight(elasticsearchTemplate, Article.class, 1, 5, new String[] {"title"}, "id", key);
		m.addAttribute("info", info);
		m.addAttribute("key", key);
// 		PageInfo<Article> info = new PageInfo<Article>(infos.getList());
		
		
		// 页面右侧显示最近发布的5篇文章
		Article last = new Article();
		last.setStatus(1);
		last.setDeleted(0);
		PageInfo<Article> lastInfo = articleService.selects(last, 1);
		m.addAttribute("lastInfo", lastInfo);
				
		return "index/index";
			
	}
	
	@SuppressWarnings({ "unused", "unchecked" })
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
			List<Article> redisArticle = redisTemplate.opsForList().range("new_article", 0, -1);
			
			if(redisArticle==null || redisArticle.size()==0 ) {
				PageInfo<Article> lastInfo = articleService.selects(a2, pageNum);
				redisTemplate.opsForList().leftPushAll("new_article", lastInfo.getList().toArray());
				model.addAttribute("info", lastInfo);
			}else {
				PageInfo<Article> pageInfo = new PageInfo<>(redisArticle);
				model.addAttribute("info", pageInfo);
			}
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
		last.setDeleted(0);
		// 优化最新文章
		@SuppressWarnings("unchecked")
		List<Article> redisArticle = redisTemplate.opsForList().range("new_article", 0, -1);
		
		if(redisArticle==null || redisArticle.size()==0 ) {
			PageInfo<Article> lastInfo = articleService.selects(last, pageNum);
			redisTemplate.opsForList().leftPushAll("new_article", lastInfo.getList().toArray());
			model.addAttribute("lastInfo", lastInfo);
		}else {
			PageInfo<Article> pageInfo = new PageInfo<>(redisArticle);
			model.addAttribute("lastInfo", pageInfo);
		}
		
		return "index/index";

	}

	// 查询单个文章
	@GetMapping("article")
	public String article(Integer id, Model model) {
		Article article = new Article();
		article.setId(id);
		ArticleWithBLOBs art = articleService.selectByPrimaryKey(id);
		
		
		
		articleService.insertByCom(id);
		model.addAttribute("art", art);

		Comment comment = new Comment();
		comment.setArticleId(art.getId());
		PageInfo<Comment> info = commentService.selects(comment, 1);
		model.addAttribute("info", info);
		
		return "/index/article";
	}

	@ResponseBody
	@PostMapping("addComment")
	public boolean addComment(Comment comment, HttpServletRequest request) {
		HttpSession session = request.getSession();
		// 获取session中的用户对象
		User user = (User) session.getAttribute("user");
		if (null == user)
			return false;// 没有登录，不能评论
		comment.setUserId(user.getId());
		comment.setCreated(new Date());
		return commentService.insert(comment) > 0;

	}

	// 去举报
	@GetMapping("complain")
	public String complain(Model model, Article art, HttpSession session) {
		User user = (User) session.getAttribute("user");
		if (null != user) {// 如果有户登录
			art.setUser(user);// 封装举报人和举报的文章
			model.addAttribute("art", art);
			return "index/complain";// 转发到举报页面
		}

		return "redirect:/passport/login";// 没有登录，先去登录

	}
	// 收藏
	@GetMapping("collect")
	public String collect(Model m,Article art,HttpSession session) {
		User user = (User) session.getAttribute("user");
		if (null != user) {// 如果有户登录
			art.setUser(user);
			m.addAttribute("art", art);
			return "index/collect";// 转发到页面
		}

		return "redirect:/passport/login";// 没有登录，先去登录
		
	}
	// 收藏
	@ResponseBody
	@PostMapping("go/collect")
	public boolean collect(Model m,MultipartFile file,Collect co) {
		
		try {
			collectService.insert(co);
			return true;
		} catch (CMSException e) {
			e.printStackTrace();
			m.addAttribute("error", e.getMessage());
		}
		
		return false;
	}
	
	// 执行举报
	@ResponseBody
	@PostMapping("complain")
	public boolean complain(Model model, MultipartFile file, Complain complain) {
		if (null != file && !file.isEmpty()) {
			String path = "d:/pic/";
			String filename = file.getOriginalFilename();
			String newFileName = UUID.randomUUID() + filename.substring(filename.lastIndexOf("."));
			File f = new File(path, newFileName);
			try {
				file.transferTo(f);
				complain.setPicurl(newFileName);
			} catch (IllegalStateException | IOException e) {
				e.printStackTrace();
			}
		}

		try {
			// 执行举报
			complainService.insert(complain);
			return true;
		} catch (CMSException e) {
			e.printStackTrace();
			model.addAttribute("error", e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("error", "系统错误，联系管理员");
		}
		return false;
	}
	
	
	

}

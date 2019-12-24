package com.huqingjie.cms.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.huqingjie.cms.dao.ArticleMapper;
import com.huqingjie.cms.domain.Article;
import com.huqingjie.cms.domain.ArticleWithBLOBs;
import com.huqingjie.cms.domain.Complain;
import com.huqingjie.cms.domain.User;
import com.huqingjie.cms.service.ArticleService;
import com.huqingjie.cms.service.ComplainService;
import com.huqingjie.cms.service.UserService;
import com.huqingjie.cms.vo.ComplainVO;

@RequestMapping("admin")
@Controller
public class AdminController {
	
	@Resource
	private UserService userService;
	@Resource
	private ArticleService articleService;
	@Resource
	private ComplainService comService;
	
	/**
	 * 
	 * @Title: index 
	 * @Description: 进入admin后台首页
	 * @return
	 * @return: String
	 */
	@RequestMapping(value = {"index","/",""})
	public String index() {
		return "admin/index";
	}
	
	@GetMapping("article/selects")
	public String selects(Model m,Article article,@RequestParam(defaultValue = "1")int  pageNum) {
		
		if (article.getStatus()==null) {
			article.setStatus(0);
		}
		PageInfo<Article> info = articleService.selects(article, pageNum);
		m.addAttribute("info", info);
		m.addAttribute("article", article);
		
		return "admin/article/articles";
	}
	
	/**
	 * 
	 * @Title: selects 
	 * @Description: 用户列表
	 * @param m
	 * @param pageNum
	 * @param name
	 * @return
	 * @return: String
	 */
	@GetMapping("user/selects")
	public String selects(Model m,@RequestParam(defaultValue = "1")int pageNum,String name) {
		
		PageInfo<User> info = userService.selects(name, pageNum);
		
		m.addAttribute("info", info);
		m.addAttribute("name", name);
		
		return "admin/user/users";
	}
	/**
	 * 
	 * @Title: update 
	 * @Description: 修改用户
	 * @param user
	 * @return
	 * @return: boolean
	 */
	@ResponseBody
	@RequestMapping("user/update")
	public boolean update(User user) {
		return userService.updateByPrimaryKeySelective(user)>0;
	}
	
	@GetMapping("article/select")
	public String select(Model m,Integer id) {
		ArticleWithBLOBs art = articleService.selectByPrimaryKey(id);
		m.addAttribute("art", art);
		
		return "admin/article/article";
	}
	/**
	 * 
	 * @Title: update 
	 * @Description: 审核文章
	 * @param article
	 * @return
	 * @return: Boolean
	 */
	@ResponseBody
	@PostMapping("article/update")
	public Boolean update(ArticleWithBLOBs article) {
		return articleService.updateByPrimaryKeySelective(article)>0;
	}
	/**
	 * 查询投诉
	 * @Title: complain 
	 * @Description: TODO
	 * @param m
	 * @param vo
	 * @param pageNum
	 * @return
	 * @return: String
	 */
	@GetMapping("article/complains")
	public String complain(Model m,ComplainVO vo,@RequestParam(defaultValue = "1")int pageNum) {
		PageInfo<Complain> info = comService.selects(vo, pageNum);
		m.addAttribute("info", info);
		m.addAttribute("vo", vo);
		
		return "admin/article/complains";
	}
	
	
	
	
}

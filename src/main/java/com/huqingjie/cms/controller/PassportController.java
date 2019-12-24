package com.huqingjie.cms.controller;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.huqingjie.cms.domain.User;
import com.huqingjie.cms.service.UserService;
import com.huqingjie.cms.util.CMSException;
import com.huqingjie.cms.util.CookieUtil;
import com.huqingjie.utils.StringUtil;

@RequestMapping("passport")
@Controller
public class PassportController {

	@Resource
	private UserService userService;

	// 去登陆页面
	@GetMapping("login")
	public String login() {
		return "passport/login";
	}

	// 去注册页面
	@GetMapping("reg")
	public String reg() {
		return "passport/reg";
	}

	// 注册
	@PostMapping("reg")
	public String reg(Model m, User user, RedirectAttributes redirectAttributes) {
		try {

			int i = userService.insertSelective(user);

			if (i > 0) {
				// 重定向传值，携带参数
				redirectAttributes.addFlashAttribute("username", user.getUsername());
				// 注册成功，重定向到登陆页面
				return "redirect:/passport/login";
			}
		} catch (CMSException e) {
			// TODO: handle exception
			e.printStackTrace();
			m.addAttribute("error", e.getMessage());
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			m.addAttribute("error", "系统错误，请联系管理员");
		}

		m.addAttribute("user", user);
		return "passport/reg";// 注册失败
	}

	// 登陆
	@PostMapping("login")
	public String login(Model m, User user, HttpSession session, HttpServletResponse response) {
		try {
			// 账号验证成功
			User u = userService.login(user);
			// 如果用户勾选了 【10天免登陆】
			if (StringUtil.hasText(user.getIsRemember())) {
				CookieUtil.addCookie(response, "username", u.getUsername(), 60 * 60 * 24 * 10);// 存10天
				CookieUtil.addCookie(response, "password", u.getPassword(), 60 * 60 * 24 * 10);// 存10天
			}
			// 根据用户进入不同的页面
			if ("0".equals(u.getRole())) {
				// 登陆成功 存session 普通用户
				session.setAttribute("user", u);
				return "redirect:/my";
			} else {
				// 登陆成功 存session 管理员
				session.setAttribute("admin", u);
				return "redirect:/admin";
			}

		} catch (CMSException e) {
			e.printStackTrace();
			m.addAttribute("error", e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			m.addAttribute("error", "系统异常，请联系管理员");
		}

		return "passport/login";
	}

	// 注销
	@GetMapping("logout")
	public String logout(HttpSession session, HttpServletRequest request, HttpServletResponse resp) {

		// 让cookie删除
		Cookie[] cookies = request.getCookies();
		if (null != cookies) {
			for (Cookie cookie : cookies) {
				// System.out.println("cookie.getName():"+cookie.getName());
				if (cookie.getName().equals("username")) {
					cookie.setMaxAge(0);// cookie的存活时间。 0：删除cookie
					cookie.setPath("/");
					resp.addCookie(cookie);
				}
			}
		}

		session.invalidate();
		return "redirect:/passport/login";
	}

}

package com.qiaosoftware.crm.handler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.qiaosoftware.crm.entity.Authority;
import com.qiaosoftware.crm.entity.Role;
import com.qiaosoftware.crm.entity.User;
import com.qiaosoftware.crm.model.Navigation;
import com.qiaosoftware.crm.model.Page;
import com.qiaosoftware.crm.service.RoleService;
import com.qiaosoftware.crm.service.UserService;

@RequestMapping("/user")
@Controller
public class UserHandler {

	@Autowired
	private UserService userService;
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private ResourceBundleMessageSource messageSource;
	
	@ResponseBody
	@RequestMapping("/navigate")
	public List<Navigation> navigate(HttpSession session){
		
		List<Navigation> navigations = new ArrayList<>();
		
		Navigation top = new Navigation();
		top.setId(Long.MAX_VALUE);
		top.setText("客户关系管理系统");
		navigations.add(top);
		
		User user = (User) session.getAttribute("user");
		String path = session.getServletContext().getContextPath();
		
		Map<Long, Navigation> parentNavigations = new HashMap<>();
		Navigation parentNavigation = null;
		
		for(Authority authority: user.getRole().getAuthorities()){
			
			Navigation subNavigation = new Navigation();
			subNavigation.setId(authority.getId());
			subNavigation.setText(authority.getDisplayName());
			subNavigation.setUrl(path + authority.getUrl());
			
			Authority parentAuthority = authority.getParentAuthority();
			parentNavigation = parentNavigations.get(parentAuthority.getId());
			
			if (parentNavigation == null) {
				parentNavigation = new Navigation();
				parentNavigation.setId(parentAuthority.getId());
				parentNavigation.setText(parentAuthority.getDisplayName());
				parentNavigation.setState("closed");
				
				top.getChildren().add(parentNavigation);
				
				parentNavigations.put(parentAuthority.getId(), parentNavigation);
			}
			parentNavigation.getChildren().add(subNavigation);
		}
		
		return navigations;
	}
	
	@RequestMapping("/delete/{id}")
	public String delete(@PathVariable("id") Long id,
						RedirectAttributes attributes){
		
		userService.delete(id);
		
		attributes.addFlashAttribute("message", "删除成功！");
		
		return "redirect:/user/list";
	}
	
	@RequestMapping("/saveOrUpdate/{id}")
	public String update(User user, RedirectAttributes attributes){
		
		userService.update(user);
		
		attributes.addFlashAttribute("message", "修改成功！");
		
		return "redirect:/user/list";
	}
	
	@RequestMapping("/edit/{id}")
	public String edit(@PathVariable("id") Long id,
						Map<String, Object> map){
		
		List<Role> roles = roleService.getRoles();
		map.put("roles", roles);
		
		Map<Integer, String> allStatus = new LinkedHashMap<>();
		allStatus.put(0, "无效");
		allStatus.put(1, "有效");
		
		map.put("allStatus", allStatus);
		
		User user = userService.get(id);
		map.put("user", user);
		
		return "user/input";
	}
	
	@RequestMapping("/saveOrUpdate/")
	public String save(User user, RedirectAttributes attributes){
		
		String name = user.getName();
		boolean exists = userService.checkName(name);
		
		if (exists) {
			attributes.addFlashAttribute("message", "该用户名已经存在！");
			return "redirect:/user/list";
		}
		
		userService.save(user);
		
		attributes.addFlashAttribute("message", "添加成功！");
		
		return "redirect:/user/list";
	}
	
	@RequestMapping("/create")
	public String create(Map<String, Object> map){
		
		map.put("user", new User());
		
		List<Role> roles = roleService.getRoles();
		map.put("roles", roles);
		
		Map<Integer, String> allStatus = new LinkedHashMap<>();
		allStatus.put(0, "无效");
		allStatus.put(1, "有效");
		
		map.put("allStatus", allStatus);
		
		return "user/input";
	}
	
	@RequestMapping("/list")
	public String list(
			@RequestParam(value="pageNoStr",required=false) String pageNoStr,
			@RequestParam(value="search_LIKE_name",required=false,defaultValue="null") String name,
			@RequestParam(value="search_EQ_enabled",required=false,defaultValue="null") String enabled,
			Map<String, Object> map){
		
		Page<User> page = userService.getConditionPage(pageNoStr, name, enabled);
		map.put("page", page);
		
		return "user/list";
	}
	
	
	/*@RequestMapping("/logout")
	public String logout(HttpSession session){
		session.invalidate();
		return "redirect:/index.jsp";
	}*/
	
	
	@RequestMapping(value="/shiroLogin",method=RequestMethod.POST)
	public String login(
					@RequestParam(value="name",required=false) String name,
					@RequestParam(value="password",required=false) String password,
					HttpSession session,
					RedirectAttributes attributes,
					Locale locale){
		
	    //1.获取当前的 Subject(和 Shiro 交互的对象), 调用 SecurityUtils.getSubject() 方法
		Subject currentUser = SecurityUtils.getSubject();
		//2.检测当前用户是否已经被认证. 即是否登录. 调用 Subject 的 isAuthenticated() 方法
		if (!currentUser.isAuthenticated()) {
		    //3.把用户名和密码封装为一个 UsernamePasswordToken 对象.
			UsernamePasswordToken token = new UsernamePasswordToken(name, password);
			token.setRememberMe(true);
			try {
			    //4. 执行登录. 调用 Subject 的 login(AuthenticationToken). 通常传入的是 UsernamePasswordToken 对象
                //这也说明 UsernamePasswordToken 是 AuthenticationToken 的实现类.
				currentUser.login(token);
			} catch (IncorrectCredentialsException e) {
			    //5. 若用户名不存在, 则会抛出 UnknownAccountException 异常.
			    //6. 若用户名存在, 但密码不匹配, 则会抛出 IncorrectCredentialsException 异常.
				String code = "error.user.login";
				
				String[] args = null;
				
				String message = messageSource.getMessage(code, args, locale);
				
				attributes.addFlashAttribute("message", message);
				
				return "redirect:/index";
				
			} catch (AuthenticationException e) {
				attributes.addFlashAttribute("message", e.getMessage());
				return "redirect:/index";
			}
			
		}
		
		Object user = SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
		session.setAttribute("user", user);
		return "redirect:/success";
	}
	
	/*
	@RequestMapping(value="/login",method=RequestMethod.POST)
	public String login(
					@RequestParam(value="name",required=false) String name,
					@RequestParam(value="password",required=false) String password,
					HttpSession session,
					RedirectAttributes attributes,
					Locale locale){
		
		User user = userService.login(name, password);
		
		if (user != null) {
			session.setAttribute("user", user);
			return "redirect:/success";
		}
		
		String code = "error.user.login";
		
		String[] args = null;
		
		String message = messageSource.getMessage(code, args, locale);
		
		attributes.addFlashAttribute("message", message);
		
		return "redirect:/index";
	} */
	
}

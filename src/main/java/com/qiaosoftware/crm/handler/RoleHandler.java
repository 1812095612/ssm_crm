package com.qiaosoftware.crm.handler;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.qiaosoftware.crm.entity.Authority;
import com.qiaosoftware.crm.entity.Role;
import com.qiaosoftware.crm.model.Page;
import com.qiaosoftware.crm.service.AuthorityService;
import com.qiaosoftware.crm.service.RoleService;

@RequestMapping("/role")
@Controller
public class RoleHandler {
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private AuthorityService authorityService;
	
	
	@RequestMapping(value="assign/{id}",method=RequestMethod.PUT)
	public String assign(@PathVariable("id") Long id,
						@RequestParam(value="authorities2",required=false) List<Long> authList,
						RedirectAttributes attributes){
		
		authorityService.update(id, authList);
		
		attributes.addFlashAttribute("message", "修改成功！");
		
		return "redirect:/role/list";
	}
	
	@RequestMapping(value="/assign/{id}",method=RequestMethod.GET)
	public String toAssign(@PathVariable("id") Long id,
							Map<String, Object> map){
		
		List<Authority> parentAuthorities = authorityService.getAuthorityList();
		
		map.put("parentAuthorities", parentAuthorities);
		
		Role role = roleService.get(id);
		
		List<String> authorities = authorityService.getRoleAuthorityStringList(id);
		
		role.setAuthorities2(authorities);
		map.put("role", role);
		
		return "role/assign";
	}
	
	@RequestMapping("/delete/{id}")
	public String delete(@PathVariable("id") Long id, RedirectAttributes attributes){
		
		roleService.delete(id);
		
		attributes.addFlashAttribute("message", "删除成功！");
		
		return "redirect:/role/list";
	}
	
	@RequestMapping("/save")
	public String save(Role role, RedirectAttributes attributes ){
		
		String name = role.getName();
		boolean exists = roleService.checkName(name);
		
		if (exists) {
			attributes.addFlashAttribute("message", "该角色已经存在！");
			return "redirect:/role/list";
		}
		
		roleService.save(role);
		
		attributes.addFlashAttribute("message", "添加成功！");
		
		return "redirect:/role/list";
	}
	
	@RequestMapping("/create")
	public String create(){
		return "role/input";
	}
	
	@RequestMapping("/list")
	public String list(
			@RequestParam(value="pageNoStr",required=false) String pageNoStr,
			Map<String, Object> map){
		
		Page<Role> page = roleService.getConditionPage(pageNoStr);
		map.put("page", page);
		
		return "role/list";
	}
	
}

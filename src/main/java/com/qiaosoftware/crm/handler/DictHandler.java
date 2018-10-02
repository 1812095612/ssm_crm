package com.qiaosoftware.crm.handler;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.qiaosoftware.crm.entity.Dict;
import com.qiaosoftware.crm.model.Page;
import com.qiaosoftware.crm.service.DictService;

@RequestMapping("/dict")
@Controller
public class DictHandler {

	@Autowired
	private DictService dictService;
	
	@RequestMapping("/delete/{id}")
	public String delete(@PathVariable("id") Long id, RedirectAttributes attributes){
		
//		dictService.delete(id);
		
		attributes.addFlashAttribute("message", "不能删除字典内容！");
		
		return "redirect:/dict/list";
	}
	
	@RequestMapping("/saveOrUpdate/{id}")
	public String update(Dict dict, RedirectAttributes attributes){
		
		dictService.update(dict);
		
		attributes.addFlashAttribute("message", "修改成功！");
		
		return "redirect:/dict/list";
	}
	
	@RequestMapping("/edit/{id}")
	public String edit(
				@PathVariable("id") Long id,
				Map<String, Object> map){
		
		Dict dict = dictService.getDict(id);
		map.put("dict", dict);
		
		return "dict/input";
	}
	
	@RequestMapping("/saveOrUpdate/")
	public String save(Dict dict, RedirectAttributes attributes){
		
		boolean exists = dictService.checkDictExists(dict);
		
		if (exists) {
			attributes.addFlashAttribute("message", dict.getType() + "----" + dict.getItem() + "已存在！");
			return "redirect:/dict/list";
		}
		
		dictService.save(dict);
		
		attributes.addFlashAttribute("message", "修改成功！");
		
		return "redirect:/dict/list";
	}
	
	@RequestMapping("/create")
	public String create(Map<String, Object> map){
		
		map.put("dict", new Dict());
		
		return "dict/input";
	}
	
	@RequestMapping("/list")
	public String list(
			@RequestParam(value="pageNoStr",required=false) String pageNoStr,
			@RequestParam(value="search_LIKE_type",required=false,defaultValue="null") String type,
			@RequestParam(value="search_LIKE_item",required=false,defaultValue="null") String item,
			@RequestParam(value="search_LIKE_value",required=false,defaultValue="null") String value,
			Map<String, Object> map){
		
		Page<Dict> page = dictService.getConditionPage(pageNoStr, type, item, value);
		map.put("page", page);
		
		return "dict/list";
	}
	
}

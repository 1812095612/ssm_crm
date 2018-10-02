package com.qiaosoftware.crm.handler;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.qiaosoftware.crm.entity.Product;
import com.qiaosoftware.crm.model.Page;
import com.qiaosoftware.crm.service.ProductService;

@RequestMapping("/product")
@Controller
public class ProductHandler {

	@Autowired
	private ProductService productService;
	
	@RequestMapping("/delete/{id}")
	public String delete(RedirectAttributes attributes){
		
		attributes.addFlashAttribute("message", "字典的内容不能删除！");
		
		return "redirect:/product/list";
		
	}
	
	@RequestMapping("/saveOrUpdate/{id}")
	public String update(Product product, RedirectAttributes attributes){
		
		productService.update(product);
		
		attributes.addFlashAttribute("message", "修改成功！");
		
		return "redirect:/product/list";
	}
	
	@RequestMapping("/edit/{id}")
	public String edit(@PathVariable("id") Long id,
						Map<String, Object> map){
		
		Product product = productService.getProduct(id);
		map.put("product", product);
		
		return "product/input";
	}
	
	@RequestMapping("/saveOrUpdate/")
	public String save(Product product, RedirectAttributes attributes){
		
		boolean exists = productService.checkProductExists(product);
		
		if (exists) {
			attributes.addFlashAttribute("message", "该产品已存在！");
			return "redirect:/product/list";
		}
		
		productService.save(product);
		
		attributes.addFlashAttribute("message", "添加成功！");
		
		return "redirect:/product/list";
	}
	
	@RequestMapping("/create")
	public String create(Map<String, Object> map){
		
		map.put("product", new Product());
		
		return "product/input";
	}
	
	@RequestMapping("/list")
	public String list(
			@RequestParam(value="pageNoStr",required=false) String pageNoStr,
			@RequestParam(value="search_LIKE_name",required=false,defaultValue="null") String name,
			@RequestParam(value="search_LIKE_type",required=false,defaultValue="null") String type,
			@RequestParam(value="search_LIKE_batch",required=false,defaultValue="null") String batch,
			Map<String, Object> map){
		
		Page<Product> page = productService.getConditionPage(pageNoStr, name, type, batch);
		map.put("page", page);
		
		return "product/list";
	}
	
}

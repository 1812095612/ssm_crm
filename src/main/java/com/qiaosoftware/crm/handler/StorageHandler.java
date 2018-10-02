package com.qiaosoftware.crm.handler;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.qiaosoftware.crm.entity.Product;
import com.qiaosoftware.crm.entity.Storage;
import com.qiaosoftware.crm.model.Page;
import com.qiaosoftware.crm.service.ProductService;
import com.qiaosoftware.crm.service.StorageService;

@RequestMapping("/storage")
@Controller
public class StorageHandler {

	@Autowired
	private StorageService storageService;
	
	@Autowired
	private ProductService productService;
	
	@RequestMapping("/saveOrUpdate/{id}")
	public String update(@PathVariable("id") Long id,
						 @RequestParam(value="incrementCount",required=false) Integer incrementCount,
						 RedirectAttributes attributes){
		
		storageService.update(id, incrementCount);
		
		attributes.addFlashAttribute("message", "修改成功！");
		
		return "redirect:/storage/list";
	}
	
	@RequestMapping("/edit/{id}")
	public String edit(@PathVariable("id") Long id, Map<String, Object> map){
		
		Storage storage = storageService.getStorage(id);
		map.put("storage", storage);
		
		return "storage/input";
	}
	
	@RequestMapping("/delete/{id}")
	public String delete(RedirectAttributes attributes){
		
		attributes.addFlashAttribute("message", "字典的内容不能删除！");
		
		return "redirect:/storage/list";
	}
	
	@RequestMapping("/saveOrUpdate/")
	public String save(Storage storage, RedirectAttributes attributes){
		
		storageService.save(storage);
		
		attributes.addFlashAttribute("message", "添加成功！");
		
		return "redirect:/storage/list";
	}
	
	@RequestMapping("/create")
	public String create(Map<String, Object> map){
		
		map.put("storage", new Storage());
		
		List<Product> products = productService.getProducts();
		map.put("products", products);
		
		return "storage/input";
	}
	
	@RequestMapping("/list")
	public String list(
			@RequestParam(value="pageNoStr",required=false) String pageNoStr,
			@RequestParam(value="search_LIKE_productName",required=false,defaultValue="null") String productName,
			@RequestParam(value="search_LIKE_wareHouse",required=false,defaultValue="null") String wareHouse,
			Map<String, Object> map){
		
		Page<Storage> page = storageService.getConditionPage(pageNoStr, productName, wareHouse);
		map.put("page", page);
		
		return "storage/list"; 
	}
}

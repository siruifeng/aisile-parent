package com.aisile.shop.controller;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aisile.pojo.TbTypeTemplate;
import com.aisile.pojo.entity.PageResult;
import com.aisile.pojo.entity.Result;
import com.aisile.sellergoods.service.TypeTemplateService;
import com.alibaba.dubbo.config.annotation.Reference;

@RestController
@RequestMapping("/typeTemplate")
public class TypeTemplateController {
	@Reference
	private TypeTemplateService typeTemplateService;
	
	@RequestMapping("/findAll")
	public List<TbTypeTemplate> findAll(){
		return typeTemplateService.findAll();
	}
	
	@RequestMapping("/findPage")
	public PageResult findPage( int page,int rows){
		return typeTemplateService.findPage(page, rows);
	}
	
	@RequestMapping("/findSearch")
	public PageResult findSearch(int page,int rows,@RequestBody TbTypeTemplate tbTypeTemplate) {
		return typeTemplateService.findSearch(page, rows, tbTypeTemplate);
	}
	
	@RequestMapping("/add")
	public Result add(@RequestBody TbTypeTemplate tbTypeTemplate) {
		try {
			typeTemplateService.add(tbTypeTemplate);
			return new Result(true, "添加成功");
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return new Result(false, "添加失败");
		}
	}
	@RequestMapping("/update")
	public Result update(@RequestBody TbTypeTemplate tbTypeTemplate) {
		try {
			typeTemplateService.update(tbTypeTemplate);
			return new Result(true, "修改成功");
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return new Result(false, "修改失败");
		}
	}
	@RequestMapping("/findOne")
	public TbTypeTemplate findOne(Long id) {
		//去后台获取id
		return typeTemplateService.findOne(id);
	}
	
	@RequestMapping("/dele")
	public Result delete(Long [] ids) {
		try {
			typeTemplateService.delete(ids);
			return new Result(true, "删除成功");
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return new Result(false, "删除失败");
		}
	}
	@RequestMapping("/findSpecList")
	public List<Map> findOptionsList(Long id){
		return  typeTemplateService.findOptionsList(id);
	}
}

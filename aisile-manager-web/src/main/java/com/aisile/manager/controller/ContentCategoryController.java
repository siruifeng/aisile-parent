package com.aisile.manager.controller;

import java.util.List;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aisile.content.service.ContentCategoryService;
import com.aisile.pojo.TbContentCategory;
import com.aisile.pojo.TbItemCat;
import com.aisile.pojo.entity.PageResult;
import com.aisile.pojo.entity.Result;
import com.alibaba.dubbo.config.annotation.Reference;

@RestController
@RequestMapping("/contentCategory")
public class ContentCategoryController {
	@Reference
	private ContentCategoryService contentCategoryService;
	
	@RequestMapping("/findAll")
	public List<TbContentCategory> findAll(){
		return contentCategoryService.findAll();
	}
	
	@RequestMapping("/findPage")
	public PageResult findPage( int page,int rows){
		return contentCategoryService.findPage(page, rows);
	}
	
	@RequestMapping("/findSearch")
	public PageResult findSearch(int page,int rows,@RequestBody TbContentCategory tbContentCategory) {
		return contentCategoryService.findSearch(page, rows, tbContentCategory);
	}
	
	@RequestMapping("/add")
	public Result add(@RequestBody TbItemCat tbItemCat) {
		try {
			contentCategoryService.add(tbItemCat);
			return new Result(true, "添加成功");
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return new Result(false, "添加失败");
		}
	}
	@RequestMapping("/update")
	public Result update(@RequestBody TbItemCat tbItemCat) {
		try {
			contentCategoryService.update(tbItemCat);
			return new Result(true, "修改成功");
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return new Result(false, "修改失败");
		}
	}
	@RequestMapping("/findOne")
	public TbItemCat findOne(Long id) {
		return contentCategoryService.findOne(id);
	}
	
	@RequestMapping("/dele")
	public Result delete(Long [] ids) {
		try {
			for (Long id : ids) {
				contentCategoryService.delete(id);
			}
			return new Result(true, "删除成功");
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return new Result(false, "删除失败");
		}
	}
	@RequestMapping("findAllByParentId")
	public List<TbItemCat> findAllByParentId(Long parentId){
		return contentCategoryService.findAllByParentId(parentId);
	}
}

package com.aisile.manager.controller;

import java.util.List;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aisile.content.service.ContentService;
import com.aisile.pojo.TbContent;
import com.aisile.pojo.TbSeller;
import com.aisile.pojo.entity.PageResult;
import com.aisile.pojo.entity.Result;
import com.alibaba.dubbo.config.annotation.Reference;

@RestController
@RequestMapping("/content")
public class ContentController {

	@Reference
	private ContentService contentService;
	
	@RequestMapping("/findAll")
	public List<TbContent> findAll(){
		return contentService.findAll();
	}
	
	@RequestMapping("/findPage")
	public PageResult findPage( int page,int rows){
		return contentService.findPage(page, rows);
	}
	
	@RequestMapping("/findSearch")
	public PageResult findSearch(int page,int rows,@RequestBody TbContent tbContent) {
		return contentService.findSearch(page, rows, tbContent);
	}
	
	@RequestMapping("/add")
	public Result add(@RequestBody TbContent tbContent ){
		try {
			contentService.add(tbContent);
			return new Result(true, "添加成功");
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return new Result(false, "添加失败");
		}
	}
	@RequestMapping("/update")
	public Result update(@RequestBody TbContent tbContent) {
		try {
			contentService.update(tbContent);
			return new Result(true, "修改成功");
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return new Result(false, "修改失败");
		}
	}
	@RequestMapping("/updateStatus")
	public Result updateStatus(@RequestBody TbSeller tbSeller,String status) {
		try {
			contentService.updateStatus(tbSeller,status);
			return new Result(true, "修改成功");
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return new Result(false, "修改失败");
		}
	}
	@RequestMapping("/findOne")
	public TbContent  findOne(Long id) {
		return contentService.findOne(id);
	}
	
	@RequestMapping("/dele")
	public Result delete(Long [] ids) {
		try {
			contentService.delete(ids);
			return new Result(true, "删除成功");
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return new Result(false, "删除失败");
		}
	}
	
	@RequestMapping("/shield")
	public Result shield(Long[] ids) {
		try {
			contentService.shield(ids);
			return new Result(true, "屏蔽成功");
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return new Result(false, "屏蔽失败");
		}
	}
	@RequestMapping("/openq")
	public Result openq(Long[] ids) {
		try {
			contentService.openq(ids);
			return new Result(true, "开启成功");
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return new Result(false, "开启失败");
		}
	}
}

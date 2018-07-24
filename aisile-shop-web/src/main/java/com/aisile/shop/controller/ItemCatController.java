package com.aisile.shop.controller;

import java.util.List;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aisile.pojo.TbItemCat;
import com.aisile.pojo.entity.PageResult;
import com.aisile.pojo.entity.Result;
import com.aisile.sellergoods.service.ItemCatService;
import com.alibaba.dubbo.config.annotation.Reference;

@RestController
@RequestMapping("/itemCat")
public class ItemCatController {
	@Reference
	private ItemCatService itemCatService;
	
	@RequestMapping("/findAll")
	public List<TbItemCat> findAll(){
		return itemCatService.findAll();
	}
	
	@RequestMapping("/findPage")
	public PageResult findPage( int page,int rows){
		return itemCatService.findPage(page, rows);
	}
	
	@RequestMapping("/findSearch")
	public PageResult findSearch(int page,int rows,@RequestBody TbItemCat tbItemCat) {
		return itemCatService.findSearch(page, rows, tbItemCat);
	}
	
	@RequestMapping("/add")
	public Result add(@RequestBody TbItemCat tbItemCat) {
		try {
			itemCatService.add(tbItemCat);
			return new Result(true, "添加成功");
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return new Result(false, "添加失败");
		}
	}
	@RequestMapping("/update")
	public Result update(@RequestBody TbItemCat tbItemCat) {
		try {
			itemCatService.update(tbItemCat);
			return new Result(true, "修改成功");
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return new Result(false, "修改失败");
		}
	}
	@RequestMapping("/findOne")
	public TbItemCat findOne(Long id) {
		return itemCatService.findOne(id);
	}
	
	@RequestMapping("/dele")
	public Result delete(Long [] ids) {
		try {
			for (Long id : ids) {
				itemCatService.delete(id);
			}
			return new Result(true, "删除成功");
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return new Result(false, "删除失败");
		}
	}
	@RequestMapping("findAllByParentId")
	public List<TbItemCat> findAllByParentId(Long parentId){
		return itemCatService.findAllByParentId(parentId);
	}
}

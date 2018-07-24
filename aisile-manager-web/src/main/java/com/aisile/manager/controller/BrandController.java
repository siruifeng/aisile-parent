package com.aisile.manager.controller;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aisile.pojo.TbBrand;
import com.aisile.pojo.entity.PageResult;
import com.aisile.pojo.entity.Result;
import com.aisile.sellergoods.service.BrandService;
import com.alibaba.dubbo.config.annotation.Reference;

@RestController
@RequestMapping("/brand")
public class BrandController {
	@Reference
	private BrandService brandService;
	
	@RequestMapping("/findAll")
	public List<TbBrand> findAll(){
		return brandService.geTbBrands();
	}
	
	@RequestMapping("/findPage")
	public PageResult findPage( int page,int rows){
		return brandService.findPage(page, rows);
	}
	
	@RequestMapping("/findSearch")
	public PageResult findSearch(int page,int rows,@RequestBody TbBrand tbBrand) {
		return brandService.findSearch(page, rows, tbBrand);
	}
	
	@RequestMapping("/add")
	public Result add(@RequestBody TbBrand tbBrand) {
		try {
			brandService.add(tbBrand);
			return new Result(true, "添加成功");
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return new Result(false, "添加失败");
		}
	}
	@RequestMapping("/update")
	public Result update(@RequestBody TbBrand tbBrand) {
		try {
			brandService.update(tbBrand);
			return new Result(true, "修改成功");
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return new Result(false, "修改失败");
		}
	}
	@RequestMapping("/findOne")
	public TbBrand findOne(Long id) {
		return brandService.findOne(id);
	}
	
	@RequestMapping("/dele")
	public Result delete(Long [] ids) {
		try {
			brandService.delete(ids);
			return new Result(true, "删除成功");
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return new Result(false, "删除失败");
		}
	}
	@RequestMapping("/selectOptionList")
	public List<Map> selectOptionList() {
		return brandService.selectOptionList();
	}
}

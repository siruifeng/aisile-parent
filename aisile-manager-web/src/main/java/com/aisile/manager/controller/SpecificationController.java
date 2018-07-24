package com.aisile.manager.controller;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aisile.pojo.TbSpecification;
import com.aisile.pojo.entity.PageResult;
import com.aisile.pojo.entity.Result;
import com.aisile.pojo.entity.group.Specification;
import com.aisile.sellergoods.service.SpecificationService;
import com.alibaba.dubbo.config.annotation.Reference;

@RestController
@RequestMapping("/specification")
public class SpecificationController {
	@Reference
	private SpecificationService specificationService;
	
	@RequestMapping("/findAll")
	public List<TbSpecification> findAll(){
		return specificationService.findAll();
	}
	
	@RequestMapping("/findPage")
	public PageResult findPage( int page,int rows){
		return specificationService.findPage(page, rows);
	}
	
	@RequestMapping("/findSearch")
	public PageResult findSearch(int page,int rows,@RequestBody TbSpecification tbSpecification) {
		return specificationService.findSearch(page, rows, tbSpecification);
	}
	
	@RequestMapping("/add")
	public Result add(@RequestBody Specification specification) {
		try {
			specificationService.add(specification);
			return new Result(true, "添加成功");
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return new Result(false, "添加失败");
		}
	}
	@RequestMapping("/update")
	public Result update(@RequestBody Specification specification) {
		try {
			specificationService.update(specification);
			return new Result(true, "修改成功");
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return new Result(false, "修改失败");
		}
	}
	@RequestMapping("/findOne")
	public Specification findOne(Long id) {
		//去后台获取id
		return specificationService.findOne(id);
	}
	
	@RequestMapping("/dele")
	public Result delete(Long [] ids) {
		try {
			specificationService.delete(ids);
			return new Result(true, "删除成功");
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return new Result(false, "删除失败");
		}
	}
	@RequestMapping("/selectOptionList")
	public List<Map> selectOptionList() {
			return specificationService.selectOptionList();
	}
	
}

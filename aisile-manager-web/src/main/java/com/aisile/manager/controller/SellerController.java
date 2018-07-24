package com.aisile.manager.controller;

import java.util.List;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aisile.pojo.TbSeller;
import com.aisile.pojo.entity.PageResult;
import com.aisile.pojo.entity.Result;
import com.aisile.sellergoods.service.SellerService;
import com.alibaba.dubbo.config.annotation.Reference;

@RestController
@RequestMapping("/seller")
public class SellerController {

	@Reference
	private SellerService sellerService;
	
	@RequestMapping("/findAll")
	public List<TbSeller> findAll(){
		return sellerService.findAll();
	}
	
	@RequestMapping("/findPage")
	public PageResult findPage( int page,int rows){
		return sellerService.findPage(page, rows);
	}
	
	@RequestMapping("/findSearch")
	public PageResult findSearch(int page,int rows,@RequestBody TbSeller tbSeller) {
		return sellerService.findSearch(page, rows, tbSeller);
	}
	
	@RequestMapping("/add")
	public Result add(@RequestBody TbSeller tbSeller ){
		try {
			sellerService.add(tbSeller);
			return new Result(true, "添加成功");
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return new Result(false, "添加失败");
		}
	}
	@RequestMapping("/update")
	public Result update(@RequestBody TbSeller tbSeller) {
		try {
			sellerService.update(tbSeller);
			return new Result(true, "修改成功");
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return new Result(false, "修改失败");
		}
	}
	@RequestMapping("/updateStatus")
	public Result updateStatus(@RequestBody TbSeller tbSeller,String status) {
		try {
			sellerService.updateStatus(tbSeller,status);
			return new Result(true, "修改成功");
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return new Result(false, "修改失败");
		}
	}
	@RequestMapping("/findOne")
	public TbSeller findOne(String id) {
		return sellerService.findOne(id);
	}
	
	@RequestMapping("/dele")
	public Result delete(Long [] ids) {
		try {
			sellerService.delete(ids);
			return new Result(true, "删除成功");
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return new Result(false, "删除失败");
		}
	}
}

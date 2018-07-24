package com.aisile.manager.controller;

import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aisile.pojo.TbGoods;
import com.aisile.pojo.entity.PageResult;
import com.aisile.pojo.entity.Result;
import com.aisile.pojo.entity.group.Goods;
import com.aisile.sellergoods.service.GoodsService;
import com.alibaba.dubbo.config.annotation.Reference;

@RestController
@RequestMapping("/goods")
public class GoodsController {

	@Reference
	private GoodsService goodsService;
	
	@RequestMapping("/findAll")
	public List<TbGoods> findAll(){
		return goodsService.findAll();
	}
	
	@RequestMapping("/findPage")
	public PageResult findPage( int page,int rows){
		return goodsService.findPage(page, rows);
	}
	
	@RequestMapping("/findSearch")
	public PageResult findSearch(int page,int rows,@RequestBody TbGoods tbGoods) {
		/*String sellerName = SecurityContextHolder.getContext().getAuthentication().getName();
		tbGoods.setSellerId(sellerName);*/
		return goodsService.findSearch(page, rows, tbGoods);
	}
	
	@RequestMapping("/add")
	public Result add(@RequestBody Goods goods ){
		try {
			//根据认证用户获取id
			String seler_id = SecurityContextHolder.getContext().getAuthentication().getName();
			goods.getGoods().setSellerId(seler_id);
			goodsService.add(goods);
			return new Result(true, "添加成功");
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return new Result(false, "添加失败");
		}
	}
	@RequestMapping("/update")
	public Result update(@RequestBody Goods goods) {
		try {
			//效验是否是当前商家的ID
			Goods goods2 = goodsService.findOne(goods.getGoods().getId());
			//获取当前登录的商家ID
			String sellerId = SecurityContextHolder.getContext().getAuthentication().getName();
			//如果传递过来的商家ID并不是当前登录用户的ID,则属于非法操作
			if (!goods2.getGoods().getSellerId().equals(sellerId) || !goods.getGoods().getSellerId().equals(sellerId)) {
				return new Result(false, "非法操作");
			}
			goodsService.update(goods);
			return new Result(true, "修改成功");
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return new Result(false, "修改失败");
		}
	}
	@RequestMapping("/findOne")
	public Goods findOne(Long id) {
		return goodsService.findOne(id);
	}
	
	@RequestMapping("/dele")
	public Result delete(Long [] ids) {
		try {
			goodsService.delete(ids);
			return new Result(true, "删除成功");
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return new Result(false, "删除失败");
		}
	}
	//驳回
	@RequestMapping("/turnDown")
	public Result turnDown(Long [] ids) {
		try {
			goodsService.turnDown(ids);
			return new Result(true, "驳回成功");
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return new Result(false, "驳回失败");
		}
	}
	//审核通过
	@RequestMapping("/openGo")
	public Result openGo(Long [] ids) {
		try {
			goodsService.openGo(ids);
			return new Result(true, "审核成功");
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return new Result(false, "审核失败");
		}
	}
	
	
}

package com.aisile.sellergoods.service;

import java.util.List;

import com.aisile.pojo.TbGoods;
import com.aisile.pojo.entity.PageResult;
import com.aisile.pojo.entity.group.Goods;

public interface GoodsService {
	/**
	 * 查询全部数据
	 * @return
	 */
	public List<TbGoods> findAll();
	/**
	 * 分页
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public PageResult findPage(int pageNum,int pageSize);
	/**
	 * 模糊查询
	 * @param pageNum
	 * @param pageSize
	 * @param TbGoods
	 * @return
	 */
	public PageResult findSearch(int pageNum,int pageSize,TbGoods tbGoods);
	/**
	 * 添加方法
	 * 使用的是组合类
	 * @param Goods
	 */
	public void add(Goods goods);
	/**
	 * 修改方法
	 * @param TbGoods
	 */
	public void update(Goods goods);
	/**
	 * 回显   返回的是一个对象
	 * @param id
	 * @return
	 */
	public Goods findOne(Long id);
	/**
	 * 删除(批量删除)
	 * @param ids
	 */
	public void delete(Long [] ids);
	/**
	 * 驳回
	 * @param ids
	 */
	public void turnDown(Long[] ids);
	/**
	 * 申请通过
	 * @param ids
	 */
	public void openGo(Long[] ids);
	/**
	 * 上下架处理
	 * @param ids
	 * @param status
	 */
	public void upDownFrame(Long [] ids,String status);
}

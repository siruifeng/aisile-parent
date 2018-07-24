package com.aisile.sellergoods.service;

import java.util.List;

import com.aisile.pojo.TbItemCat;
import com.aisile.pojo.entity.PageResult;

public interface ItemCatService {
	/**
	 * 查询全部数据
	 * @return
	 */
	public List<TbItemCat> findAll();
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
	 * @param TbItemCat
	 * @return
	 */
	public PageResult findSearch(int pageNum,int pageSize,TbItemCat tbItemCat);
	/**
	 * 添加方法
	 * @param parentId 
	 * @param tnBrand
	 */
	public void add(TbItemCat tbItemCat);
	/**
	 * 修改方法
	 * @param TbItemCat
	 */
	public void update(TbItemCat tbItemCat);
	/**
	 * 回显   返回的是一个对象
	 * @param id
	 * @return
	 */
	public TbItemCat findOne(Long id);
	/**
	 * 删除(批量删除)
	 * @param ids
	 */
	public boolean delete(Long id);
	/**
	 * 根据父亲的id获取列表
	 * @param parentId
	 * @return
	 */
	public List<TbItemCat> findAllByParentId(Long parentId);
	public List<TbItemCat> findOne1(Long id);
}

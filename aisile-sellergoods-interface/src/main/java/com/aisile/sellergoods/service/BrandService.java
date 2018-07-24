package com.aisile.sellergoods.service;

import java.util.List;
import java.util.Map;

import com.aisile.pojo.TbBrand;
import com.aisile.pojo.entity.PageResult;

public interface BrandService {
	/**
	 * 查询全部数据
	 * @return
	 */
	public List<TbBrand> geTbBrands();
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
	 * @param tbBrand
	 * @return
	 */
	public PageResult findSearch(int pageNum,int pageSize,TbBrand tbBrand);
	/**
	 * 添加方法
	 * @param tnBrand
	 */
	public void add(TbBrand tnBrand);
	/**
	 * 修改方法
	 * @param tbBrand
	 */
	public void update(TbBrand tbBrand);
	/**
	 * 回显   返回的是一个对象
	 * @param id
	 * @return
	 */
	public TbBrand findOne(Long id);
	/**
	 * 删除(批量删除)
	 * @param ids
	 */
	public void delete(Long [] ids);
	/**
	 * 查询下拉框
	 * @return
	 */
	public List<Map> selectOptionList();
}

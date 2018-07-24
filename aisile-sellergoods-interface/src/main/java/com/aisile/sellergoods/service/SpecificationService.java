package com.aisile.sellergoods.service;

import java.util.List;
import java.util.Map;

import com.aisile.pojo.TbSpecification;
import com.aisile.pojo.entity.PageResult;
import com.aisile.pojo.entity.group.Specification;

public interface SpecificationService {
	/**
	 * 查询全部数据
	 * @return
	 */
	public List<TbSpecification> findAll();
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
	public PageResult findSearch(int pageNum,int pageSize,TbSpecification tbSpecification);
	/**
	 * 添加方法
	 * @param tnBrand
	 */
	public void add(Specification specification);
	/**
	 * 修改方法
	 * @param tbBrand
	 */
	public void update(Specification specification);
	/**
	 * 回显   返回的是一个对象
	 * @param id
	 * @return
	 */
	public Specification findOne(Long id);
	/**
	 * 删除(批量删除)
	 * @param ids
	 */
	public void delete(Long [] ids);
	/**
	 * 规格名称下拉框
	 * @return
	 */
	public List<Map> selectOptionList();

}

package com.aisile.sellergoods.service;

import java.util.List;
import java.util.Map;

import com.aisile.pojo.TbTypeTemplate;
import com.aisile.pojo.entity.PageResult;

public interface TypeTemplateService {
	/**
	 * 查询全部数据
	 * @return
	 */
	public List<TbTypeTemplate> findAll();
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
	 * @param tbTypeTemplate
	 * @return
	 */
	public PageResult findSearch(int pageNum,int pageSize,TbTypeTemplate tbTypeTemplate);
	/**
	 * 添加
	 * @param tbTypeTemplate
	 */
	public void add(TbTypeTemplate tbTypeTemplate);
	/**
	 * 修改
	 * @param tbTypeTemplate
	 */
	public void update(TbTypeTemplate tbTypeTemplate);
	/**
	 * 回显    返回的是一个对象
	 * @param id
	 * @return
	 */
	public TbTypeTemplate findOne(Long id);
	/**
	 * 批量删除
	 * @param ids
	 */
	public void delete(Long [] ids);
	/**
	 * 规格选项使用
	 */
	public List<Map> findOptionsList(Long id);
}

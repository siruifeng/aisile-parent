package com.aisile.content.service;

import java.util.List;

import com.aisile.pojo.TbContent;
import com.aisile.pojo.TbSeller;
import com.aisile.pojo.entity.PageResult;

public interface ContentService {
	
	public List<TbContent> findAll();
	
	public PageResult findPage( int page,int rows);
	
	public PageResult findSearch(int page,int rows,TbContent tbContent);
	
	public void add(TbContent tbContent );
	
	public void update(TbContent tbContent);
	
	public TbContent findOne(Long id); 
	
	public void delete(Long [] ids);

	public void updateStatus(TbSeller tbSeller, String status);
	
	/**
	 * 根据广告类型ID查询列表
	 * @param key
	 * @return
	 */
	public List<TbContent> findByCategoryId(Long categoryId);
	
	//屏蔽
	public void shield(Long [] ids);
	//开启
	public void openq(Long[] ids);

}

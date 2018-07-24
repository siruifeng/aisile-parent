package com.aisile.sellergoods.service;

import java.util.List;

import com.aisile.pojo.TbSeller;
import com.aisile.pojo.entity.PageResult;
import com.aisile.pojo.entity.Result;

public interface SellerService {
	
	public List<TbSeller> findAll();
	
	public PageResult findPage( int page,int rows);
	
	public PageResult findSearch(int page,int rows,TbSeller tbSeller);
	
	public void add(TbSeller tbSeller );
	
	public void update(TbSeller tbSeller);
	
	public TbSeller findOne(String id); 
	
	public Result delete(Long [] ids);

	public void updateStatus(TbSeller tbSeller, String status);
}

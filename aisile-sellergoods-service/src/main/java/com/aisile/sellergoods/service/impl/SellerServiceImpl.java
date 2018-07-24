package com.aisile.sellergoods.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.aisile.mapper.TbSellerMapper;
import com.aisile.pojo.TbSeller;
import com.aisile.pojo.TbSellerExample;
import com.aisile.pojo.TbSellerExample.Criteria;
import com.aisile.pojo.entity.PageResult;
import com.aisile.pojo.entity.Result;
import com.aisile.sellergoods.service.SellerService;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
@Service
public class SellerServiceImpl implements SellerService {

	@Autowired
	private TbSellerMapper sellerMapper;
	
	@Override
	public List<TbSeller> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PageResult findPage(int page, int rows) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PageResult findSearch(int page, int rows, TbSeller tbSeller) {
		PageHelper.startPage(page, rows);
		TbSellerExample example = new TbSellerExample();
		Criteria criteria = example.createCriteria();
		if(tbSeller.getName() != null && !tbSeller.getName().equals("")) {
			criteria.andNameLike("%"+tbSeller.getName()+"%");
		}                  
		if(tbSeller.getStatus() != null && !tbSeller.getStatus().equals("")) {
			criteria.andStatusEqualTo(tbSeller.getStatus());
		}                  
		if(tbSeller.getNickName() != null && !tbSeller.getNickName().equals("")) {
			criteria.andNickNameLike("%"+tbSeller.getNickName()+"%");
		}                  
		Page<TbSeller> pageinfo = (Page<TbSeller>) sellerMapper.selectByExample(example);
		return new PageResult(pageinfo.getTotal(), pageinfo.getResult());
	}

	@Override
	public void add(TbSeller tbSeller) {
		tbSeller.setStatus("0");
		tbSeller.setCreateTime(new Date());
		sellerMapper.insert(tbSeller);
	}

	@Override
	public TbSeller findOne(String id) {
		return sellerMapper.selectByPrimaryKey(id);
	}

	@Override
	public Result delete(Long[] ids) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(TbSeller tbSeller) {
		
	}

	@Override
	public void updateStatus(TbSeller tbSeller, String status) {
		tbSeller.setStatus(status);
		sellerMapper.updateByPrimaryKeySelective(tbSeller);
		
	}

}
